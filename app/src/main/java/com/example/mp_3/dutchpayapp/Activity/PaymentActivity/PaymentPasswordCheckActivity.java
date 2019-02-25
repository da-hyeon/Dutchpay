package com.example.mp_3.dutchpayapp.Activity.PaymentActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Activity.LoginActivity;
import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.ConfirmationPaymentActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.ConfirmedDutchPayActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.PaymentCallActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.QRCodeCreateActivity;
import com.example.mp_3.dutchpayapp.Class.FireBaseClass.FirebasePost;
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRCancel_DBDeleteRequest;
import com.example.mp_3.dutchpayapp.Class.RequestClass.UserStateChangeRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PaymentPasswordCheckActivity extends AppCompatActivity {

    UserInfo userinfo = UserInfo.getInstance();

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


    private DatabaseReference mPostReference;

    private SharedPreferences pref;

    private String targetHostID;
    private String totalParticipantCount;
    private String assignedAmount;
    private int currentTotalParticipantCount;

    private ArrayList<FirebasePost> firebasePostArrayList;

    private PaymentCallActivity _PaymentCallActivity;
    private ConfirmedDutchPayActivity _ConfirmedDutchPayActivity;

    private UserInfo userInfo;

    private TextView txt_title;
    private ImageView radio_pw[] = new ImageView[6];
    private Vibrator vibrator;

    private String tmp = "";

    int vibratorTime = 50;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_password_check);

        userInfo = UserInfo.getInstance();

        txt_title = (TextView) findViewById(R.id.txt_title);


        _PaymentCallActivity = PaymentCallActivity._PaymentCallActivity;
        _ConfirmedDutchPayActivity = ConfirmedDutchPayActivity._ConfirmedDutchPayActivity;

        pref = getSharedPreferences("hostID", MODE_PRIVATE);
        targetHostID = pref.getString("hostID", "");

        mPostReference = FirebaseDatabase.getInstance().getReference("userInfo");
        totalParticipantCount = getIntent().getExtras().getString("totalParticipantCount");
        assignedAmount = getIntent().getExtras().getString("assignedAmount");
        currentTotalParticipantCount = 0;

        firebasePostArrayList = new ArrayList<>();

        int radioID[] = {R.id.radio_pw1, R.id.radio_pw2, R.id.radio_pw3, R.id.radio_pw4, R.id.radio_pw5, R.id.radio_pw6};

        for (int i = 0; i < radio_pw.length; i++) {
            radio_pw[i] = (ImageView) findViewById(radioID[i]);
        }

        txt_title.setText("비밀번호가 일치하면\n" + String.format("%,d", Integer.parseInt(assignedAmount)) + "원을 결제합니다.");
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
        int length = password.length();

        if (password.length() < radio_pw.length) {
            for (int i = 0; i < length; i++) {
                radio_pw[i].setImageResource(R.drawable.password_entered);
            }

            for (int i = length; i < radio_pw.length; i++) {
                radio_pw[i].setImageResource(R.drawable.passworkd_notenterd);
            }

        } else {
            long now = System.currentTimeMillis();

            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            String getTime = sdf.format(date);

            if (password.equals(userinfo.getUserPaymentPassword())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentPasswordCheckActivity.this);
                AlertDialog dialog = builder.setTitle("결제 완료")
                        .setMessage("결제 일시 : " + getTime + "\n결제 금액 : " + String.format("%,d", Integer.parseInt(assignedAmount)) + "원\n결제 방식 : 더치머니")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userinfo.setUserDutchMoney(userInfo.getUserDutchMoney() - Integer.parseInt(assignedAmount));
                                //응답받기
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");

                                            if (success) {
                                                postFirebaseDatabase();

                                                Intent intent = new Intent(PaymentPasswordCheckActivity.this, ConfirmationPaymentActivity.class);
                                                startActivity(intent);
                                                _ConfirmedDutchPayActivity.finish();
                                                _PaymentCallActivity.finish();
                                                finish();
                                            } else {

                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.d("DB Error : ", "에러에러");
                                        }
                                    }
                                };
                                UserStateChangeRequest userStateChangeRequest = new UserStateChangeRequest(userInfo.getUserID(), userinfo.getUserDutchMoney() + "", "3", responseListener);
                                RequestQueue queue = Volley.newRequestQueue(PaymentPasswordCheckActivity.this);
                                queue.add(userStateChangeRequest);

                            }
                        }).setCancelable(false)
                        .create();
                dialog.show();
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                tmp = "";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        }
    }

    public void postFirebaseDatabase() {

        if (targetHostID.equals("")) {
            mPostReference.child(userinfo.getUserID()).push().setValue(new FirebasePost(userinfo.getUserID(), userinfo.getUserID(), true));

        } else {
            mPostReference.child(targetHostID).push().setValue(new FirebasePost(targetHostID, userinfo.getUserID(), true));
        }
    }
}