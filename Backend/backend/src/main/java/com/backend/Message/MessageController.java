package com.backend.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @GetMapping(path = "/messages")
    List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @PutMapping(path = "messages/isRead/{id}")
    void changeToRead(@PathVariable int id) {
        Message message = messageRepository.findById(id);
        message.setRead(true);
        messageRepository.save(message);
    }


}
