package com.example.MollyMoranFYP.Models;

public class Message {
    //private int userID;
    private String subject;
    private String messageText;
    private String image;
    private String sender;


    public Message() {
    }

    public Message(String subject, String messageText, String image, String sender) {
        this.subject = subject;
        this.messageText = messageText;
        this.image = image;
        this.sender = sender;
    }



    public Message(String subject, String messageText, String sender) {
        this.subject = subject;
        this.messageText = messageText;
        this.sender = sender;
        this.image = image;
    }


    public Message(String subject, String messageText) {
        this.subject = subject;
        this.messageText = messageText;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String messageText) {
        this.subject = subject;
    }


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
