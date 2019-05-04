package com.nnxy.gjp.application;

import android.app.Application;

import com.dbmanager.CommomUtils;
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.entity.User;

import org.json.JSONObject;

import java.util.List;


public class MyApplication extends Application {


    private static JSONObject user1;
    private static List<Account> accountList;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static List<Account> getAccountList() {
        return accountList;
    }

    public static void setAccountList(List<Account> accountList) {
        MyApplication.accountList = accountList;
    }

    public static JSONObject getUser() {
        return user1;
    }

    public static void setUser(JSONObject user) {
        user1 = user;
    }
}
