package com.example.clock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clock.database.DatabaseDao;
import com.example.clock.database.DatabaseThread;
import com.example.clock.util.BitmapUtil;
import com.example.clock.util.ViewUtil;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;


public class LoginShareActivity extends AppCompatActivity implements OnClickListener {

    private RadioGroup rg_login;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private EditText et_phone;
    private TextView tv_password;
    private EditText et_password;
    private Button btn_register;
    private CheckBox ck_remember;
    private Button btn_login;

    private int mRequestCode = 0;
    private int mType = 0;
    private boolean bRemember = false;
    private String mPassword = "111111";
    private String mVerifyCode;
    private SharedPreferences mShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_share);
        rg_login = (RadioGroup) findViewById(R.id.rg_login);
        rb_password = (RadioButton) findViewById(R.id.rb_password);
        rb_verifycode = (RadioButton) findViewById(R.id.rb_verifycode);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_password = (TextView) findViewById(R.id.tv_password);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_register = (Button) findViewById(R.id.btn_forget);
        ck_remember = (CheckBox) findViewById(R.id.ck_remember);
        btn_login = (Button) findViewById(R.id.btn_login);
        ck_remember.setOnCheckedChangeListener(new CheckListener());
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        mShared = getSharedPreferences("share_login", MODE_PRIVATE);
        String phone = mShared.getString("phone", "");
        String password = mShared.getString("password", "");
        et_phone.setText(phone);
        et_password.setText(password);

    }



    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.ck_remember) {
                bRemember = isChecked;
            }
        }
    }

    private class loginHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == DatabaseDao.checkUserPasswordFlag) {
                String userId = msg.getData().getString("value");
                if (userId!=null){
                    Intent intent = new Intent(LoginShareActivity.this, BodyActivity.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginShareActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    ;


    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        if (v.getId() == R.id.btn_forget) {
            if (rb_password.isChecked() == true) {
                Intent intent = new Intent(this, LoginForgetActivity.class);
                intent.putExtra("phone", phone);
                startActivityForResult(intent, mRequestCode);
            } else if (rb_verifycode.isChecked() == true) {
                mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
                builder.setPositiveButton("好的", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        } else if (v.getId() == R.id.btn_login) {
            if (rb_password.isChecked() == true) {
                DatabaseThread.checkUserPassword(new loginHandler(), phone, password);
                /*if (password.equals(mPassword) != true) {
                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    startActivity(new Intent(LoginShareActivity.this,BodyActivity.class));
                }*/
            } else if (rb_verifycode.isChecked() == true) {
                if (et_password.getText().toString().equals(mVerifyCode) != true) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else {



                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && data != null) {
            //用户密码已改为新密码
            mPassword = data.getStringExtra("new_password");
        }
    }

    //从修改密码页面返回登录页面，要清空密码的输入框
    @Override
    protected void onRestart() {
        et_password.setText("");
        super.onRestart();
    }

}