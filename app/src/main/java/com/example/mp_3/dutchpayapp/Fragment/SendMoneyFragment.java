package com.example.mp_3.dutchpayapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.ProgressClass.CustomProgressDialog;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;

public class SendMoneyFragment extends Fragment {

    Button btn0;    Button btn1;
    Button btn2;    Button btn3;
    Button btn4;    Button btn5;
    Button btn6;    Button btn7;
    Button btn8;    Button btn9;
    ImageButton back;    Button clear;
    Button btn_send_Money;

    private TextView tv;
    private TextView txt_maxAmountText;

    private String money = "";


    private Vibrator vibrator;
    int vibratorTime = 50;

    private DataListener dataListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof DataListener) {
            dataListener = (DataListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        dataListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_money,null);

        tv = (TextView)view.findViewById(R.id.tv_money);
        txt_maxAmountText = (TextView) view.findViewById(R.id.txt_maxAmountText);

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        btn0 = (Button)view.findViewById(R.id.btn_0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(money.length() > 0 ) {
                    money = money + "0";
                    tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                }
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn1 = (Button)view.findViewById(R.id.btn_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "1";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn2 = (Button)view.findViewById(R.id.btn_2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "2";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn3 = (Button)view.findViewById(R.id.btn_3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "3";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn4 = (Button)view.findViewById(R.id.btn_4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "4";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn5 = (Button)view.findViewById(R.id.btn_5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "5";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn6 = (Button)view.findViewById(R.id.btn_6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "6";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn7 = (Button)view.findViewById(R.id.btn_7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "7";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn8 = (Button)view.findViewById(R.id.btn_8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "8";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        btn9 = (Button)view.findViewById(R.id.btn_9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + "9";
                tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                vibrator.vibrate(vibratorTime);
                textViewWatcher();
            }
        });
        back = (ImageButton)view.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewWatcher();
                vibrator.vibrate(vibratorTime);
                int cut_l = money.length()-1;
                if(cut_l < 0){
                    cut_l = 0;
                }

                money = money.substring(0, cut_l);
                if(money.equals("")){
                    tv.setText("0원");
                } else {
                    tv.setText(String.format("%,d", Integer.parseInt(money)) + "원");
                }

            }
        });
        clear = (Button)view.findViewById(R.id.btn_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewWatcher();
                money = "";
                tv.setText(money+"0원");
            }
        });

        btn_send_Money = (Button) view.findViewById(R.id.btn_send_Money);
        btn_send_Money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataListener.sendMoneyListenerSet(money);
            }
        });

        return view;
    }

    public void textViewWatcher() {
        if(money.length() >= 7){
            if(Integer.parseInt(money) > 2000000){
                txt_maxAmountText.setVisibility(View.VISIBLE);
                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                tv.startAnimation(shake);
                txt_maxAmountText.startAnimation(shake);
                vibrator.vibrate(300);
                tv.setText(String.format("%,d", 2000000)+"원");
                money = "2000000";
            }
            else {
                txt_maxAmountText.setVisibility(View.GONE);
            }
        }
    }

}
