package com.vthink.interThreadComm;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.vthink.interThreadComm.service.ThreadCommunicationMethod;

import java.util.Scanner;

@SpringBootApplication
public class InterThreadCommApplication {

	public static void main(String[] args) {
		// Start Spring application and get the ApplicationContext
		ApplicationContext context = SpringApplication.run(InterThreadCommApplication.class, args);

		// Allow user to choose a communication method
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose thread communication method:");
		System.out.println("1. BlockingQueue");
		System.out.println("2. wait/notify");
        System.out.println("3. SynchronousQueue");
        System.out.println("4. Exchanger");
        System.out.println("5. CompletableFuture");
        System.out.println("6. ReentrantLock with Condition");

		int choice = scanner.nextInt();

		ThreadCommunicationMethod communicationMethod;

		// Based on the choice, get the appropriate bean from the Spring context
		switch (choice) {
        case 1:
            communicationMethod = (ThreadCommunicationMethod) context.getBean("blockingQueueCommunication");
            break;
        case 2:
            communicationMethod = (ThreadCommunicationMethod) context.getBean("waitNotifyCommunication");
            break;
        case 3:
            communicationMethod = (ThreadCommunicationMethod) context.getBean("synchronousQueueCommunication");
            break;
        case 4:
            communicationMethod = (ThreadCommunicationMethod) context.getBean("exchangerCommunication");
            break;
        case 5:
            communicationMethod = (ThreadCommunicationMethod) context.getBean("completableFutureCommunication");
            break;
        case 6:
            communicationMethod = (ThreadCommunicationMethod) context.getBean("reentrantLockCommunication");
            break;
        default:
            System.out.println("Invalid choice");
            return;
    }


		// Execute the selected communication method
		try {
			communicationMethod.communicate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
