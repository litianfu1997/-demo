package com.nnxy.gjp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dbmanager.CommomUtils;
import com.nnxy.gjp.R;
import com.nnxy.gjp.adapter.AccountAdapter;
import com.nnxy.gjp.application.MyApplication;
import com.nnxy.gjp.entity.Account;

import org.json.JSONException;

import java.util.List;

public class SelectAccountByOptionFragment extends Fragment {
    private CommomUtils accountUtils;
    private ListView listView;
    private Boolean typeB;
    private Long id;
    private Double miniMoney=0d,bigMoney=0d;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part_account_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        bundle.putString("startDatestr",startDatestr);
//        bundle.putString("endDatestr",endDatestr);
//        bundle.putString("miniMoneyStr",miniMoneyStr);
//        bundle.putString("bigMoneyStr",bigMoneyStr);
//        bundle.putString("typeB",typeStr);
        listView = view.findViewById(R.id.select_part_account_listview);
        accountUtils = new CommomUtils(getActivity());


        final Bundle bundle = getArguments();

        if(bundle != null){
            String startDate = bundle.getString("startDatestr");
            String endDate =bundle.getString("endDatestr");
            String miniMoneyStr =bundle.getString("miniMoneyStr");
            String bigMoneyStr =bundle.getString("bigMoneyStr");
            String type =bundle.getString("typeStr");
            System.out.println(miniMoneyStr+" "+bigMoneyStr+" ");
            if(bigMoneyStr.trim().equals("")||miniMoneyStr.trim().equals("")){


                miniMoney = 0d;
                bigMoney = 0d;

            }else {
                bigMoney = Double.valueOf(bigMoneyStr);
                miniMoney = Double.valueOf(miniMoneyStr);
            }

            if (type.equals("收入")){
                typeB = true;
            }else {
                typeB = false;

            }
            try {
                id = Long.valueOf(MyApplication.getUser().getString("userId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(getActivity(),"查询中",Toast.LENGTH_LONG).show();
            final List<Account> accountList = accountUtils.queryAccount(id,startDate,endDate,miniMoney,bigMoney,typeB);
//            System.out.println(id+" "+startDate+" "+ endDate+" "+miniMoney+" "+bigMoney+" "+type);
//            System.out.println(accountList);
            AccountAdapter adapter = new AccountAdapter(getActivity(),R.layout.select_account_layout,accountList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    UpdateOrDeleteAccountFragment updateOrDeleteAccountFragment = new UpdateOrDeleteAccountFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("accId",String.valueOf(accountList.get(position).getAccId()));
                    bundle.putString("money",accountList.get(position).getAccMoney().toString());
                    bundle.putString("date",accountList.get(position).getAccCreateDate());
                    bundle.putString("type", String.valueOf((accountList.get(position).getAccType() ? true:false)));
                    bundle.putString("style",accountList.get(position).getAccStyle());
                    bundle.putString("note",accountList.get(position).getAccNote());
                    updateOrDeleteAccountFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,updateOrDeleteAccountFragment).commitAllowingStateLoss();

                }
            });
        }else {
            System.out.println("bundle为空！");
        }




    }
}
