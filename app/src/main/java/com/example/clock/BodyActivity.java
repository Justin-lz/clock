package com.example.clock;

import java.lang.reflect.Field;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;



public class BodyActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    private static final String TAG = "tag";
    private ImageView me;
    private ImageView more;
    private FragmentTabHost tabHost;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        initIcon();

        tabHost=(FragmentTabHost)super.findViewById(android.R.id.tabhost);
        //在tabHost创建FragmentLayout , contentLayout的id就是布局文件里面FragmentLayout的id，如果在布局文件FragmentLayout添加子元素，那么实际上相互切换的3个FragmentLayout都会被添加子元素
        tabHost.setup(this,super.getSupportFragmentManager(),R.id.activity_home_container);
        //getTabWidget()方法表示获取切换卡，setDividerDrawable(null)表示没有分割线
        tabHost.getTabWidget().setDividerDrawable(null);
        //点击下面5个图标的时候会变色，就是setOnTabChangedListener(this)方法影响的，如果没有这个方法，那么点击图标不会变色
        tabHost.setOnTabChangedListener(this);
        initTab();

    }

    private void initIcon() {
        me = (ImageView) super.findViewById(R.id.img_me);
        more = (ImageView) super.findViewById(R.id.img_more);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(BodyActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
        //右上角菜单栏
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(BodyActivity.this,more);
                //加载菜单资源
                menu.getMenuInflater().inflate(R.menu.menu, menu.getMenu());
                //设置点击事件的响应
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.menu_change:
                                startActivity(new Intent(BodyActivity.this,LoginShareActivity.class));
                                break;
                            case R.id.menu_finish:
                                Toast.makeText(BodyActivity.this, "finish", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_logout:
                                startActivity(new Intent(BodyActivity.this,LoginShareActivity.class));
                                break;
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });

    }



    //initTab()方法表示没有点击图标的时候的初始状态
    private void initTab() {
        String tabs[]=TabDb.getTabsTxt();
        for(int i=0;i<tabs.length;i++){
            TabHost.TabSpec tabSpec=tabHost.newTabSpec(tabs[i]).setIndicator(getTabView(i));
            //addTab()方法表示添加导航栏
            tabHost.addTab(tabSpec,TabDb.getFragment()[i],null);
            tabHost.setTag(i);
            tabHost.setCurrentTab(1);
        }
    }

    private View getTabView(int idx) {
        //引入footer_tabs.xml布局，这个布局包含了导航栏的图片和文本，我这里通过设置布局文件参数把文本隐藏了
        View view= LayoutInflater.from(this).inflate(R.layout.foot_tab,null);

        ((TextView)view.findViewById(R.id.tvTab)).setText(TabDb.getTabsTxt()[idx]);
        //下面的判断语句表示图标颜色的默认状态，第一个图标默认点亮，其他默认不点亮
        if(idx==0){

            ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.RED);
            //设置为点亮的图标
            ((ImageView)view.findViewById(R.id.ivImg)).setImageResource(TabDb.getImgChoId()[idx]);
        }else{
            //设置为点不亮的图标
            ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.GRAY);
            ((ImageView)view.findViewById(R.id.ivImg)).setImageResource(TabDb.getImgNotId()[idx]);
        }
        return view;
    }

    //onTabChanged自带方法
    @Override
    public void onTabChanged(String tabId) {
        updateTab();
    }

    //点击图标就会调用updateTab()方法
    private void updateTab(){
        //TabWidget表示切换卡
        TabWidget tabw=tabHost.getTabWidget();
        for(int i=0;i<tabw.getChildCount();i++){
            View view=tabw.getChildAt(i);
            ImageView iv=(ImageView)view.findViewById(R.id.ivImg);
            if(i==tabHost.getCurrentTab()){
                ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.RED);
                iv.setImageResource(TabDb.getImgChoId()[i]);
            }else{
                ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.GRAY);
                iv.setImageResource(TabDb.getImgNotId()[i]);
            }

        }
    }


}