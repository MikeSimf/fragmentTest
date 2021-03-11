package ru.mike.fragmenttest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TestService extends Service {
    public static final String BROADCAST_ACTION = "ru.mike.fragmentTest.serviceBroadcast";
    public static final String PARAM_TEXT = "text";

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendBroadcast(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    class MyBinder extends Binder {
        TestService getService() {
            return TestService.this;
        }
    }
}