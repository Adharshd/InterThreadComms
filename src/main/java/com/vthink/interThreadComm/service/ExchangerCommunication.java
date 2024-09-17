package com.vthink.interThreadComm.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.Exchanger;

@Service("exchangerCommunication")
public class ExchangerCommunication implements ThreadCommunicationMethod {
    private final Exchanger<String> exchanger = new Exchanger<>();

    @Override
    public void communicate() throws InterruptedException {
        Thread producer = new Thread(() -> {
            try {
                String message = "Message from Producer";
                System.out.println("Produced: " + message);
                String response = exchanger.exchange(message);
                System.out.println("Received from Consumer: " + response);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                String received = exchanger.exchange(null); // Wait for producer's message
                System.out.println("Consumed: " + received);
                exchanger.exchange("Ack from Consumer");
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
