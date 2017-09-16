package com.volnoor.gogo;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Eugene on 16.09.2017.
 */

public class GoGo extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
