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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nnxy.gjp.R;
import com.nnxy.gjp.entity.User;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class AddAccountFragment extends Fragment {
    private Button addAccountBtn;
    private Calendar calendar;
    private int mYear,mMonth,mDay;
    private OKManager manager;

    private Spinner output_LeiBie = null;

    private Spinner leiBie = null;
    private String[][] leiieData = new String[][]{{"工资", "捡钱", "金融", "其他"},
            {"购物", "吃饭", "出行", "其他"}};
    private ArrayAdapter<CharSequence> adapterArea = null;



    private EditText money,date,note;

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

        View view = inflater.inflate(R.layout.activity_add_account_,container,false );

        return view;
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

        calendar=Calendar.getInstance();

        addAccountBtn = view.findViewById(R.id.addAccount_btn);

        if (getActivity() != null){
            addAccountBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (money.getText().toString().trim().equals("")){
                        Toast.makeText(getActivity(),"金额不能为空",Toast.LENGTH_LONG).show();
                    }else if (date.getText().toString().trim().equals("")){
                        Toast.makeText(getActivity(),"日期不能为空",Toast.LENGTH_LONG).show();
                    }else{
                        HashMap<String,String> accountMap = new HashMap<String,String>();
                        accountMap.put("userId", "");
                        accountMap.put("accCreateDate",date.getText().toString());
                        accountMap.put("accMoney",money.getText().toString());
                        accountMap.put("accType",output_LeiBie.getSelectedItem().toString());
                        accountMap.put("accStyle",leiBie.getSelectedItem().toString());
                        accountMap.put("accNote",note.getText().toString());

                        manager.sendComplexForm("", accountMap, new OKManager.Func4() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                            }
                        });

                        Toast.makeText(getActivity(),"插入.........",Toast.LENGTH_LONG).show();
                    }
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

        }else {
            System.out.println("getActivity为空！");
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
