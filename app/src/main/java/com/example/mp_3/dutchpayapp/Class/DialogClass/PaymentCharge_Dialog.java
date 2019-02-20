package com.example.mp_3.dutchpayapp.Class.DialogClass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.mp_3.dutchpayapp.Activity.PaymentActivity.PaymentPasswordCheckActivity;
import com.example.mp_3.dutchpayapp.R;

import static android.support.v4.content.ContextCompat.startActivity;

public class PaymentCharge_Dialog extends Dialog implements View.OnClickListener{
    private Context context;

    public PaymentCharge_Dialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_payment_charge);

        Button charge = (Button)findViewById(R.id.btn_payment_charge);
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PaymentPasswordCheckActivity.class);
                startActivity(getContext(), intent, null);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}