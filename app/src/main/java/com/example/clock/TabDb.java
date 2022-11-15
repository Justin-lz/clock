package com.example.clock;

public class TabDb {
    public static int[] getImgId(){
        return new int[]{R.drawable.user,R.drawable.main,R.drawable.rank};
    }

    public static Class[] getFragment(){
        return new Class[]{MyFragment.class, ClockFragment.class, RankFragment.class};
    }

    public static String[] getTabsTxt(){
        return new String[]{"我的","时钟","排行"};
    }
}
