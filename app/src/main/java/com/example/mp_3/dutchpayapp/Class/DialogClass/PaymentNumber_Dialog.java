package com.example.mp_3.dutchpayapp.Class.DialogClass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mp_3.dutchpayapp.Activity.PaymentActivity.PaymentCheckActivity;
import com.example.mp_3.dutchpayapp.R;

import static android.support.v4.content.ContextCompat.startActivity;

public class PaymentNumber_Dialog extends Dialog implements View.OnClickListener{
    private Context context;

    public PaymentNumber_Dialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_payment_number);

        final EditText part1 = (EditText) findViewById(R.id.et_pay_num1);
        final EditText part2 = (EditText) findViewById(R.id.et_pay_num2);
        final EditText part3 = (EditText) findViewById(R.id.et_pay_num3);
        final EditText part4 = (EditText) findViewById(R.id.et_pay_num4);

        Button next = (Button) findViewById(R.id.btn_payment_number_check);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(part1.getText().toString().equals("")||
                        part2.getText().toString().equals("")||
                        part3.getText().toString().equals("")||
                        part4.getText().toString().equals("")){
                    Toast.makeText(getContext(),"결제번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                } else {
                    //액티비티 호출
                    Intent intent = new Intent(getContext(), PaymentCheckActivity.class);
                    intent.putExtra("data", part1.getText().toString() + part2.getText().toString() + part3.getText().toString() + part4.getText().toString());
                    startActivity(getContext(), intent, null);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}