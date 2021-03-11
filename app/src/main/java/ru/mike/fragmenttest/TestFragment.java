package ru.mike.fragmenttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {
    public static final String BROADCAST_ACTION = "ru.mike.fragmentTest.serviceBroadcast";
    public static final String PARAM_TEXT = "text";

    TextView tv;
    EditText et;
    Button btn;
    BroadcastReceiver br;

    public static TestFragment newInstance(){
        return new TestFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tv = (TextView) getActivity().findViewById(R.id.tv);
        et = (EditText) getActivity().findViewById(R.id.et);
        btn = (Button) getActivity().findViewById(R.id.btn);

         btn.setOnClickListener((View view) -> {
             // запускаем сервис и передаем текст по кнопке
             Intent intent = new Intent(getActivity(), TestService.class).putExtra(PARAM_TEXT, et.getText().toString());
             getActivity().startService(intent);
         });

        // создаем BroadcastReceiver
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String text = intent.getStringExtra(PARAM_TEXT);
                tv.setText(text);
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        getActivity().registerReceiver(br, intentFilter);

        return inflater.inflate(R.layout.fragment_layout, container, false);
    }
}
