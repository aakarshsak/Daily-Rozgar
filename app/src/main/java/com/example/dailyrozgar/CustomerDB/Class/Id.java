package com.example.dailyrozgar.CustomerDB.Class;

import com.google.gson.annotations.SerializedName;

public class Id {
    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }
}
