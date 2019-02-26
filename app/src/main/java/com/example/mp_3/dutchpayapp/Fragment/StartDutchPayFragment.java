package com.example.mp_3.dutchpayapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Activity.LoginActivity;
import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRCreate_DBAddRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.GlobalVariable;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;

import org.json.JSONObject;

public class StartDutchPayFragment extends Fragment {

    private UserInfo userInfo;
    Button btn0;    Button btn1;
    Button btn2;    Button btn3;
    Button btn4;    Button btn5;
    Button btn6;    Button btn7;
    Button btn8;    Button btn9;
    ImageButton back;    Button clear;
    Button code;    Button group;

    TextView tv;
    TextView txt_maxAmountText;

    String money = "";

    private Vibrator vibrator;
    int vibratorTime = 50;
    private DataListener dataListener;

    public static StartDutchPayFragment newInstance() {
        Bundle args = new Bundle();

        StartDutchPayFragment fragment = new StartDutchPayFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_start_dutch_pay,null);
        userInfo = UserInfo.getInstance();

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        tv = (TextView)view.findViewById(R.id.tv_money);
        txt_maxAmountText = (TextView)view.findViewById(R.id.txt_maxAmountText);

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
                vibrator.vibrate(vibratorTime);
                money = "";
                tv.setText(money+"0원");
            }
        });

        code = (Button)view.findViewById(R.id.btn_code);
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money.length() > 1) {
                    //응답받기
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    //mmoney값 전달
                                    dataListener.dataListenerSet(money);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("DB Error : ", "에러에러");
                            }
                        }
                    };
                    QRCreate_DBAddRequest qrCreateDBAddRequest = new QRCreate_DBAddRequest(userInfo.getUserID(), money, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(qrCreateDBAddRequest);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    AlertDialog dialog = builder.setMessage("최소 10원 이상이어야 합니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setCancelable(false)
                            .create();
                    dialog.show();
                }
            }
        });

        group = (Button)view.findViewById(R.id.btn_group);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mmoney값 전달
                //dataListener.dataListenerSet(money);
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