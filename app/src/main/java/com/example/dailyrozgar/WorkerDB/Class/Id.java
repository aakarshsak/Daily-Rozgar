package com.example.dailyrozgar.WorkerDB.Class;

import com.google.gson.annotations.SerializedName;

public class Id {
    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }
}
