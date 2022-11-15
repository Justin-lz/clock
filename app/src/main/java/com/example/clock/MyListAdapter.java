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

public class MyListAdapter extends BaseAdapter {

    private Context mcontext;
    private LayoutInflater mLayoutIntlater;

    public MyListAdapter(Context context){
        this.mcontext=context;
        mLayoutIntlater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return  6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder{
        public ImageView imageview;
        public TextView tvtitle,tvtime,tvcontent;

    }
    @SuppressLint("WrongViewCast")
    @Override  //返回每一行或者每一列长什么样子，Listview中最重要的方法
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder=null;
       if(view==null){
           view=mLayoutIntlater.inflate(R.layout.layout_list_item,null);
           holder=new ViewHolder();
           holder.tvtitle=(TextView) view.findViewById(R.id.tv_title);
           holder.tvtime=(TextView) view.findViewById(R.id.tv_time);
           holder.tvcontent=(TextView) view.findViewById(R.id.tv_content);
           view.setTag(holder);
       }
       else {
           holder=(ViewHolder) view.getTag();
       }

       //给控件赋值
        holder.tvtitle.setText("这是标题");
        holder.tvtime.setText("2025-05-04");
        holder.tvcontent.setText("这是内容");
        return view;
    }
}
