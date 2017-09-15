package com.volnoor.gogo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Eugene on 15.09.2017.
 */

public interface ApiService {
    @GET("/users")
    Call<List<User>> getUsersList();

    @GET("/users/{login}/repos?per_page=100")
    Call<List<Repo>> getUserReposList(@Path("login") String login);
}
