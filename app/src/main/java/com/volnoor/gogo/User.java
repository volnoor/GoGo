package com.volnoor.gogo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene on 14.09.2017.
 */

class User {
    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("id")
    @Expose
    private Integer id;

    public String getLogin() {
        return login;
    }

    public Integer getId() {
        return id;
    }
}
