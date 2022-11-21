package com.example.clock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clock.database.DatabaseDao;
import com.example.clock.database.DatabaseThread;


public class LoginForgetActivity extends AppCompatActivity implements OnClickListener {

    private EditText et_usename;
    private EditText et_password_second;
    private EditText et_verifycode;
    private String mVerifyCode;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        et_usename = (EditText) findViewById(R.id.et_password_first);
        et_password_second = (EditText) findViewById(R.id.et_password_second);
        et_verifycode = (EditText) findViewById(R.id.et_verifycode);
        findViewById(R.id.btn_verifycode).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        mPhone = "18888888888";
    }

    private class forgetHandler extends Handler {
        String username, password;

        public forgetHandler(String user, String pass) {

            username = user;
            password = pass;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Boolean flag = false;
            if (msg.what == DatabaseDao.checkUserUniqueFlag) {
                flag = msg.getData().getBoolean("value");
                System.out.println(flag);
                if (flag) {
                    Toast.makeText(LoginForgetActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseThread.insertUserPassword(this, username, password);
                    Toast.makeText(LoginForgetActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_verifycode) {
            mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginForgetActivity.this);
            builder.setTitle("请记住验证码");
            builder.setMessage("手机号" + mPhone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
            builder.setPositiveButton("好的", null);
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.btn_confirm) {
            String username = et_usename.getText().toString();
            String password_second = et_password_second.getText().toString();
            System.out.println(username + password_second);
            forgetHandler forgetHandler = new forgetHandler(username, password_second);
            DatabaseThread.checkUserUnique(forgetHandler, username);

        }
    }

}
