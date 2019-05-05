package com.nnxy.gjp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nnxy.gjp.R;

public class ShowStatFragment extends Fragment {
    private Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_stat,container,false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        String startDate = bundle.getString("startDate");
        String endDate = bundle.getString("endDate");
        Double sr = bundle.getDouble("sr");
        Double zc = bundle.getDouble("zc");

        //获取元素
        TextView startDateTV = view.findViewById(R.id.show_stat_startDate);
        TextView endDateTV = view.findViewById(R.id.show_stat_endDate);
        TextView srTV = view.findViewById(R.id.show_stat_sr);
        TextView zcTV = view.findViewById(R.id.show_stat_zc);

        //设置值
        startDateTV.setText("开始时间:"+startDate);
        endDateTV.setText("结束时间:"+endDate);
        srTV.setText("收入"+sr+"元");
        zcTV.setText("支出"+zc+"元");


    }

}
