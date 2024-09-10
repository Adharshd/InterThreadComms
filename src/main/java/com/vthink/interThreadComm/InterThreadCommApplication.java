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

		int choice = scanner.nextInt();

		ThreadCommunicationMethod communicationMethod;

		// Based on the choice, get the appropriate bean from the Spring context
		if (choice == 1) {
			communicationMethod = (ThreadCommunicationMethod) context.getBean("blockingQueueCommunication");
		} else if (choice == 2) {
			communicationMethod = (ThreadCommunicationMethod) context.getBean("waitNotifyCommunication");
		} else {
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
