package com.vthink.interThreadComm.service;

import org.springframework.stereotype.Service;

@Service("waitNotifyCommunication")
public class WaitNotifyCommunication implements ThreadCommunicationMethod {
    private final Object lock = new Object();
    private boolean ready = false;

    @Override
    public void communicate() throws InterruptedException {
        Thread producer = new Thread(() -> {
            synchronized (lock) {
                ready = true;
                System.out.println("Produced");
                lock.notify();
            }
        });

        Thread consumer = new Thread(() -> {
            synchronized (lock) {
                try {
                    while (!ready) {
                        lock.wait();
                    }
                    System.out.println("Consumed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
    }
}
