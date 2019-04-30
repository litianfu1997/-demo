package com.nnxy.gjp.application;

import android.app.Application;

import com.dbmanager.CommomUtils;
import com.nnxy.gjp.entity.User;

import org.json.JSONObject;


public class MyApplication extends Application {


    private static JSONObject user1;

    @Override
    public void onCreate() {
        super.onCreate();

    }


    public static JSONObject getUser() {
        return user1;
    }

    public static void setUser(JSONObject user) {
        user1 = user;
    }
}
