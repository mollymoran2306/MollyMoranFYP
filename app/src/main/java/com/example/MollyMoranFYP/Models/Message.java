package com.example.MollyMoranFYP.Models;

public class Message {
    //private int userID;
    private String subject;
    private String messageText;
    private String image;


    public Message() {
    }

    public Message(String subject, String messageText, String image) {
        this.subject = subject;
        this.messageText = messageText;
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
}
