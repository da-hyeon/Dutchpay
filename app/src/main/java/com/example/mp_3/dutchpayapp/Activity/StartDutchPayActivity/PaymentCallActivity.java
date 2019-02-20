package com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Activity.PaymentActivity.PaymentPasswordCheckActivity;
import com.example.mp_3.dutchpayapp.R;

public class PaymentCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_call);

        TextView member_num = (TextView)findViewById(R.id.tv_member_num);
        TextView total = (TextView)findViewById(R.id.tv_total_cost);
        TextView cost = (TextView)findViewById(R.id.tv_cost_dutch);
        //DB작업

        Button next = (Button) findViewById(R.id.btn_next_dutch);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제 비밀번호 확인 창 이동
                Intent intent = new Intent(getApplicationContext(),PaymentPasswordCheckActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button back = (Button) findViewById(R.id.btn_back_dutch);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB 작업
                finish();
            }
        });


    }
}
