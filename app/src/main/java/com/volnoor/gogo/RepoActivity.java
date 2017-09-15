package com.volnoor.gogo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoActivity extends AppCompatActivity {

    private List<Repo> mRepos;
    private ReposAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_repos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mRepos = new ArrayList<>();
        mAdapter = new ReposAdapter(mRepos);
        recyclerView.setAdapter(mAdapter);

        load(getIntent().getStringExtra("login"));
    }

    private void load(String login) {
        ApiService api = RetrofitClient.getApiService();

        Call<List<Repo>> call = api.getUserReposList(login);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                mRepos.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                System.out.println("onFailure");
            }
        });
    }
}
