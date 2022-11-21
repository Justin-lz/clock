package com.example.clock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.clock.database.DatabaseDao;
import com.example.clock.database.DatabaseThread;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {
    private ListView LV1;
    private String userId;


    private class historyHandle extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == DatabaseDao.getUserHistoryFlag) {
                LV1.setAdapter(new MyListAdapter(HistoryActivity.this, (ArrayList<HashMap<String,String>>) msg.getData().getSerializable("value")));  //安装自定义列表适配器
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        userId = getIntent().getStringExtra("userId");
        LV1 = (ListView) findViewById(R.id.lv1);

        DatabaseThread.getUserHistory(new historyHandle(),userId);


    }
}