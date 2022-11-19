package com.example.clock;

public class TabDb {
    public static int[] getImgNotId(){
        return new int[]{R.drawable.mine_not,R.drawable.main_not,R.drawable.rank_not};
    }

    public static int[] getImgChoId(){
        return new int[]{R.drawable.mine_cho,R.drawable.main_cho,R.drawable.rank_cho};
    }

    public static Class[] getFragment(){
        return new Class[]{MyFragment.class, ClockFragment.class, RankFragment.class};
    }

    public static String[] getTabsTxt(){
        return new String[]{"我的","时钟","排行"};
    }
}
