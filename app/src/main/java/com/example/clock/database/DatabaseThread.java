package com.example.storage.database;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.sql.SQLException;
import java.util.HashMap;
import com.example.clock.database.DatabaseDao

public class DatabaseThread {
    public static void checkUserPassword(Handler handler,String userName,String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("value",DatabaseDao.getInstance().checkUserPassword(userName,password));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                message.setData(bundle);
                message.what=DatabaseDao.checkUserPasswordFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void checkUserUnique(Handler handler,String userName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putBoolean("value",DatabaseDao.getInstance().checkUserUnique(userName));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                message.setData(bundle);
                message.what=DatabaseDao.checkUserUniqueFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void getUserTimeSet(Handler handler,String userID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putInt("value",DatabaseDao.getInstance().getUserTimeSet(userID));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                message.setData(bundle);
                message.what=DatabaseDao.getUserTimeSetFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void updateUserTimeSet(Handler handler,String userID, int timeLength){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putBoolean("value",DatabaseDao.getInstance().updateUserTimeSet(userID,timeLength));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                message.setData(bundle);
                message.what=DatabaseDao.updateUserTimeSetFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void insertUserPassword(Handler handler,String userName, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("value",DatabaseDao.getInstance().insertUserPassword(userName,password));
                message.setData(bundle);
                message.what=DatabaseDao.insertUserPasswordFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void getUserInformation(Handler handler,String userID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                bundle.putSerializable("value",DatabaseDao.getInstance().getUserInformation(userID));
                message.setData(bundle);
                message.what=DatabaseDao.getUserInformationFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void updateUserInformation(Handler handler,String userID, HashMap<String, String> userInfo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                bundle.putBoolean("value",DatabaseDao.getInstance().updateUserInformation(userID,userInfo));
                message.setData(bundle);
                message.what=DatabaseDao.updateUserInformationFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void getUserHistory(Handler handler,String userID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                bundle.putSerializable("value",DatabaseDao.getInstance().getUserHistory(userID));
                message.setData(bundle);
                message.what=DatabaseDao.getUserHistoryFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void insertUserRecord(Handler handler,String userID, HashMap<String, String> recordInfo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                bundle.putBoolean("value",DatabaseDao.getInstance().insertUserRecord(userID,recordInfo));
                message.setData(bundle);
                message.what=DatabaseDao.insertUserRecordFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void getRankLimited(Handler handler,String userID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Bundle bundle=new Bundle();
                bundle.putSerializable("value",DatabaseDao.getInstance().getRankLimited(userID));
                message.setData(bundle);
                message.what=DatabaseDao.getRankLimitedFlag;
                handler.sendMessage(message);
            }
        }).start();
    }
}
