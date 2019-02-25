package com.example.mp_3.dutchpayapp.Activity.PaymentHistoryActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.R;

import java.util.ArrayList;

public class DetailPaymentHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_payment_history);

        ArrayList<String> data =  getIntent().getStringArrayListExtra("tmp");

        TextView date = (TextView)findViewById(R.id.tv_date_detail);
        TextView shop = (TextView)findViewById(R.id.tv_shop_detail);
        TextView member = (TextView)findViewById(R.id.tv_member_detail);
        TextView cost = (TextView)findViewById(R.id.tv_cost_detail);

        date.setText(data.get(0));
        shop.setText(data.get(1));
        member.setText(data.get(2));
        cost.setText(String.format("%,d", Integer.parseInt(data.get(3))) + "원");


    }



}