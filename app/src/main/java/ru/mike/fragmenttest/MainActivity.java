package ru.mike.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    public static final String BROADCAST_ACTION = "ru.mike.fragmentTest.serviceBroadcast";
    public static final String PARAM_TEXT = "text";


    TextView tv;
    EditText et;
    Button btn;
    TestFragment fragment;
    BroadcastReceiver br;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = TestFragment.newInstance();
        fragmentTransaction.add(R.id.main, fragment);
        fragmentTransaction.commit();

        // создаем BroadcastReceiver
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String text = intent.getStringExtra(PARAM_TEXT);
                tv.setText(text);
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intentFilter);

     }

    @Override
    protected void onStart() {
        super.onStart();
        tv = (TextView) findViewById(R.id.tv);
        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener((View view) -> {
            // запускаем сервис и передаем текст по кнопке
            intent = new Intent(this, TestService.class).putExtra(PARAM_TEXT, et.getText().toString());
            startService(intent);
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
}