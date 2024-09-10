package com.vthink.interThreadComm.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service("blockingQueueCommunication")
public class BlockingQueueCommunication implements ThreadCommunicationMethod {
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    @Override
    public void communicate() throws InterruptedException {
        Thread producer = new Thread(() -> {
            try {
                queue.put("Message from Producer");
                System.out.println("Produced");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                String message = queue.take();
                System.out.println("Consumed: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
    }
}
