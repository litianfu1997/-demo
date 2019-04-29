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
import com.nnxy.gjp.entity.Account;
import com.nnxy.gjp.okhttp.OKManager;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AllAccountFragment extends Fragment {





    private ListView listView;
    private String str[]={"123","456"};
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
//       AccountAdapter adapter = new AccountAdapter(getActivity(),R.layout.select_account_layout,null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,str);
        listView.setAdapter(adapter);


    }

}
