package com.example.clock.database;

import android.util.Base64;
import android.util.Log;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseDao {
    private Connection conn = null;
    private static DatabaseDao instance = null;
    public static int checkUserPasswordFlag = 1010;
    public static int checkUserUniqueFlag = 1020;
    public static int insertUserPasswordFlag = 1030;
    public static int getUserInformationFlag = 1040;
    public static int updateUserInformationFlag = 1050;
    public static int getUserTimeSetFlag = 1060;
    public static int updateUserTimeSetFlag = 1070;
    public static int getUserHistoryFlag = 1080;
    public static int insertUserRecordFlag = 1090;
    public static int getRankLimitedFlag = 1100;

    public static void main(String[] args) {
        ArrayList<HashMap<String, String>> list = DatabaseDao.
                getInstance().getRankLimited("10022");
        for (HashMap map : list) {
            System.out.println(map);
        }
    }

    private DatabaseDao() {
        String url = "jdbc:mysql://rm-uf6dwiuhh0val89vaho.mysql.rds.aliyuncs.com:3306/xie_2035060729?userSSL=true";
        String user = "xie_2035060729";
        String password = "xie_2035060729";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.i("Database", "connect");
            conn = DriverManager.getConnection(url, user, password);
            Log.i("Database", conn.toString());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseDao getInstance() {
        if (instance == null)
            synchronized (DatabaseDao.class) {
                instance = new DatabaseDao();
            }
        return instance;
    }


    public String insertUserPassword(String userName, String password) {
        PreparedStatement ps = null;
        String register = "Insert into user_password (username,password) " +
                "values(?,?)";
        String getId = "Select userid from user_password where username=?";
        try {
            ps = conn.prepareStatement(register);
            ps.setString(1, userName);
            ps.setString(2, password);
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(getId);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("userid");
            return String.valueOf(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, String> getUserInformation(String userID) {
        PreparedStatement ps;
        HashMap<String, String> map = null;
        String sql = "Select * from user_info_view where userid=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                map = new HashMap<>();
                map.put("userName", rs.getString("userName"));
                map.put("userSex", rs.getString("userSex"));
                map.put("userAge", rs.getString("userAge"));
                map.put("userAvatar", rs.getString("userAvatar"));
                map.put("userResume", rs.getString("userResume"));
                map.put("userTime", rs.getString("allTime"));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Boolean updateUserInformation(String userID, HashMap<String, String> userInfo) {
        PreparedStatement ps = null;
        try {
            for (String key : userInfo.keySet()) {
                String sql = String.format("Update user_info set %s=? where userid=?", key);
                ps = conn.prepareStatement(sql);
                ps.setString(1, userInfo.get(key));
                ps.setString(2, userID);
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<HashMap<String, String>> getUserHistory(String userID) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        String sql = "select * from user_history where userid=? order by recordtime desc";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new HashMap() {{
                    put("recordTitle", rs.getString("recordTitle"));
                    String time = rs.getString("recordTime");
                    put("recordTime", time.substring(0, time.length() - 2));
                    put("recordLength", rs.getString("recordLength"));
                }});
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean insertUserRecord(String userID, HashMap<String, String> recordInfo) {
        PreparedStatement ps;
        String sql = "Insert Into user_history values(?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, recordInfo.get("recordTitle"));
            ps.setString(3, recordInfo.get("recordTime"));
            ps.setString(4, recordInfo.get("recordLength"));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<HashMap<String, String>> getRankLimited(String userID) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        String sql = "select * from user_rank where 1=1 ";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            for (int i = 0; i < 3; i++) {
                if (rs.next()) {
                    HashMap<String, String> info = new HashMap<>();
                    info.put("userId", rs.getString("userid"));
                    info.put("userName", rs.getString("userName"));
                    info.put("userAvatar", rs.getString("userAvatar"));
                    info.put("userResume", rs.getString("userResume"));
                    info.put("allTime", rs.getString("allTime"));
                    info.put("userRank", rs.getString("userRank"));
                    result.add(info);
                } else {
                    result.add(new HashMap() {{
                        put("userId", "00000");
                    }});
                }
            }
            ps.close();
            ps = conn.prepareStatement(sql + "and userid=?");
            ps.setString(1, userID);
            rs.close();
            rs = ps.executeQuery();
            int rank = 0;
            if (rs.next()) {
                rank = rs.getInt("userrank") - 1;
            }
            rs.close();
            ps.close();
            sql += "and userrank=?";
            for (int i = 0; i < 3; i++) {
                if (rank <= 0) {
                    rank++;
                    result.add(new HashMap() {{
                        put("userId", "00000");
                    }});
                    continue;
                }
                ps = conn.prepareStatement(sql);
                ps.setInt(1, rank);
                rs = ps.executeQuery();
                if (rs.next()) {
                    HashMap<String, String> info = new HashMap<>();
                    info.put("userId", rs.getString("userid"));
                    info.put("userName", rs.getString("userName"));
                    info.put("userAvatar", rs.getString("userAvatar"));
                    info.put("userResume", rs.getString("userResume"));
                    info.put("allTime", rs.getString("allTime"));
                    info.put("userRank", rs.getString("userRank"));
                    result.add(info);
                } else {
                    result.add(new HashMap() {{
                        put("userId", "00000");
                    }});
                }
                rank++;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String checkUserPassword(String userName, String password) throws SQLException {
        Statement statement= conn.createStatement();
        String sql="select userid from user_password where username= '"+userName+"'and password='"+password+"'";
        ResultSet rs= statement.executeQuery(sql);
        rs.next();
        return rs.getString("userid");
    }

    public Boolean checkUserUnique(String userName) throws SQLException {
        Statement statement= conn.createStatement();
        String sql="select userName from user_password where username= '"+userName+"'";
        ResultSet rs=statement.executeQuery(sql);
        return rs.next();
    }
    public int getUserTimeSet(String userID) throws SQLException {
        Statement statement= conn.createStatement();
        String sql="select usertimeset from user_info where userid="+userID;
        ResultSet rs=statement.executeQuery(sql);
        rs.next();
        int usertimeset=rs.getInt("usertimeset");
        return usertimeset;
    }
    public Boolean updateUserTimeSet(String userID, int timeLength) throws SQLException {
        Statement statement= conn.createStatement();
        String sql="update user_info set usertimeset= '"+timeLength+"' where userid="+userID;
        boolean flag=statement.execute(sql);
        return flag;
    }
}
