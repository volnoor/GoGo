package com.volnoor.gogo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
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

        String login = getIntent().getStringExtra("login");
        setTitle(login);
        if (isOnline()) {
            load(login);
        } else {
            loadFromRealm(login);
        }
    }

    private void load(String login) {
        ApiService api = RetrofitClient.getApiService();

        Call<List<Repo>> call = api.getUserReposList(login);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                mRepos.addAll(response.body());
                mAdapter.notifyDataSetChanged();
                saveToRealm();
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                System.out.println("onFailure");
            }
        });
    }

    private void saveToRealm() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(mRepos);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Snackbar.make(findViewById(R.id.repo_root), "Saved", Snackbar.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Snackbar.make(findViewById(R.id.repo_root), "Failed to save", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFromRealm(String login) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Repo> realmResults = realm.where(Repo.class)
                .beginsWith("fullName", login)
                .findAll();
        mRepos.addAll(realm.copyFromRealm(realmResults));

        mAdapter.notifyDataSetChanged();

        Snackbar.make(findViewById(R.id.repo_root), "Loaded from cache", Snackbar.LENGTH_SHORT).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
