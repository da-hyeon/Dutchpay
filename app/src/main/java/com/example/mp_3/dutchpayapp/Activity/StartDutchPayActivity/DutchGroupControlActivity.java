package com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.DutchStartListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStart;
import com.example.mp_3.dutchpayapp.R;

import java.util.ArrayList;

public class DutchGroupControlActivity extends AppCompatActivity {
    Toolbar toolbar;

    private ArrayList<ListViewItem_DutchStart> listViewItemList;
    private DutchStartListViewAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch_group_control);

        toolbar = findViewById(R.id.tv_memberCount);
        toolbar.setTitle("모집된 멤버 ( "+ "#" +"명 )");

        listViewItemList = new ArrayList<>();

        listViewItemList.add(new ListViewItem_DutchStart("dp1" , 50000 , 0 ));
        listViewItemList.add(new ListViewItem_DutchStart("dp2" , 50000 , 0 ));
        listViewItemList.add(new ListViewItem_DutchStart("dp3" , 50000 , 0 ));
        listViewItemList.add(new ListViewItem_DutchStart("dp4" , 50000 , 0 ));
        list = (ListView) findViewById(R.id.lv_dutch_host);
        adapter = new DutchStartListViewAdapter(listViewItemList);

        list.setAdapter(adapter);

        final Boolean check = true;

        Button next = (Button) findViewById(R.id.btn_next_host);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {

                }
//                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(intent);
            }
        });
    }
}
