package com.volnoor.gogo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Eugene on 15.09.2017.
 */

public class Repo extends RealmObject {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    private String userLogin;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUserLogin() {
        return userLogin;
    }
}
