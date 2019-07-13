package com.nnxy.gjp.fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dbmanager.CommomUtils;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.nnxy.gjp.R;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.entity.User;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类的功能是添加账务
 */
public class AddAccountFragment extends Fragment {

    //对所有控件、对象声明
    private Button addAccountBtn;
    private Calendar calendar;
    private int mYear,mMonth,mDay;
    private OKManager manager;
    private Spinner output_LeiBie = null;
    private Spinner leiBie = null;
    private String[][] leiieData = new String[][]{{"工资", "捡钱", "金融", "其他"},
            {"购物", "吃饭", "出行", "其他"}};
    private ArrayAdapter<CharSequence> adapterArea = null;
    private List<Account> accountList;
    private EditText money,date,note;
    private Account account;
    private CommomUtils accountUtils;

    /**
     * 创建view，相当于Activity的setContentView（）；
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyApplication.setAPPFLAG(102);
        View view = inflater.inflate(R.layout.activity_add_account_,container,false );

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyApplication.setAPPFLAG(101);
    }

    /**
     * 当view创将完成之后,要干的活
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.output_LeiBie =  view.findViewById(R.id.zhangwu_leibie);
        this.leiBie =  view.findViewById(R.id.leibie);
        this.output_LeiBie.setOnItemSelectedListener(new OnItemSelectedListenerImp());
        manager = OKManager.getInstance();
        money=view.findViewById(R.id.acc_money);
        date=view.findViewById(R.id.acc_date);
        note=view.findViewById(R.id.acc_note);
        //对日历对象进行实例化
        calendar=Calendar.getInstance();
        //对操作数据库的对象进行实例化
        accountUtils = new CommomUtils(getActivity());
        addAccountBtn = view.findViewById(R.id.addAccount_btn);
        //判空
        if (getActivity() != null&&accountUtils != null){

            addAccountBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    account = new Account();


                    if (money.getText().toString().trim().equals("")){
                        Toast.makeText(getActivity(),"金额不能为空",Toast.LENGTH_LONG).show();
                    }else if (date.getText().toString().trim().equals("")){
                        Toast.makeText(getActivity(),"日期不能为空",Toast.LENGTH_LONG).show();
                    }else {
                        try {
                            account.setUserId(Long.parseLong(MyApplication.getUser().getString("userId")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                    account.setAccMoney(Double.parseDouble(money.getText().toString()));
                        account.setAccMoney(Double.parseDouble(money.getText().toString()));
                        account.setAccCreateDate(date.getText().toString());
                        if (output_LeiBie.getSelectedItem().toString().equals("收入")){
                            account.setAccType(true);
                        }else {
                            account.setAccType(false);
                        }
                        account.setOperateFlag(2l);//设置标识符
                        account.setAccStyle(leiBie.getSelectedItem().toString());
                        account.setAccNote(note.getText().toString());
                        account.setAccIsDel(false);
                        if (accountUtils.insertAccount(account)){//插入账务

                            Toast.makeText(getActivity(),"插入成功",Toast.LENGTH_LONG).show();
                            //跳转到主页面
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,new AllAccountFragment()).commitAllowingStateLoss();
                        }else {
                            Toast.makeText(getActivity(),"插入失败",Toast.LENGTH_LONG).show();
                        }
                    }
                }

//                @Override
//                public void onClick(View v) {
//                    if (money.getText().toString().trim().equals("")){
//                        Toast.makeText(getActivity(),"金额不能为空",Toast.LENGTH_LONG).show();
//                    }else if (date.getText().toString().trim().equals("")){
//                        Toast.makeText(getActivity(),"日期不能为空",Toast.LENGTH_LONG).show();
//                    }else{
//                        HashMap<String,String> accountMap = new HashMap<String,String>();
//                        try {
//                            accountMap.put("userId", MyApplication.getUser().getString("userId"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        accountMap.put("accCreateDate",date.getText().toString());
//                        accountMap.put("accMoney",money.getText().toString());
//                        accountMap.put("accType",output_LeiBie.getSelectedItem().toString());
//                        accountMap.put("accStyle",leiBie.getSelectedItem().toString());
//                        accountMap.put("accNote",note.getText().toString());
//
////                        accountList =new ArrayList<Account>();
//////                        Map<Integer,Account> accountMap1 = accountList.stream().collect(Collectors.toMap(User::getUserId, Function.identity(),(key1,key2)-> key2));
////                        Map<Integer, Account> maps = Maps.uniqueIndex(accountList, new Function<Account, Integer>() {
////
////                            @Override
////                            public Integer apply( Account account) {
////                                return account.getAccId();
////                            }
////                        });
//
//
//
//                        manager.sendComplexForm("http://10.0.2.2:8080/accountService/account/syncToServer.action", accountMap, new OKManager.Func4() {
//                            @Override
//                            public void onResponse(JSONObject jsonObject) {
//
//                                try {
//                                    if (jsonObject.getString("status").equals("success")){
//
//                                        Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据
//                                    }else if (jsonObject.getString("status").equals("error")){
//                                        Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//
//                                }
//                            }
//                        });
//
////                        Toast.makeText(getActivity(),"插入.........",Toast.LENGTH_LONG).show();
//                    }
//                }
            });

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //日历控件
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            mYear = year;
                            mMonth = month;
                            mDay = day;
                            date.setText(new StringBuilder()
                                    .append(mYear).append("-").append((mMonth+1) < 10 ? "0"+(mMonth+1):(mMonth+1)).append("-").append((mDay<10)?"0"+mDay:mDay));
                        }
                    },calendar.get(Calendar.YEAR)
                            ,calendar.get(Calendar.MONTH)
                            ,calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

        }else {
            System.out.println("getActivity为空或者accountUtils为空！");
        }




    }

    private class OnItemSelectedListenerImp implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            // 得到选择的选项
            AddAccountFragment.this.adapterArea = new ArrayAdapter<CharSequence>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    AddAccountFragment.this.leiieData[position]);
            AddAccountFragment.this.adapterArea
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            AddAccountFragment.this.leiBie.setAdapter(AddAccountFragment.this.adapterArea);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }



}
