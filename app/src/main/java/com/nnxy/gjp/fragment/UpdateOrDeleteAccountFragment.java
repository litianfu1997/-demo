package com.nnxy.gjp.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dbmanager.CommomUtils;
import com.nnxy.gjp.R;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.entity.User;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONException;

import java.util.Calendar;

public class UpdateOrDeleteAccountFragment extends Fragment {
    private Button updateAccountBtn,deleteAccountBtn;
    private Calendar calendar;
    private int mYear,mMonth,mDay;
    private OKManager manager;

    private Spinner output_LeiBie = null;

    private Spinner leiBie = null;
    private String[][] leiieData = new String[][]{{"工资", "捡钱", "金融", "其他"},
            {"购物", "吃饭", "出行", "其他"}};
    private ArrayAdapter<CharSequence> adapterArea = null;

    private EditText money,date,note;
    private TextView type_tv,style_tv;

    private Account account;

    private CommomUtils accountUtils;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_or_delete_account_,container,false );

        return view;
    }

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
        type_tv = view.findViewById(R.id.type);
        style_tv = view.findViewById(R.id.style);

        calendar=Calendar.getInstance();
        accountUtils = new CommomUtils(getActivity());
        updateAccountBtn = view.findViewById(R.id.updateAccount_btn);
        deleteAccountBtn = view.findViewById(R.id.deleteAccount_btn);

        final Bundle bundle = getArguments();
        accountUtils = new CommomUtils(getActivity());

        money.setText(bundle.getString("money"));
        date.setText(bundle.getString("date"));
        note.setText(bundle.getString("note"));
        if (bundle.getString("type").equals("false")){
            type_tv.setText("支出");
        }else {
            type_tv.setText("收入");
        }

        style_tv.setText(bundle.getString("style"));

        account = new Account();
        account.setAccId(Long.parseLong(bundle.get("accId").toString()));
        try {
            account.setUserId(Long.parseLong(MyApplication.getUser().getString("userId")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        account.setAccMoney(Double.parseDouble(money.getText().toString()));
        account.setAccCreateDate(date.getText().toString());
        if (output_LeiBie.getSelectedItem().toString().equals("收入")){
            account.setAccType(true);
        }else {
            account.setAccType(false);
        }
        account.setOperateFlag(1l);




        updateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setAccNote(note.getText().toString());

//                    account.setAccMoney(Double.parseDouble(money.getText().toString()));


                account.setAccIsDel(false);
                account.setAccStyle(leiBie.getSelectedItem().toString());
                if (money.getText().toString().trim().equals("||")){
                    Toast.makeText(getActivity(),"金额不能为空",Toast.LENGTH_LONG).show();
                }else if (date.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(),"日期不能为空",Toast.LENGTH_LONG).show();
                }else {
                    accountUtils.updateAccount(account);
                    System.out.println(account.toString());
                    Toast.makeText(getActivity(),"更新成功",Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,new AllAccountFragment()).commitAllowingStateLoss();
                }
            }
        });

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setTitle("你确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                account.setAccStyle(leiBie.getSelectedItem().toString());
                                account.setAccIsDel(true);
                                accountUtils.updateAccount(account);
                            }
                        }).setNegativeButton("取消",null).show();

            }
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
    }


    private class OnItemSelectedListenerImp implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            // 得到选择的选项
            UpdateOrDeleteAccountFragment.this.adapterArea = new ArrayAdapter<CharSequence>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    UpdateOrDeleteAccountFragment.this.leiieData[position]);
            UpdateOrDeleteAccountFragment.this.adapterArea
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            UpdateOrDeleteAccountFragment.this.leiBie.setAdapter(UpdateOrDeleteAccountFragment.this.adapterArea);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }
}
