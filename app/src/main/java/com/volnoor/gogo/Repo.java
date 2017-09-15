package com.volnoor.gogo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene on 15.09.2017.
 */

public class Repo {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
