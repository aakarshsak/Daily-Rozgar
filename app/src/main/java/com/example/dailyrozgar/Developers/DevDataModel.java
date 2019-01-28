package com.example.dailyrozgar.Developers;

public class DevDataModel {

    public String name;
    public String desc;
    public int img;

    public DevDataModel(String name, String desc, int img) {
        this.name = name;
        this.desc = desc;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getImg() {
        return img;
    }
}
