package com.volnoor.gogo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Eugene on 14.09.2017.
 */

public class User extends RealmObject {
    @SerializedName("login")
    @Expose
    @PrimaryKey
    private String login;

    @SerializedName("id")
    @Expose
    private Integer id;

    private Integer changesCount;

    public User() {
        login = "";
        id = 0;
        changesCount = 0;
    }

    public String getLogin() {
        return login;
    }

    public Integer getId() {
        return id;
    }

    public Integer getChangesCount() {
        return changesCount;
    }
}
