package com.nnxy.gjp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.nnxy.gjp.R;
import com.nnxy.gjp.entity.User;


import java.util.List;

public class UserAdapter extends ArrayAdapter {
    private final int resourceId;

    public UserAdapter(Context context, int textViewId, List<User> obj){
        super(context,textViewId,obj);
        this.resourceId = textViewId;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        //1.获取Account实例，实例化view对象
        User user = (User) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        //2.通过id，获取textView
        TextView idTv = view.findViewById(R.id.biaohao_1);
        TextView usernameTv = view.findViewById(R.id.username_1);
        TextView passwordTv = view.findViewById(R.id.password_1);


        //3.设置你要填入的信息
//        idTv.setText(Long.toString(user.getUser_id()));
//        usernameTv.setText(user.getUsername());
//        passwordTv.setText(user.getPassword());

        return view;
    }
}
