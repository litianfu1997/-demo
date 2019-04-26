package com.nnxy.gjp.mainfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nnxy.gjp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        System.out.println("11111");
        startActivity(new Intent(MainActivity.this,MeunActivity.class));

    }


    /**
     * 该按钮实现的功能是跳转到注册页面
     * @param view
     */
    public void Intent_reg(View view){
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }
}
