package com.nnxy.gjp.mainfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dbmanager.CommomUtils;
import com.google.gson.Gson;
import com.nnxy.gjp.R;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.entity.User;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TGA = MainActivity.class.getSimpleName();

    private OKManager manager ;

    private EditText userCode,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到组件
        userCode = findViewById(R.id.ed_username);
        password = findViewById(R.id.ed_password);
        //获取网络框架manager
        manager = OKManager.getInstance();
    }

    /**
     * 退出
     * @param view
     */
    public void Exit_fun(View view){
        CommomUtils commomUtils = new CommomUtils(getApplicationContext());
        commomUtils.deleteTable();
        finish();
    }

    /**
     * 忘记密码
     * @param view
     */
    public void Forget_Fun(View view){
        startActivity(new Intent(MainActivity.this,ForgetActivity.class));

    }

    /**
     * 该按钮实现登录功能，并且跳转到菜单页面
     * @param view
     */
    public void Login_Fun(View view){

        CommomUtils commomUtils = new CommomUtils(getApplicationContext());
        commomUtils.deleteTable();

        final String userCodeStr =  userCode.getText().toString();
        String pwd = password.getText().toString();



        if(userCodeStr.trim().equals("")||pwd.trim().equals("")){
            Toast.makeText(getApplicationContext(),"用户名和密码不能为空",Toast.LENGTH_LONG).show();
        }else{
            User user =new User();
            user.setUserCode(userCodeStr);
            user.setPassword(pwd);
            final Gson gson =new Gson();
            String str =gson.toJson(user);
            manager.sendStringByPostMethod("http://www.tech4flag.com/accountService/user/login.action", str, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) { //将服务器表单提交到

                    try {
                        if(jsonObject.getString("status").equals("success")){
                            //将user放入MyApplication
                            MyApplication.setUser(jsonObject.getJSONObject("obj"));

                            final Gson gson = new Gson();
                            //通过服务器传过来的json字符串获取user对象
                            User user =gson.fromJson(MyApplication.getUser().toString(), User.class);
                            String userJsonStr = gson.toJson(user);


                            manager.sendStringByPostMethod5("http://www.tech4flag.com/accountService/account/syncToClient.action", userJsonStr, new OKManager.Func5() {
                                @Override
                                public void onResponse(JSONArray jsonArray) {
                                    System.out.println();
                                    CommomUtils commomUtils = new CommomUtils(getApplicationContext());
                                    List<Account> accounts = new ArrayList<>();
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
                                    startActivity(new Intent(MainActivity.this,MeunActivity.class));//登录成功跳转页面
                                }
                            });




                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                        }else if (jsonObject.getString("status").equals("error")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                        }

//                        Log.i(TGA,jsonObject.toString()); //服务器响应后应该返回一个json对象数据
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }


    /**
     * 该按钮实现的功能是跳转到注册页面
     * @param view
     */
    public void Intent_reg(View view){
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }
}
