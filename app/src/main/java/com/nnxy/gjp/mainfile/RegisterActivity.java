package com.nnxy.gjp.mainfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nnxy.gjp.R;
import com.nnxy.gjp.entity.User;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText ed_username,ed_password,ed_repassword,ed_phoneNum,ed_nicheng;
    private OKManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_username = findViewById(R.id.Reg_username);
        ed_password = findViewById(R.id.Reg_password);
        ed_repassword = findViewById(R.id.Reg_repassword);
        ed_phoneNum = findViewById(R.id.Reg_phoneNum);
        ed_nicheng = findViewById(R.id.Reg_name);
        manager = OKManager.getInstance();//获取OKmanager对象


    }

    public void Register_NewUser(View view){
        //获取输入框的值
        String userCode = ed_username.getText().toString();
        String password = ed_password.getText().toString();
        String repassword = ed_repassword.getText().toString();
        String userPhone = ed_phoneNum.getText().toString();
        String userName = ed_nicheng.getText().toString();

        //进行条件判断
        if(userCode.equals("")||password.equals("")||repassword.equals("")||userPhone.equals("")||userName.equals("")){
            Toast.makeText(getApplicationContext(),"输入框不要留空",Toast.LENGTH_LONG).show();
        }else if(!(password.equals(repassword))){
            Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_LONG).show();
        }else {
            //将获取到的数据填入user对象中
            User user = new User();
            user.setUserCode(userCode);
            user.setPassword(password);
            user.setUserPhone(userPhone);
            user.setUserName(userName);
            Gson gson =new Gson();//实例化Gson对象
            String userJson = gson.toJson(user);//将user对象转换为Json字符串
            //将Json字符串发给服务器
            manager.sendStringByPostMethod("http://www.tech4flag.com/accountService/user/register.action", userJson, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        //对服务器返回的Json对象进行解析
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


        }
    }
}
