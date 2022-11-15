package com.example.clock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clock.widget.TextProgressCircle;

public class ClockFragment extends Fragment {

    private Button btn_runnable;
    private Button btn_settime;
    private Button btn_progress;
    private TextView tv_result;
    private TextView tv_time;
    private int mCount = 0;
    private int mCounts = 0;
    private  int progress;
    private  int sum=0;
    private  int x=0;
    private  int y=0;
    private boolean flag=false;
    private ProgressBar pb_progress;
    private EditText et_progress;
    private static int time=0;
    private TextProgressCircle tpc_progress;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_body1, container, false);
        btn_runnable = (Button) view.findViewById(R.id.btn_runnable);
        btn_settime = (Button) view.findViewById(R.id.btn_settime);
//        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_time = (TextView) view.findViewById(R.id.tv_time0);
        pb_progress = (ProgressBar) view.findViewById(R.id.pb_progress);
        et_progress = (EditText) view.findViewById(R.id.et_progress);
        btn_progress= view.findViewById(R.id.btn_progress);
        tpc_progress = (TextProgressCircle) view.findViewById(R.id.tpc_progress);
        tpc_progress.setProgress(0, "开始学习！",-1);
        setListenners();




        return view;
    }


    private void setListenners() {
        OnClick onClick = new OnClick();
        btn_runnable.setOnClickListener(onClick);
        btn_settime.setOnClickListener(onClick);
        btn_progress.setOnClickListener(onClick);
    }


    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.btn_runnable:
                    if (bStart == false) {
                        btn_runnable.setText("暂停");
                        mHandler.post(mCounter);
                    } else {
                        btn_runnable.setText("继续");
                        mHandler.removeCallbacks(mCounter);
                    }
                    bStart = !bStart;
                    break;
                case R.id.btn_settime:
                    mCounts = Integer.parseInt(tv_time.getText().toString());//获取单次任务总时长
                    mCount=mCounts*60; //单位s
                    x=mCount;
                    break;
                case R.id.btn_progress:
                    progress = Integer.parseInt(et_progress.getText().toString());
//                    time=progress;
                    pb_progress.setMax(progress);//设置进度条总时长min
//             pb_progress.setProgress(progress);
                    break;
            }
            if(intent!=null) startActivity(intent);
        }
    }

    private boolean bStart = false;
    private Handler mHandler = new Handler();

    public String secToTime(int time) {

        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time < 0) {

            return "00分00秒";}
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60 + 1;
                timeStr = minute + "分" + second + "秒";
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60 + 1;
                timeStr = hour + "时" + minute + "分" + second + "秒";
            }
        }
        return timeStr;
    }


    private Runnable mCounter = new Runnable() {
        @Override
        public void run() {

            --mCount;
            y=mCount*100/x;
            if (mCount < 0) {
                sum+=mCounts;
                pb_progress.setProgress(sum);
                btn_runnable.setText("完成！！");
                mHandler.removeCallbacks(mCounter);
                tpc_progress.setProgress(0, secToTime(mCount),-1);
//                tv_result.setText(secToTime(mCount));
            }
            else{
                tpc_progress.setProgress(y, secToTime(mCount),-1);
//            tv_result.setText(secToTime(mCount));
                mHandler.postDelayed(this, 1000);}
        }
    };


}
