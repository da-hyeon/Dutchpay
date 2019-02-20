package com.example.mp_3.dutchpayapp.Activity.PaymentActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Activity.LoginActivity;
import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.example.mp_3.dutchpayapp.Class.DialogClass.PaymentCharge_Dialog;
import com.example.mp_3.dutchpayapp.Class.DialogClass.PaymentNumber_Dialog;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;

import static android.support.v4.content.ContextCompat.startActivity;


public class PaymentCheckActivity extends AppCompatActivity {

    TextView textView13;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_check);

        textView13 = (TextView) findViewById(R.id.textView13);

        final String check = getIntent().getStringExtra("data");

        UserInfo userinfo = UserInfo.getInstance();
        final String user = userinfo.getUserID();
        if(!check.equals("0000")) {
            textView13.setText("결제금액 : 10,000원");
        }

        Button pay = (Button) findViewById(R.id.btn_pay_dutchmoney);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check.equals("0000")){
                    //결제 실패 flow
                    PaymentCharge_Dialog dialog = new PaymentCharge_Dialog(PaymentCheckActivity.this);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                }else {
                    Intent intent = new Intent(PaymentCheckActivity.this,PaymentPasswordCheckActivity.class);
                    startActivity(intent);
//                    //결제 완료 flow
//                    AlertDialog.Builder builder = new AlertDialog.Builder(PaymentCheckActivity.this);
//                    AlertDialog dialog = builder.setTitle("결제 완료")
//                            .setMessage("상점명 : oo점\n결제 일시 : 2019.02.00 ##:@@:**\n결제금액 : ##원\n결제방식 : 더치머니")
//                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //메인으로 이동
//                                    Intent intent = new Intent(PaymentCheckActivity.this, MainActivity.class);
//                                    intent.putExtra("userID",user);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }).setCancelable(false)
//                            .create();
//                    dialog.show();

                }

            }
        });

    }
}