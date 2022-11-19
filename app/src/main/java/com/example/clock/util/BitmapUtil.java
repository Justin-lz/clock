package com.example.webdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {
    private static final int targetResolution =300;
    public static String Image2Base64(Bitmap bitmap){
//        Bitmap bitmap=BitmapFactory.decodeFile(image);
//        int height=bitmap.getHeight();
        int width= bitmap.getWidth();
        if (width> targetResolution){
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] bytes= baos.toByteArray();
            Log.i("length",bytes.length+"");
            int sampleSize=width/ targetResolution;
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=sampleSize;
            bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes= baos.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
    public static Bitmap Base642Bitmap(String image){
        byte[] bytes= Base64.decode(image,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
