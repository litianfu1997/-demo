package com.nnxy.gjp.mainfile;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dbmanager.CommomUtils;
import com.google.gson.Gson;
import com.nnxy.gjp.R;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.entity.User;
import com.nnxy.gjp.fragment.AddAccountFragment;
import com.nnxy.gjp.fragment.AllAccountFragment;
import com.nnxy.gjp.fragment.SelectAccountFragment;
import com.nnxy.gjp.fragment.StatFragment;
import com.nnxy.gjp.fragment.SystemSettingFragment;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MeunActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private  AddAccountFragment addAccountFragment;
    private SelectAccountFragment selectAccountFragment;
    private AllAccountFragment allAccountFragment;
    private OKManager manager;
    private CommomUtils commomUtils;
    private List<Account> accounts ;
    private TextView nameTextView,phoneTextView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meun);

        MyApplication.setAPPFLAG(101);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);


        //将CommomUtils初始化，这是操作本地数据库的对象
        commomUtils = new CommomUtils(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

//        先获取到navigation
        View headerView = navigationView.getHeaderView(0);

        //将头部信息赋值
        nameTextView = headerView.findViewById(R.id.header_name_textView);
        phoneTextView = headerView.findViewById(R.id.phone_textView);
        try {
//            设置头部信息
            nameTextView.setText(MyApplication.getUser().getString("userName"));
            phoneTextView.setText(MyApplication.getUser().getString("userPhone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,new AllAccountFragment()).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_syncToServer) {//同步到服务器按钮

            //实例化网络框架OKhttp
            manager = OKManager.getInstance();
            List<Account> accountList = null;
            try {
                //获取操作标准符不为0的所有数据
                accountList = commomUtils.queryAllAccountAndIsDel(Long.parseLong(MyApplication.getUser().getString("userId")));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final Gson gson = new Gson();
            //通过服务器传过来的json字符串获取user对象
            User user =gson.fromJson(MyApplication.getUser().toString(), User.class);
            for (Account account: accountList ) {
               account.setUser(user);//将user对象放到account对象里
            }
            //将刚刚添加user对象的accountList转换为json字符串
            String accountJsonStr = gson.toJson(accountList);

//            System.out.println(accountJsonStr);
            //并且传输到服务器上
            manager.sendStringByPostMethod("http://www.tech4flag.com/accountService/account/syncToServer.action", accountJsonStr, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getString("status").equals("success")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据
                        }else if (jsonObject.getString("status").equals("error")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });



            return true;

        }else if (id == R.id.action_syncToClient){//同步到app按钮
            final Gson gson = new Gson();
            //通过服务器传过来的json字符串获取user对象
            User user =gson.fromJson(MyApplication.getUser().toString(), User.class);
            String userJsonStr = gson.toJson(user);
//            实例化网络框架
            manager = OKManager.getInstance();
//            调用网络框架的sendStringByPostMethod5方法，返回值是jsonArray  http://www.tech4flag.com  http://10.0.2.2:8080
            manager.sendStringByPostMethod5("http://www.tech4flag.com/accountService/account/syncToClient.action",userJsonStr , new OKManager.Func5() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    commomUtils.deleteTable();//进行删除表数据
                    Account account = null;
                    accounts =new ArrayList<Account>();
                    try {
                        for (int i = 0 ;i < jsonArray.length();i++){
//                            通过服务器传过来的jsonArray解析成Account对象
                            account = gson.fromJson(jsonArray.getJSONObject(i).toString(),Account.class);
//                            设置每一条账务的userId
                            account.setUserId(Long.valueOf(jsonArray.getJSONObject(i).getJSONObject("user").getInt("userId")));//设置id
//                            System.out.println(account);
//                            将所有的account对象存入list<Account> accounts中
                            accounts.add(account);


                        }
                        for(int i = 0 ; i<accounts.size() ;i++ ){
                            commomUtils.insertAccount(accounts.get(i));//逐条插入到本地数据库
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(),"同步成功",Toast.LENGTH_LONG).show();
//                    跳转到主页面
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,new AllAccountFragment()).commitAllowingStateLoss();
                }
            });
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_account) {
//            跳转到添加账务的界面
            toolbar.setTitle("添加账务");
            addAccountFragment =new AddAccountFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,addAccountFragment).addToBackStack(null).commitAllowingStateLoss();

        } else if (id == R.id.nav_select_account) {
//            跳转到查询账务的界面
            toolbar.setTitle("查询账务");
             selectAccountFragment =new SelectAccountFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,selectAccountFragment).addToBackStack(null).commitAllowingStateLoss();
        } else if (id == R.id.nav_home) {
//            跳转到主页面
            toolbar.setTitle("主页");
            allAccountFragment = new AllAccountFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,allAccountFragment).addToBackStack(null).commitAllowingStateLoss();
        } else if (id == R.id.nav_account_tj) {
            toolbar.setTitle("账务统计");
            StatFragment statFragment = new StatFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,statFragment).addToBackStack(null).commitAllowingStateLoss();
        } else if (id == R.id.nav_setting) {
            toolbar.setTitle("系统设置");
            SystemSettingFragment systemSettingFragment = new SystemSettingFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,systemSettingFragment).addToBackStack(null).commitAllowingStateLoss();
        } else if (id == R.id.nav_exit) {
            new AlertDialog.Builder(this).setTitle("您确定退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //退出时
                            //实例化网络框架OKhttp
                            manager = OKManager.getInstance();
                            List<Account> accountList = null;
                            try {
                                //获取操作标准符不为0的所有数据
                                accountList = commomUtils.queryAllAccountAndIsDel(Long.parseLong(MyApplication.getUser().getString("userId")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            final Gson gson = new Gson();
                            //通过服务器传过来的json字符串获取user对象
                            User user =gson.fromJson(MyApplication.getUser().toString(), User.class);
                            for (Account account: accountList ) {
                                account.setUser(user);//将user对象放到account对象里
                            }
                            //将刚刚添加user对象的accountList转换为json字符串
                            String accountJsonStr = gson.toJson(accountList);

//            System.out.println(accountJsonStr);
                            //并且传输到服务器上
                            manager.sendStringByPostMethod("http://www.tech4flag.com/accountService/account/syncToServer.action", accountJsonStr, new OKManager.Func4() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    try {
                                        if (jsonObject.getString("status").equals("success")){
                                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据
                                        }else if (jsonObject.getString("status").equals("error")){
                                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            commomUtils.deleteTable();//删除数据表
                            finish();
                        }
                    }).setNegativeButton("取消",null).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getShortClassName();
        return runningActivity;
    }

    //退出时的时间
    private long mExitTime;
    //对返回键进行监听

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&&MyApplication.getAPPFLAG() ==101 ) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {

        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            //退出时
            //实例化网络框架OKhttp

            manager = OKManager.getInstance();
            List<Account> accountList = null;
            try {
                //获取操作标准符不为0的所有数据
                accountList = commomUtils.queryAllAccountAndIsDel(Long.parseLong(MyApplication.getUser().getString("userId")));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final Gson gson = new Gson();
            //通过服务器传过来的json字符串获取user对象
            User user =gson.fromJson(MyApplication.getUser().toString(), User.class);
            for (Account account: accountList ) {
                account.setUser(user);//将user对象放到account对象里
            }
            //将刚刚添加user对象的accountList转换为json字符串
            String accountJsonStr = gson.toJson(accountList);

//            System.out.println(accountJsonStr);
            //并且传输到服务器上
            manager.sendStringByPostMethod("http://www.tech4flag.com/accountService/account/syncToServer.action", accountJsonStr, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getString("status").equals("success")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据
                        }else if (jsonObject.getString("status").equals("error")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            commomUtils.deleteTable();//删除数据表
            finish();
        }
    }


}
