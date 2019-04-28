package com.nnxy.gjp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nnxy.gjp.R;
import com.nnxy.gjp.adapter.AccountAdapter;

import java.util.List;

public class SelectAccountFragment extends Fragment {
    private ListView listView;
    private String str[]={"123","456"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_select_account_,container,false );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       listView = view.findViewById(R.id.select_listview);
//
//
//      //  AccountAdapter adapter = new AccountAdapter(getActivity(),R.layout.select_account_layout,null);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,str);
       listView.setAdapter(adapter);
    }
}
