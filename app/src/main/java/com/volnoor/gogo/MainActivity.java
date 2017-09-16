package com.volnoor.gogo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewUsers;
    private List<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewUsers = (RecyclerView) findViewById(R.id.recycler_view_users);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewUsers.setLayoutManager(layoutManager);

        if (isOnline()) {
            load();
        } else {
            loadFromRealm();
        }
    }

    private void load() {
        ApiService api = RetrofitClient.getApiService();

        Call<List<User>> call = api.getUsersList();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    System.out.println("onResponse");

                    usersList = response.body();

                    saveToRealm();

                    mRecyclerViewUsers.setAdapter(new UsersAdapter(usersList, new OnUserClickListener() {
                        @Override
                        public void onItemClick(User user) {
                            Intent intent = new Intent(MainActivity.this, RepoActivity.class);
                            intent.putExtra("login", user.getLogin());
                            startActivity(intent);
                        }
                    }));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println("onFailure" + t.getMessage());
            }
        });
    }

    private void saveToRealm() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(usersList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Snackbar.make(findViewById(R.id.root), "Saved", Snackbar.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Snackbar.make(findViewById(R.id.root), "Failed to save", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFromRealm() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<User> realmResults = realm.where(User.class).findAll();
        usersList = realm.copyFromRealm(realmResults);

        mRecyclerViewUsers.setAdapter(new UsersAdapter(usersList, new OnUserClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(MainActivity.this, RepoActivity.class);
                intent.putExtra("login", user.getLogin());
                startActivity(intent);
            }
        }));

        Snackbar.make(findViewById(R.id.root), "Loaded from cache", Snackbar.LENGTH_SHORT).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

/* - витягнути юзерів в список recyclerview https://api.github.com/users +
 * - при тапі на одного юзера показати його репозиторії https://api.github.com/users/{login}/repos +
 * - всі дані повинні кешитись в realm
 * - підключити FCM і зробити кастомний пуш з полем data в якому буде userId (id користувача) і changesCount - поле, яке треба показати в списку користувачів в червоному кружечку.
 * Відкриваєм апп, бачим список користувачів гітхаба, якщо приходить пуш з полем дата, треба його розпарсити, і внести зміни в реалм обєкт користувача, змінити поле changesCount, і апдейтнути список" - ось так :)
 */
