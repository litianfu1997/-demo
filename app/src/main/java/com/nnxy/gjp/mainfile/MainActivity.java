package com.nnxy.gjp.mainfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nnxy.gjp.R;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String TGA = MainActivity.class.getSimpleName();

    private OKManager manager;

    private EditText username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到组件
        username = findViewById(R.id.ed_username);
        password = findViewById(R.id.ed_password);
        //获取网络框架manager
        manager = OKManager.getInstance();
    }

    /**
     * 退出
     * @param view
     */
    public void Exit_fun(View view){
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

        HashMap<String,String> userMap=new HashMap<String, String>();
//        userMap.put("username","tom");
//        userMap.put("password","123456");
        String user =  username.getText().toString();
        String pwd = password.getText().toString();
        userMap.put("username",user);
        userMap.put("password",pwd);
        if(user.trim().equals("")||pwd.trim().equals("")){
            Toast.makeText(getApplicationContext(),"用户名和密码不能为空",Toast.LENGTH_LONG).show();
        }else{
            manager.sendComplexForm("http://10.0.2.2:8080/Demo/loginServlet", userMap, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) { //将服务器表单提交到

                    try {
                        if(jsonObject.getString("flag").equals("1")){
                            startActivity(new Intent(MainActivity.this,MeunActivity.class));
                            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                        }else if (jsonObject.getString("flag").equals("404")){
                            Toast.makeText(getApplicationContext(),"用户不存在",Toast.LENGTH_LONG).show();
                        }else if (jsonObject.getString("flag").equals("0")){
                            Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_LONG).show();

                        }

                        Log.i(TGA,jsonObject.toString()); //服务器响应后应该返回一个json对象数据
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }


//        userMap.put("username","tom");
//        userMap.put("password","123456");
//        manager.sendComplexForm("http://10.0.2.2:8080/Demo/loginServlet", userMap, null);


    }


    /**
     * 该按钮实现的功能是跳转到注册页面
     * @param view
     */
    public void Intent_reg(View view){
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }
}
