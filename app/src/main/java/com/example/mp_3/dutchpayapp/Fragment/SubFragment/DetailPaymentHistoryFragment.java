package com.example.mp_3.dutchpayapp.Fragment.SubFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem;
import com.example.mp_3.dutchpayapp.R;

public class DetailPaymentHistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_payment_history,null);

        ListView list = (ListView)view.findViewById(R.id.lv_pay_history);
        ListViewAdapter adapter = new ListViewAdapter();

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String date = item.getDate();
                String shop = item.getShop();
                String member = item.getMamber();
                String cost = item.getCost();

                //프래그먼트 전환

            }
        });

        //-----------tmp_data-------------//
        adapter.addItem("2019.02.13","###식당","ㅁㅁㅁ,ㅇㅇㅇ,ㅅㅅㅅ,ㅈㅈㅈ","80000원");
        adapter.addItem("2019.02.13","***식당","ㅇㅇㅇ,ㅅㅅㅅ,ㅈㅈㅈ","100000원");
        adapter.addItem("2019.02.13","!!!식당","ㅅㅅㅅ,ㅈㅈㅈ","10000원");
        adapter.addItem("2019.02.13","$$$식당","ㅇㅇㅇ,ㅅㅅㅅ,ㅈㅈㅈ,ㅍㅍㅍ,ㅂㅂㅂ,ㅋㅋㅋ,ㅎㅎㅎ,ㄱㄱㄱ,ㄴㄴㄴ,ㅌㅌㅌ","10000원");
        //-------------------------------//


        return view;
    }

}
