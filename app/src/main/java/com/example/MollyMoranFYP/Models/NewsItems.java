package com.example.MollyMoranFYP.Models;

public class NewsItems {
    //code here adapted from NewsApp by Satsih7897 on github url:https://github.com/Satish7897/NewsApp
    String title, author, date, urlToImage, url;
    public NewsItems() {

    }

    public NewsItems(String title, String author, String date, String urlToImage, String url) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.urlToImage = urlToImage;
        this.url = url; }

    public String getTitle() {return title; }
    public void setTitle(String title) {this.title = title; }
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}
    public String getUrlToImage() {return urlToImage;}
    public void setUrlToImage(String urlToImage) {this.urlToImage = urlToImage;}
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
}
