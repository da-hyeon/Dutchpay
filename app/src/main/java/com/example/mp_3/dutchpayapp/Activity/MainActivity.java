package com.example.mp_3.dutchpayapp.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Activity.PaymentActivity.PaymentQRScanActivity;
import com.example.mp_3.dutchpayapp.Activity.PaymentHistoryActivity.DetailPaymentHistoryActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.ConfirmedDutchPayActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.QRCodeCreateActivity;
import com.example.mp_3.dutchpayapp.Class.Adapter.TabPagerAdapter.TabPagerAdapter;
import com.example.mp_3.dutchpayapp.Class.Handler.BackPressCloseHandler;
import com.example.mp_3.dutchpayapp.Class.RequestClass.UserPushIDRegisterRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements DataListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BackPressCloseHandler backPressCloseHandler;
    private String userID;

    private TabPagerAdapter pagerAdapter;

    private UserInfo userInfo;
    private SharedPreferences pref;
    private SharedPreferences pref_AutoLogin;
    private TextView MainTV;

    private Button btn_Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        //뒤로가기
        backPressCloseHandler = new BackPressCloseHandler(this);

        //유저정보 load
        userInfo = UserInfo.getInstance();

        //앱 내에 저장된 데이터 Load
        pref = getSharedPreferences("QR_CODE", MODE_PRIVATE);
        userInfo.setUserQRCode(pref.getString("qrData", ""));

        //userID
        userID = getIntent().getExtras().getString("userID");
        Log.d("userID : ", userID);

        //DB접근 -> user정보 get
        new BackGroundTask().execute();


        //상단 보유 더치머니 textview
        MainTV = (TextView) findViewById(R.id.tv_main_money);

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("더치페이 시작하기"));
        tabLayout.addTab(tabLayout.newTab().setText("더치페이 참여하기"));
        tabLayout.addTab(tabLayout.newTab().setText("단독결제 시작하기"));
        tabLayout.addTab(tabLayout.newTab().setText("이용내역"));
        tabLayout.addTab(tabLayout.newTab().setText("더보기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn_Logout = (Button) findViewById(R.id.btn_Logout);
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref_AutoLogin = getSharedPreferences("AutoLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref_AutoLogin.edit();
                editor.putString("sharedUserID", "");
                editor.putString("sharedUserPassword", "");
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private OSSubscriptionObserver mSubscriptionObserver = new OSSubscriptionObserver() {

        @Override
        public void onOSSubscriptionChanged(OSSubscriptionStateChanges stateChanges) {
            // The user is subscribed
            // Either the user subscribed for the first time
            // Or the user was subscribed -> unsubscribed -> subscribed
            Log.d("getUserID : ", stateChanges.getTo().getUserId());

            // Make a POST call to your server with the user ID
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DB Error : ", "에러에러");
                    }
                }
            };
            UserPushIDRegisterRequest userPushIDRegisterRequest = new UserPushIDRegisterRequest(userInfo.getUserID(), stateChanges.getTo().getUserId(), responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(userPushIDRegisterRequest);

        }
    };

//    @Override
//    public void onOSSubscriptionChanged(OSSubscriptionStateChanges stateChanges) {
//
//
//    }

    @Override
    public void dataListenerSet(String data) {
        Intent intent = new Intent(this, QRCodeCreateActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    @Override
    public void PayLisstenerSet(String tmp) {
        Intent intent = new Intent(this, PaymentQRScanActivity.class);
        intent.putExtra("tmp", tmp);
        startActivity(intent);
    }

    @Override
    public void ArrayListenerSet(ArrayList<String> tmp) {
        Intent intent = new Intent(this, DetailPaymentHistoryActivity.class);
        intent.putExtra("tmp", tmp);
        startActivity(intent);
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    private class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        String userPassword;
        String userName;
        String userPaymentPassword;
        String userEmail;
        int userDutchMoney;
        int userState;

        UserInfo userInfo;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/DutchPay_UserInfo.php?userID=" + URLEncoder.encode(userID, "UTF-8");

                userInfo = UserInfo.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    userPassword = object.getString("userPassword");
                    userName = object.getString("userName");
                    userPaymentPassword = object.getString("userPaymentPassword");
                    userEmail = object.getString("userEmail");
                    userDutchMoney = object.getInt("userDutchMoney");
                    userState = object.getInt("userState");
                    count++;
                }

                userInfo.setUserInfo(userID, userPassword, userPaymentPassword, userName, userEmail, userDutchMoney, userState);
                OneSignal.addSubscriptionObserver(mSubscriptionObserver);
                MainTV.setText("더치머니 " + String.format("%,d", userInfo.getUserDutchMoney()) + "원 보유");

                //user의 결제상태가 진행중이라면 앱 내에 저장되어있는 QRCODE데이터를 Load하여 QRCodeCreateActivity로 전달.
                if (userState == 1) {
                    Intent intent = new Intent(MainActivity.this, QRCodeCreateActivity.class);
                    intent.putExtra("data", userInfo.getUserQRCode());
                    startActivity(intent);
                }
                //host가 결제를 확정지었을 경우 진입.
                else if (userState == 2) {
                    Intent intent = new Intent(MainActivity.this, ConfirmedDutchPayActivity.class);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //user의 결제상태가 진행중이라면 앱 종료시 QRCODE의 data를 앱 내에 저장.
        if (userInfo.getUserState() == 1) {

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("qrData", userInfo.getUserQRCode());
            editor.commit();
        }
    }
}