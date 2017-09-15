package com.volnoor.gogo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewUsers = (RecyclerView) findViewById(R.id.recycler_view_users);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewUsers.setLayoutManager(layoutManager);

        List<User> users = new ArrayList<>();
        users.add(new User("one"));
        users.add(new User("two"));
        mRecyclerViewUsers.setAdapter(new UsersAdapter(users, new OnUserClickListener() {
            @Override
            public void onItemClick(User user) {
                System.out.println(user.getLogin());
            }
        }));
    }
}

/**
 * - витягнути юзерів в список recyclerview https://api.github.com/users
 * - при тапі на одного юзера показати його репозиторії https://api.github.com/users/{login}/repos
 * - всі дані повинні кешитись в realm
 * - підключити FCM і зробити кастомний пуш з полем data в якому буде userId (id користувача) і changesCount - поле, яке треба показати в списку користувачів в червоному кружечку.
 * Відкриваєм апп, бачим список користувачів гітхаба, якщо приходить пуш з полем дата, треба його розпарсити, і внести зміни в реалм обєкт користувача, змінити поле changesCount, і апдейтнути список" - ось так :)
 */
