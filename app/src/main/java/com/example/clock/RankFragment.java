package com.example.clock;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.example.clock.database.DatabaseDao;
import com.example.clock.database.DatabaseThread;
import com.example.clock.util.BitmapUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class RankFragment extends Fragment {

    private ImageView firstAvatar;
    private ImageView secondAvatar;
    private ImageView thirdAvatar;
    private ImageView beforeAvatar;
    private ImageView youAvatar;
    private ImageView afterAvatar;
    private TextView firstName;
    private TextView firstTime;
    private TextView secondName;
    private TextView secondTime;
    private TextView thirdName;
    private TextView thirdTime;
    private TextView beforeName;
    private TextView beforeTime;
    private TextView beforeResume;
    private TextView beforeRank;
    private TextView youName;
    private TextView youTime;
    private TextView youResume;
    private TextView youRank;
    private TextView afterName;
    private TextView afterTime;
    private TextView afterResume;
    private TextView afterRank;

    private String userId ;

    private class RankHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == DatabaseDao.getRankLimitedFlag){
                ArrayList<HashMap<String,String>> rankAll=(ArrayList<HashMap<String,String>>) msg.getData().getSerializable("value");
                int i=0;
                {
                    HashMap<String,String> rank = rankAll.get(i);
                    firstAvatar.setBackgroundDrawable(circle(BitmapUtil.Base642Bitmap(rank.get("userAvatar"))));
                    firstName.setText(rank.get("userName"));
                    firstName.setText(rank.get("allTime"));
                    i++;
                }
                {
                    HashMap<String,String> rank = rankAll.get(i);
                    secondAvatar.setBackgroundDrawable(circle(BitmapUtil.Base642Bitmap(rank.get("userAvatar"))));
                    secondName.setText(rank.get("userName"));
                    secondName.setText(rank.get("allTime"));
                    i++;
                }
                {
                    HashMap<String,String> rank = rankAll.get(i);
                    thirdAvatar.setBackgroundDrawable(circle(BitmapUtil.Base642Bitmap(rank.get("userAvatar"))));
                    thirdName.setText(rank.get("userName"));
                    thirdName.setText(rank.get("allTime"));
                    i++;
                }
                {
                    HashMap<String,String> rank = rankAll.get(i);
                    beforeAvatar.setImageBitmap(BitmapUtil.Base642Bitmap(rank.get("userAvatar")));
                    beforeName.setText(rank.get("userName"));
                    beforeName.setText(rank.get("allTime"));
                    beforeRank.setText("排名："+rank.get("userRank"));
                    beforeResume.setText(rank.get("userResume"));
                    i++;
                }
                {
                    HashMap<String,String> rank = rankAll.get(i);
                    youAvatar.setImageBitmap(BitmapUtil.Base642Bitmap(rank.get("userAvatar")));
                    youName.setText(rank.get("userName"));
                    youName.setText(rank.get("allTime"));
                    youRank.setText("排名："+rank.get("userRank"));
                    youResume.setText(rank.get("userResume"));
                    i++;
                }
                {
                    HashMap<String,String> rank = rankAll.get(i);
                    afterAvatar.setImageBitmap(BitmapUtil.Base642Bitmap(rank.get("userAvatar")));
                    afterName.setText(rank.get("userName"));
                    afterName.setText(rank.get("allTime"));
                    afterRank.setText("排名："+rank.get("userRank"));
                    afterResume.setText(rank.get("userResume"));
                }



            }
        }

        private RoundedBitmapDrawable circle(Bitmap rec){
            RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),rec);
            roundedBitmapDrawable.setCornerRadius(500);
            roundedBitmapDrawable.setAntiAlias(true);
            return roundedBitmapDrawable;
        }
    };

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
        View view = inflater.inflate(R.layout.activity_rank, container, false);
        firstAvatar = view.findViewById(R.id.First_img);
        firstName = view.findViewById(R.id.First_id);
        firstTime = view.findViewById(R.id.First_time);
        secondAvatar = view.findViewById(R.id.Second_img);
        secondName = view.findViewById(R.id.Second_id);
        secondTime = view.findViewById(R.id.Second_time);
        thirdAvatar = view.findViewById(R.id.Third_img);
        thirdName = view.findViewById(R.id.Third_id);
        thirdTime = view.findViewById(R.id.Third_time);
        beforeAvatar = view.findViewById(R.id.before_img);
        beforeName = view.findViewById(R.id.before_name);
        beforeTime = view.findViewById(R.id.before_time);
        beforeResume = view.findViewById(R.id.before_resume);
        beforeRank = view.findViewById(R.id.before_rank);
        youAvatar = view.findViewById(R.id.you_img);
        youName = view.findViewById(R.id.you_name);
        youTime = view.findViewById(R.id.you_time);
        youResume = view.findViewById(R.id.you_resume);
        youRank = view.findViewById(R.id.you_rank);
        afterAvatar = view.findViewById(R.id.after_img);
        afterName = view.findViewById(R.id.after_name);
        afterTime = view.findViewById(R.id.after_time);
        afterResume = view.findViewById(R.id.after_resume);
        afterRank = view.findViewById(R.id.after_rank);

        BodyActivity bodyActivity =(BodyActivity) getActivity();
        userId = bodyActivity.getUserId();
        DatabaseThread.getRankLimited(new RankHandler(),userId);

        return view;

    }



}
