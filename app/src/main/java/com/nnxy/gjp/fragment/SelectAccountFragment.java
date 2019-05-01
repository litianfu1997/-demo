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

    private CommomUtils accountUtils;
    private Spinner output_LeiBie = null;

    private Spinner leiBie = null;

    private ArrayAdapter<CharSequence> adapterArea = null;

    private EditText miniMoney,bigMoney,startDate,endDate;
    private ListView listView;
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


        manager = OKManager.getInstance();
        miniMoney=view.findViewById(R.id.acc_mini_money);
        bigMoney = view.findViewById(R.id.acc_big_money);
        startDate=view.findViewById(R.id.acc_start_date);
        endDate=view.findViewById(R.id.acc_end_date);
        accountUtils = new CommomUtils(getActivity());
        calendar= Calendar.getInstance();

        addAccountBtn = view.findViewById(R.id.addAccount_btn);

        if (getActivity() != null){
            addAccountBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String startDatestr = startDate.getText().toString();
                    String endDatestr = endDate.getText().toString();
                    String bigMoneyStr = bigMoney.getText().toString();
                    String miniMoneyStr = miniMoney.getText().toString();
//                    if(bigMoney.getText().toString().trim().equals("")||miniMoney.getText().toString().trim().equals("")){
//                        miniMoneyStr = 0d;
//                        bigMoneyStr = 10000000000000d;
//                    }else {
//                        bigMoneyStr = Double.valueOf(bigMoney.getText().toString());
//                        miniMoneyStr = Double.valueOf(miniMoney.getText().toString());
//                    }

                    String typeStr = output_LeiBie.getSelectedItem().toString();
//                    Boolean typeB;
//                    if (typeStr.equals("收入")){
//                         typeB = true;
//                    }else {
//                        typeB = false;
//
//                    }
//                    List<Account> accountList = accountUtils.queryAccount(startDatestr,endDatestr,miniMoneyStr,bigMoneyStr,typeB);
                    Bundle bundle = new Bundle();
                    SelectAccountByOptionFragment selectAccountByOptionFragment = new SelectAccountByOptionFragment();
                    bundle.putString("startDatestr",startDatestr);
                    bundle.putString("endDatestr",endDatestr);
                    bundle.putString("miniMoneyStr",miniMoneyStr);
                    bundle.putString("bigMoneyStr",bigMoneyStr);
                    bundle.putString("typeStr",typeStr);
                    selectAccountByOptionFragment.setArguments(bundle);
//                    Toast.makeText(getActivity(),"查询中",Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,selectAccountByOptionFragment).addToBackStack(null).commitAllowingStateLoss();




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


}
