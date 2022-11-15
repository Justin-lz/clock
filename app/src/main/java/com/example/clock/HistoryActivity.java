package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class HistoryActivity extends AppCompatActivity {
    private ListView LV1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        LV1=(ListView) findViewById(R.id.lv1);
        LV1.setAdapter(new MyListAdapter(HistoryActivity.this));  //安装自定义列表适配器
        LV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //点击事件监听器

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(HistoryActivity.this, "点击pos"+i, Toast.LENGTH_SHORT).show();
            }
        });
        LV1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //长按事件监听器
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(HistoryActivity.this, "长按 pos:"+i, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }}