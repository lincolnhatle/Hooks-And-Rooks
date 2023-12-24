package com.backend.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findById(int id);

    @Transactional
    void deleteById(int id);

    Message findBySender(String sender);

    Message findByRecipient(String recipient);
}
