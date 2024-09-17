package com.vthink.interThreadComm.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service("completableFutureCommunication")
public class CompletableFutureCommunication implements ThreadCommunicationMethod {

    @Override
    public void communicate() throws InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate delay
                future.complete("Message from Producer");
                System.out.println("Produced");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            future.thenAccept(message -> {
                System.out.println("Consumed: " + message);
            });
        });

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
    }
}
