package com.example.mp_3.dutchpayapp.Activity.SendMoneyActivity;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.R;

public class SendMoneyPasswordCheckActivity extends AppCompatActivity {

    private TextView txt_title;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private ImageButton back;
    private Button clear;

    private ImageView radio_pw[] = new ImageView[6];
    private Vibrator vibrator;

    private String tmp = "";
    private String amount;

    int vibratorTime = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_password_check);

        txt_title = (TextView) findViewById(R.id.txt_title);
        amount = getIntent().getExtras().getString("amount");

        txt_title.setText("비밀번호가 일치하면\n"+ String.format("%,d", Integer.parseInt(amount)) +"원을 송금합니다.");


        int radioID[] = {R.id.radio_pw1, R.id.radio_pw2, R.id.radio_pw3, R.id.radio_pw4, R.id.radio_pw5, R.id.radio_pw6};

        for (int i = 0; i < radio_pw.length; i++) {
            radio_pw[i] = (ImageView) findViewById(radioID[i]);
        }

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn0 = (Button) findViewById(R.id.btn_0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "0";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn1 = (Button) findViewById(R.id.btn_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "1";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn2 = (Button) findViewById(R.id.btn_2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "2";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn3 = (Button) findViewById(R.id.btn_3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "3";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn4 = (Button) findViewById(R.id.btn_4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "4";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn5 = (Button) findViewById(R.id.btn_5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "5";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn6 = (Button) findViewById(R.id.btn_6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "6";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn7 = (Button) findViewById(R.id.btn_7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "7";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn8 = (Button) findViewById(R.id.btn_8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "8";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn9 = (Button) findViewById(R.id.btn_9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "9";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });

        back = (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tmp.equals("")) {
                    int cut_l = tmp.length() - 1;

                    if (cut_l < 0) {
                        cut_l = 0;
                    }
                    tmp = tmp.substring(0, cut_l);
                    ImageViewChange(tmp);
                    vibrator.vibrate(vibratorTime);
                }
            }
        });

        clear = (Button) findViewById(R.id.btn_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = "";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });

    }

    public void ImageViewChange(String password) {
        Log.d("password : ", password);
        int length = password.length();

        if (password.length() <= radio_pw.length) {
            for (int i = 0; i < length; i++) {
                radio_pw[i].setImageResource(R.drawable.password_entered);
            }
        }

        for (int i = length; i < radio_pw.length; i++) {
            radio_pw[i].setImageResource(R.drawable.passworkd_notenterd);
        }
    }
}
