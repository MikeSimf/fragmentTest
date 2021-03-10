package ru.mike.fragmenttest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TestService extends Service {

    MyBinder binder = new MyBinder();

    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    void parseText(String text) {
        Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
        intent.putExtra(MainActivity.PARAM_TEXT, text);
        sendBroadcast(intent);
    }

    class MyBinder extends Binder {
        TestService getService() {
            return TestService.this;
        }
    }
}