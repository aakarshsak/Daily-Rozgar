package com.example.dailyrozgar.Home.Model;

public class DataModel {

    private String title,description;
    private int image,id;

    public DataModel(String title,int _d, int image,String description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){ return description;}
}
