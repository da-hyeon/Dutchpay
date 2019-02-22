package com.example.mp_3.dutchpayapp.Activity.PaymentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_password_check);

        userInfo = UserInfo.getInstance();

        _PaymentCallActivity = PaymentCallActivity._PaymentCallActivity;
        _ConfirmedDutchPayActivity = ConfirmedDutchPayActivity._ConfirmedDutchPayActivity;

        pref = getSharedPreferences("hostID", MODE_PRIVATE);
        targetHostID = pref.getString("hostID", "");

        mPostReference = FirebaseDatabase.getInstance().getReference("userInfo");
        totalParticipantCount = getIntent().getExtras().getString("totalParticipantCount");
        assignedAmount = getIntent().getExtras().getString("assignedAmount");
        currentTotalParticipantCount = 0;

        firebasePostArrayList = new ArrayList<>();

        Button btn0;    Button btn1;
        Button btn2;    Button btn3;
        Button btn4;    Button btn5;
        Button btn6;    Button btn7;
        Button btn8;    Button btn9;
        Button back;    Button clear;
        Button next;

        final TextView password;

        password = (TextView)findViewById(R.id.tv_password);

        btn0 = (Button)findViewById(R.id.btn_0_pay);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "0";
                password.setText(tmp);
            }
        });
        btn1 = (Button)findViewById(R.id.btn_1_pay);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "1";
                password.setText(tmp);
            }
        });
        btn2 = (Button)findViewById(R.id.btn_2_pay);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "2";
                password.setText(tmp);
            }
        });
        btn3 = (Button)findViewById(R.id.btn_3_pay);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "3";
                password.setText(tmp);
            }
        });
        btn4 = (Button)findViewById(R.id.btn_4_pay);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "4";
                password.setText(tmp);
            }
        });
        btn5 = (Button)findViewById(R.id.btn_5_pay);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "5";
                password.setText(tmp);
            }
        });
        btn6 = (Button)findViewById(R.id.btn_6_pay);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "6";
                password.setText(tmp);
            }
        });
        btn7 = (Button)findViewById(R.id.btn_7_pay);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "7";
                password.setText(tmp);
            }
        });
        btn8 = (Button)findViewById(R.id.btn_8_pay);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "8";
                password.setText(tmp);
            }
        });
        btn9 = (Button)findViewById(R.id.btn_9_pay);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString() + "9";
                password.setText(tmp);
            }
        });
        back = (Button)findViewById(R.id.btn_back_pay);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = password.getText().toString();
                if(!tmp.equals("")){
                    int cut_l = tmp.length()-1;

                    if(cut_l<0){ cut_l = 0; }
                    tmp = tmp.substring(0,cut_l);
                    password.setText(tmp);
                }
            }
        });

        clear = (Button)findViewById(R.id.btn_clear_pay);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText("");
            }
        });



        next = (Button)findViewById(R.id.btn_next_pay);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();

                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                String getTime = sdf.format(date);


                if (password.getText().toString().equals(userinfo.getUserPaymentPassword())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PaymentPasswordCheckActivity.this);
                    AlertDialog dialog = builder.setTitle("결제 완료")
                            .setMessage("결제 일시 : " + getTime + "\n결제 금액 : " + String.format("%,d", Integer.parseInt(assignedAmount))+ "원\n결제 방식 : 더치머니")
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
                                                } else{

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.d("DB Error : ", "에러에러");
                                            }
                                        }
                                    };
                                    UserStateChangeRequest userStateChangeRequest = new UserStateChangeRequest(userInfo.getUserID(), userinfo.getUserDutchMoney()+"", "3" , responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(PaymentPasswordCheckActivity.this);
                                    queue.add(userStateChangeRequest);


                                    //메인으로 이동
//                                    Intent intent = new Intent(PaymentPasswordCheckActivity.this, MainActivity.class);
//                                    intent.putExtra("userID", user);
//                                    startActivity(intent);
//                                    finish();
                                }
                            }).setCancelable(false)
                            .create();
                    dialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void postFirebaseDatabase(){

        if(targetHostID.equals("")) {
            mPostReference.child(userinfo.getUserID()).push().setValue(new FirebasePost( userinfo.getUserID() , userinfo.getUserID() , true));

        } else{
            mPostReference.child(targetHostID).push().setValue(new FirebasePost( targetHostID , userinfo.getUserID() ,true));
        }
    }
}