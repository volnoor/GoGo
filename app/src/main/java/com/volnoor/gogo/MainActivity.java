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

        usersList = new ArrayList<>();
        usersList.add(new User("one"));
        usersList.add(new User("two"));
        mRecyclerViewUsers.setAdapter(new UsersAdapter(usersList, new OnUserClickListener() {
            @Override
            public void onItemClick(User user) {
                System.out.println(user.getLogin());
            }
        }));

        load();
    }

    private void load() {
        ApiService api = RetrofitClient.getApiService();

//        Call<UsersList> call = api.getMyJSON();
//
//        call.enqueue(new Callback<UsersList>() {
//            @Override
//            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
//                if(response.isSuccessful()){
//                    usersList =response.body().getUsers();
//                    mRecyclerViewUsers.getAdapter().notifyDataSetChanged();
//
//                    System.out.println("onResponse");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UsersList> call, Throwable t) {
//                System.out.println("onFailure" +t.getMessage());
//            }
//        });

        Call<List<User>> call = api.getMyJSON();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();

                    System.out.println("onResponse " + users.size());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println("onFailure" + t.getMessage());
            }
        });
    }
}

/* - витягнути юзерів в список recyclerview https://api.github.com/users
 * - при тапі на одного юзера показати його репозиторії https://api.github.com/users/{login}/repos
 * - всі дані повинні кешитись в realm
 * - підключити FCM і зробити кастомний пуш з полем data в якому буде userId (id користувача) і changesCount - поле, яке треба показати в списку користувачів в червоному кружечку.
 * Відкриваєм апп, бачим список користувачів гітхаба, якщо приходить пуш з полем дата, треба його розпарсити, і внести зміни в реалм обєкт користувача, змінити поле changesCount, і апдейтнути список" - ось так :)
 */
