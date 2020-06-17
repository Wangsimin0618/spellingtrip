package com.spellingtrip.example.utils;

import android.content.Context;
import android.content.Intent;

/**
 * 活动管理类
 */
public class ActivityUtils {
    public static void skipActivity(Context context,Class cl,int id,String title){
      Intent intent=  new Intent( context,cl);
      intent.putExtra("id",String.valueOf(id));
      intent.putExtra("title",title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
      context.startActivity(intent);
    }
}
