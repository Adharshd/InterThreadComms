package com.vthink.interThreadComm.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("reentrantLockCommunication")
public class ReentrantLockCommunication implements ThreadCommunicationMethod {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean ready = false;

    @Override
    public void communicate() throws InterruptedException {
        Thread producer = new Thread(() -> {
            lock.lock();
            try {
                ready = true;
                System.out.println("Produced");
                condition.signal(); // Signal the consumer
            } finally {
                lock.unlock();
            }
        });

        Thread consumer = new Thread(() -> {
            lock.lock();
            try {
                while (!ready) {
                    condition.await(); // Wait for the signal
                }
                System.out.println("Consumed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
    }
}
