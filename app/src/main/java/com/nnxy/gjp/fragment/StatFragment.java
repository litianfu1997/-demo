package com.nnxy.gjp.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.dbmanager.CommomUtils;
import com.nnxy.gjp.R;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.entity.Account;

import org.json.JSONException;

import java.util.Calendar;
import java.util.List;

public class StatFragment extends Fragment {
    //开始日期
    private EditText startDate;
    //结束日期
    private EditText endDate;
    //按键
    private Button button;
    private Calendar calendar;
    private int mYear,mMonth,mDay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_stat,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取文本框
        startDate = view.findViewById(R.id.stat_start_date);
        endDate = view.findViewById(R.id.stat_end_date);
        button = view.findViewById(R.id.statAccount_Btn);

        //设置日期选择器
        calendar=Calendar.getInstance();
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //日历控件
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mYear = year;
                        mMonth = month;
                        mDay = day;
                        startDate.setText(new StringBuilder()
                                .append(mYear).append("-").append((mMonth+1) < 10 ? "0"+(mMonth+1):(mMonth+1)).append("-").append((mDay<10)?"0"+mDay:mDay));
                    }
                },calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //日历控件
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mYear = year;
                        mMonth = month;
                        mDay = day;
                        endDate.setText(new StringBuilder()
                                .append(mYear).append("-").append((mMonth+1) < 10 ? "0"+(mMonth+1):(mMonth+1)).append("-").append((mDay<10)?"0"+mDay:mDay));
                    }
                },calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取页面数据
                String startDateStr = startDate.getText().toString();
                String endDateStr = endDate.getText().toString();

                if (startDateStr.trim().equals("")){
                    Toast.makeText(getActivity(),"开始日期不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(endDateStr.trim().equals("")){
                    Toast.makeText(getActivity(),"结束日期不能为空",Toast.LENGTH_SHORT).show();
                }
                else {

                    CommomUtils commomUtils = new CommomUtils(getActivity());
                    List<Account> accounts = null;
                    try {
                        accounts = commomUtils.queryAllAccountByDate(Long.valueOf(MyApplication.getUser().getString("userId")),
                                startDateStr,endDateStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //遍历数据
                    Double zc = 0d;
                    Double sr = 0d;
                    for (Account account:accounts) {
                        if(account.getAccType()){
                            sr += account.getAccMoney();
                        }
                        else{
                            zc += account.getAccMoney();
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putDouble("zc",zc);
                    bundle.putDouble("sr",sr);
                    bundle.putString("startDate",startDateStr);
                    bundle.putString("endDate",endDateStr);
                    //跳转页面
                    ShowStatFragment showStatFragment = new ShowStatFragment();
                    showStatFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_container,showStatFragment)
                            .addToBackStack(null).commitAllowingStateLoss();
                }
            }
        });




    }
}
