package com.example.dailyrozgar.WorkerDB.Class;

public class Worker {
    private Id _id;
    private String first;
    private String last;
    private String sex;
    private String phone;
    private String prof;;
    private String base;
    private String loc;
    private String city;
    private String state;
    private String zip;
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    private int forSex,forBase;

    public Id get_id() {
        return _id;
    }

    public int getForSex() {
        return forSex;
    }

    public void setForSex(int forSex) {
        this.forSex = forSex;
    }

    public int getForBase() {
        return forBase;
    }

    public void setForBase(int forBase) {
        this.forBase = forBase;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
