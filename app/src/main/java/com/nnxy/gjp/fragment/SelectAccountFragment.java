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

import com.nnxy.gjp.R;
import com.nnxy.gjp.adapter.AccountAdapter;
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SelectAccountFragment extends Fragment {
    private Button addAccountBtn;
    private Calendar calendar;
    private int mYear,mMonth,mDay;
    private OKManager manager;

    private Spinner output_LeiBie = null;

    private Spinner leiBie = null;
    private String[][] leiieData = new String[][]{{"工资", "捡钱", "金融", "其他"},
            {"购物", "吃饭", "出行", "其他"}};
    private ArrayAdapter<CharSequence> adapterArea = null;

    private EditText miniMoney,bigMoney,startDate,endDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_select_account_,container,false );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        listView = view.findViewById(R.id.s);


//       AccountAdapter adapter = new AccountAdapter(getActivity(),R.layout.select_account_layout,null);
//       ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,str);
//       listView.setAdapter(adapter);
        this.output_LeiBie =  view.findViewById(R.id.select_zhangwu_leibie);
        this.leiBie =  view.findViewById(R.id.select_leibie);
        this.output_LeiBie.setOnItemSelectedListener(new SelectAccountFragment.OnItemSelectedListenerImp());
        manager = OKManager.getInstance();
        miniMoney=view.findViewById(R.id.acc_mini_money);
        bigMoney = view.findViewById(R.id.acc_big_money);
        startDate=view.findViewById(R.id.acc_start_date);
        endDate=view.findViewById(R.id.acc_end_date);

        calendar= Calendar.getInstance();

        addAccountBtn = view.findViewById(R.id.addAccount_btn);

        if (getActivity() != null){
            addAccountBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (miniMoney.getText().toString().trim().equals("")
                            ||bigMoney.getText().toString().trim().equals("")
                            ||startDate.getText().toString().trim().equals("")
                            ||endDate.getText().toString().trim().equals("")){
                        Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_LONG).show();
                    }else{
                        HashMap<String,String> accountMap = new HashMap<String,String>();
                        accountMap.put("userId", "");
                        accountMap.put("accStartDate",startDate.getText().toString());
                        accountMap.put("accEndDate",endDate.getText().toString());
                        accountMap.put("accMiniMoney",miniMoney.getText().toString());
                        accountMap.put("accBigMoney",bigMoney.getText().toString());
                        accountMap.put("accType",output_LeiBie.getSelectedItem().toString());
                        accountMap.put("accStyle",leiBie.getSelectedItem().toString());
                        manager.sendComplexForm("", accountMap, new OKManager.Func4() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                            }
                        });

                        Toast.makeText(getActivity(),"查询.........",Toast.LENGTH_LONG).show();
                    }
                }
            });

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

        }else {
            System.out.println("getActivity为空！");
        }
    }
    private class OnItemSelectedListenerImp implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            // 得到选择的选项
            SelectAccountFragment.this.adapterArea = new ArrayAdapter<CharSequence>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    SelectAccountFragment.this.leiieData[position]);
            SelectAccountFragment.this.adapterArea
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SelectAccountFragment.this.leiBie.setAdapter(SelectAccountFragment.this.adapterArea);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }
}
