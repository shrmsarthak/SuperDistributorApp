package com.app.superdistributor;

public class MessageModel {
    String Sender;
    String Recipient;
    String Message;

    public MessageModel(String sender, String recipient, String message) {
        Sender = sender;
        Recipient = recipient;
        Message = message;
    }

    public MessageModel() {
    }


    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getRecipient() {
        return Recipient;
    }

    public void setRecipient(String recipient) {
        Recipient = recipient;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "Sender='" + Sender + '\'' +
                ", Recipient='" + Recipient + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
