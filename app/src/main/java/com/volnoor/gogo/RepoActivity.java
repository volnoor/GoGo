package com.volnoor.gogo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RepoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        System.out.println(getIntent().getStringExtra("login"));
    }
}
