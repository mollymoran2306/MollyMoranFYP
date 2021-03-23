package com.example.MollyMoranFYP.Models;

public class Message {
    //private int userID;
    private String subject;
    private String messageText;
    private String image;
    private String sender;
    private String full;
    private String profilePic;
    private String date;
    private String time;


    public Message() {
    }

    public Message(String subject, String messageText, String image, String sender, String profilePic, String full, String date, String time) {
        this.subject = subject;
        this.messageText = messageText;
        this.image = image;
        this.sender = sender;
        this.profilePic = profilePic;
        this.full = full;
        this.date = date;
        this.time = time;

    }

    public Message(String subject, String messageText, String sender,String profilePic, String full, String date, String time) {
        this.subject = subject;
        this.messageText = messageText;
        this.profilePic = profilePic;
        this.sender = sender;
        this.full = full;
        this.date = date;
        this.time = time;

    }



//    public Message(String subject, String messageText, String sender, String id) {
//        this.subject = subject;
//        this.messageText = messageText;
//        this.sender = sender;
//        this.image = image;
//        this.full = id;
//    }


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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }




}
