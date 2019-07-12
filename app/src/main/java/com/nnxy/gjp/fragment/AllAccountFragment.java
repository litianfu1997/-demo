package com.nnxy.gjp.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dbmanager.CommomUtils;
import com.nnxy.gjp.R;
import com.nnxy.gjp.adapter.AccountAdapter;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 查询所有账务，默认以插入时间排序
 */
public class AllAccountFragment extends Fragment {


    private CommomUtils commomUtils;


    private ListView listView;

    private List<Account> accountList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_account_layout,container,false );
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.select_all_account_listview);
        commomUtils = new CommomUtils(getActivity());
        try {
            accountList = commomUtils.queryAllAccount(Long.parseLong(MyApplication.getUser().getString("userId")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AccountAdapter adapter = new AccountAdapter(getActivity(),R.layout.select_account_layout,accountList);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,str);
       listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //实例化UpdateOrDeleteAccountFragment对象
                UpdateOrDeleteAccountFragment updateOrDeleteAccountFragment = new UpdateOrDeleteAccountFragment();
                //实例化Bundle对象用于封装数据到UpdateOrDeleteAccountFragment对象中
                Bundle bundle = new Bundle();
                //封装数据
                bundle.putString("accId",String.valueOf(accountList.get(position).getAccId()));
                bundle.putString("money",accountList.get(position).getAccMoney().toString());
                bundle.putString("date",accountList.get(position).getAccCreateDate());
                bundle.putString("type", String.valueOf((accountList.get(position).getAccType() ? true:false)));
                bundle.putString("style",accountList.get(position).getAccStyle());
                bundle.putString("note",accountList.get(position).getAccNote());
                //将bundle装进UpdateOrDeleteAccountFragment对象中
                updateOrDeleteAccountFragment.setArguments(bundle);
                //跳转界面，并且带着数据过去
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,updateOrDeleteAccountFragment).addToBackStack(null).commitAllowingStateLoss();

            }
        });


    }


}
