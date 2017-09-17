package com.volnoor.gogo;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Eugene on 16.09.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent = new Intent("data");
        intent.putExtra("userId", remoteMessage.getData().get("userId"));
        intent.putExtra("changesCount", remoteMessage.getData().get("changesCount"));
        broadcaster.sendBroadcast(intent);
    }
}