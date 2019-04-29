package com.nnxy.gjp.mainfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nnxy.gjp.R;
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
        manager = OKManager.getInstance();


    }

    public void Register_NewUser(View view){
        String userCode = ed_username.getText().toString();
        String password = ed_password.getText().toString();
        String repassword = ed_repassword.getText().toString();
        String userPhone = ed_phoneNum.getText().toString();
        String userName = ed_nicheng.getText().toString();
//        HashMap<String,String > reguser = new HashMap<String, String>();
//        reguser.put("userCode",userCode);
//        reguser.put("password",password);
//        reguser.put("userPhone",userPhone);
//        reguser.put("userName",userName);
//       Log.i("RegisterActivity",manager.toString());
//        Log.i("RegisterActivity",reguser.get("userCode").toString());
//        manager.sendForm("http://10.0.2.2:8080/Demo/regServlet",reguser,null);

        if(userCode.equals("")||password.equals("")||repassword.equals("")||userPhone.equals("")||userName.equals("")){
            Toast.makeText(getApplicationContext(),"输入框不要留空",Toast.LENGTH_LONG).show();
        }else if(!(password.equals(repassword))){
            Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_LONG).show();
        }else {
            HashMap<String,String > reguser = new HashMap<String, String>();
            reguser.put("userCode",userCode);
            reguser.put("password",password);
            reguser.put("userPhone",userPhone);
            reguser.put("userName",userName);

//            http://10.0.2.2:8080/acountService/user/register.action
            manager.sendComplexForm("http://10.0.2.2:8080/accountService/user/register.action", reguser, new OKManager.Func4() {
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

        }
    }
}
