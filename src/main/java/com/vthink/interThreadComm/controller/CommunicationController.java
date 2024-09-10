package com.vthink.interThreadComm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vthink.interThreadComm.service.BlockingQueueCommunication;
import com.vthink.interThreadComm.service.WaitNotifyCommunication;

@RestController
public class CommunicationController {

    @Autowired
    private BlockingQueueCommunication blockingQueueCommunication;

    @Autowired
    private WaitNotifyCommunication waitNotifyCommunication;

    @GetMapping("/communicate")
    public String communicate(@RequestParam String method) throws InterruptedException {
        if ("blockingQueue".equalsIgnoreCase(method)) {
            blockingQueueCommunication.communicate();
            return "BlockingQueue communication done.";
        } else if ("waitNotify".equalsIgnoreCase(method)) {
            waitNotifyCommunication.communicate();
            return "wait/notify communication done.";
        } else {
            return "Invalid communication method!";
        }
    }
}
