package com.backend.Message;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String data;
    private boolean isRead;
    private String sender;
    private String recipient;

    public Message(String data, boolean isRead, String sender, String recipient) {
        this.data = data;
        this.isRead = isRead;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Message() {};

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getID() {
        return id;
    }
}
