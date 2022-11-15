package com.example.clock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=findViewById(R.id.btnclock);
        btn2=findViewById(R.id.btn_login);
        setListenners();
    }

    private void setListenners(){
        OnClick onClick=new OnClick();
        btn1.setOnClickListener(onClick);
        btn2.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent=null;
            switch (view.getId()){
                case R.id.btnclock:
                    intent=new Intent(MainActivity.this,BodyActivity.class);
                    break;
                case  R.id.btn_login:
                    intent=new Intent(MainActivity.this,LoginShareActivity.class);
                    break;

            }
            startActivity(intent);
        }
    }
}