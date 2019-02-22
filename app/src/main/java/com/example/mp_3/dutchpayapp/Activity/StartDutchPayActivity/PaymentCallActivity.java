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

    private String hostID;
    private String totalParticipantCount;
    private String totalAmount;
    private String assignedAmount;

    public static PaymentCallActivity _PaymentCallActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_call);

        _PaymentCallActivity = PaymentCallActivity.this;

        TextView tv_host_name = (TextView)findViewById(R.id.tv_host_name);
        TextView tv_member_num = (TextView)findViewById(R.id.tv_member_num);
        TextView tv_total_cost = (TextView)findViewById(R.id.tv_total_cost);
        TextView tv_cost_dutch = (TextView)findViewById(R.id.tv_cost_dutch);
        //DB작업

        hostID = getIntent().getExtras().getString("hostID");
        totalParticipantCount= getIntent().getExtras().getString("totalParticipantCount");
        totalAmount = getIntent().getExtras().getString("totalAmount");
        assignedAmount = getIntent().getExtras().getString("assignedAmount");

        tv_host_name.setText(hostID+"님");
        tv_member_num.setText(totalParticipantCount+"명");
        tv_total_cost.setText(String.format("%,d", Integer.parseInt(totalAmount)) + "원");
        tv_cost_dutch.setText(String.format("%,d", Integer.parseInt(assignedAmount)) + "원");

        Button next = (Button) findViewById(R.id.btn_next_dutch);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제 비밀번호 확인 창 이동
                Intent intent = new Intent(getApplicationContext(),PaymentPasswordCheckActivity.class);
                intent.putExtra("assignedAmount" , assignedAmount);
                intent.putExtra("totalParticipantCount", totalParticipantCount);
                startActivity(intent);

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
