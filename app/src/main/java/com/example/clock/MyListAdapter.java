package com.example.clock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class MyListAdapter extends BaseAdapter {

    private Context mcontext;
    private LayoutInflater mLayoutIntlater;
    private ArrayList<HashMap<String,String>> histroyInfo;
    private int count;

    public MyListAdapter(Context context, ArrayList<HashMap<String,String>> histroyInfo){
        this.mcontext=context;
        mLayoutIntlater=LayoutInflater.from(context);
        this.histroyInfo=histroyInfo;
        count= histroyInfo.size();
    }
    @Override
    public int getCount() {
        return  count;
    }

    @Override
    public Object getItem(int i) {
        return histroyInfo.get(i-1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        public ImageView imageview;
        public TextView tvtitle,tvtimeStart,tvcontent,tvtimeEnd;

    }
    @SuppressLint("WrongViewCast")
    @Override  //返回每一行或者每一列长什么样子，Listview中最重要的方法
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder=null;
       if(view==null){
           view=mLayoutIntlater.inflate(R.layout.layout_list_item,null);
           holder=new ViewHolder();
           holder.tvtitle=(TextView) view.findViewById(R.id.tv_title);
           holder.tvtimeStart=(TextView) view.findViewById(R.id.tv_time_start);
           holder.tvtimeEnd=(TextView) view.findViewById(R.id.tv_time_end);
           holder.tvcontent=(TextView) view.findViewById(R.id.tv_content);
           view.setTag(holder);
       }
       else {
           holder=(ViewHolder) view.getTag();
       }

       //给控件赋值
        HashMap<String,String> histroy = histroyInfo.get(i);

        holder.tvtitle.setText("自律事件："+histroy.get("recordTitle"));
        holder.tvtimeStart.setText("开始时间");
        holder.tvtimeEnd.setText(histroy.get("recordTime"));
        holder.tvcontent.setText("持续时长："+histroy.get("recordLength")+"分钟");
        return view;
    }
}
