package com.nnxy.gjp.mainfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nnxy.gjp.R;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.HashMap;

public class ForgetActivity extends AppCompatActivity {
    private OKManager manager;
    private EditText phone,newPwd,rPwd,user;
    private HashMap<String,String> newPwdMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        phone = findViewById(R.id.For_PhoneNumber);
        newPwd = findViewById(R.id.For_NewPassword);
        rPwd = findViewById(R.id.For_ConfirmPassword);
        user = findViewById(R.id.For_UserName);
        manager = OKManager.getInstance();
    }

    public void Register_NewUser(View view){
        String phoneNum = phone.getText().toString();
        String newPassword = newPwd.getText().toString();
        String rePassword = rPwd.getText().toString();
        String userCode = user.getText().toString();

        newPwdMap = new HashMap<String, String>();

            newPwdMap.put("userCode",userCode);
            newPwdMap.put("password",newPassword);
            newPwdMap.put("userPhone",phoneNum);
//            newPwdMap.put("userName",MyApplication.getUser().getString("userName"));


        if(phoneNum.trim().equals("")||newPassword.trim().equals("")||rePassword.trim().equals("")){
            Toast.makeText(getApplicationContext(),"输入框不能为空！",Toast.LENGTH_LONG).show();
        }else if (!(newPassword.equals(rePassword))){
            Toast.makeText(getApplicationContext(),"两次密码不一致！",Toast.LENGTH_LONG).show();
        }else {
            manager.sendComplexForm("http://10.0.2.2:8080/accountService/user/checkUserPhone.action", newPwdMap, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getString("status").equals("success")){
//                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据

//                            System.out.println("进入第一个if");
                            manager.sendComplexForm("http://10.0.2.2:8080/accountService/user/updatePassword.action", newPwdMap, new OKManager.Func4() {


                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    if (jsonObject.getString("status").equals("success")){
//                                        System.out.println("进入第二个if");
                                        Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据
                                    }else if (jsonObject.getString("status").equals("error")){
                                        Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


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
