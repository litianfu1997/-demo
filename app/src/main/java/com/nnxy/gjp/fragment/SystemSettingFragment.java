package com.nnxy.gjp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nnxy.gjp.R;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.entity.User;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 修改密码
 */
public class SystemSettingFragment extends Fragment {

    private EditText oldPassword,newPassword,rePassword;
    private Button changePasswordBtn;
    private OKManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyApplication.setAPPFLAG(102);
        View view = inflater.inflate(R.layout.system_setting_layout,container,false);
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyApplication.setAPPFLAG(101);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldPassword = view.findViewById(R.id.old_password);
        newPassword = view.findViewById(R.id.new_password);
        rePassword = view.findViewById(R.id.re_password);
        changePasswordBtn = view.findViewById(R.id.change_password_btn);
        manager = OKManager.getInstance();

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPwd = oldPassword.getText().toString();
                String newPwd = newPassword.getText().toString();
                String rePwd = rePassword.getText().toString();
                String password = "";
                try {
                    password =MyApplication.getUser().getString("password");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (oldPwd.trim().equals("")||newPwd.trim().equals("")||rePwd.trim().equals("")){
                    Toast.makeText(getActivity(),"输入框不能留空",Toast.LENGTH_LONG).show();
                }else if (!(newPwd.equals(rePwd))){
                    Toast.makeText(getActivity(),"两次密码不一致",Toast.LENGTH_LONG).show();
                }else if(!(oldPwd.equals(password))){
                    Toast.makeText(getActivity(),"旧密码不一致",Toast.LENGTH_LONG).show();
                }
                else {
                    Gson gson = new Gson();

                    User user =gson.fromJson(MyApplication.getUser().toString(),User.class);

                    user.setPassword(newPwd);
                    String userJsonStr = gson.toJson(user);

                    manager.sendStringByPostMethod("http://www.tech4flag.com/accountService/user/updatePassword.action", userJsonStr, new OKManager.Func4() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getString("status").equals("success")){

                                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据
                                }else if (jsonObject.getString("status").equals("error")){
                                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }
            }
        });


    }
}
