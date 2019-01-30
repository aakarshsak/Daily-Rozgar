package com.example.dailyrozgar.WorkerDB.Class;

public class Worker {
    private Id _id;

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public Worker(Id _id, String user) {
        this._id = _id;
    }
}
