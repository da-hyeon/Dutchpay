package com.example.mp_3.dutchpayapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Activity.LoginActivity;
import com.example.mp_3.dutchpayapp.Activity.PaymentActivity.PaymentQRScanActivity;
import com.example.mp_3.dutchpayapp.Activity.PaymentHistoryActivity.DetailPaymentHistoryActivity;
import com.example.mp_3.dutchpayapp.Activity.SendMoneyActivity.FindRemittancePartnerActivity;
import com.example.mp_3.dutchpayapp.Activity.ServiceCenterActivity.CustomerServiceMainActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.ConfirmationPaymentActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.ConfirmedDutchPayActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.PaymentQRCodeCreateActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.QRCodeCreateActivity;
import com.example.mp_3.dutchpayapp.Activity.ViewMoreActivity.PaymentHistoryActivity;
import com.example.mp_3.dutchpayapp.Class.Adapter.TabPagerAdapter.TabPagerAdapter;
import com.example.mp_3.dutchpayapp.Class.Handler.BackPressCloseHandler;
import com.example.mp_3.dutchpayapp.Class.Handler.NotificationReceivedHandler;
import com.example.mp_3.dutchpayapp.Class.ProgressClass.CustomProgressDialog;
import com.example.mp_3.dutchpayapp.Class.RequestClass.UserPushIDRegisterRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.FragmentFiles.SendMoneyFragment;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.databinding.ActivityMainBinding;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataListener {

    private ActivityMainBinding mBinding;
    private BackPressCloseHandler backPressCloseHandler;
    private String userID;

    private TabPagerAdapter pagerAdapter;

    private UserInfo userInfo;
    private SharedPreferences pref;
    private SharedPreferences pref_AutoLogin;
    private TextView MainTV;

    private boolean mainTabCheck = false;

    private ImageButton btn_mainTabMenu;

    public static CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        mBinding.setActivity(this);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(new NotificationReceivedHandler(getApplicationContext()))
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
        //Log.d("userID : ", userID);

        //DB접근 -> user정보 get
        new BackGroundTask().execute();


        //상단 보유 더치머니 textview
        MainTV = (TextView) findViewById(R.id.tv_main_money);

        // Initializing the TabLayout
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("시작").setIcon(R.drawable.dutchpay_start_black_24dp));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("참여").setIcon(R.drawable.dutchpay_join_gray_24dp));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("결제").setIcon(R.drawable.solo_payment_gray_24dp));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("송금").setIcon(R.drawable.send_money_gray_24dp));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("더보기").setIcon(R.drawable.view_more_gray_24dp));
        mBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        btn_mainTabMenu = (ImageButton) findViewById(R.id.btn_mainTabMenu);
        btn_mainTabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainTabCheck){
                    btn_mainTabMenu.setImageResource(R.drawable.logo);
                    mainTabCheck = false;
                } else {
                    btn_mainTabMenu.setImageResource(R.drawable.selectmark);
                    mainTabCheck = true;
                }
            }
        });


        // Initializing ViewPager

        // Creating TabPagerAdapter adapter
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mBinding.tabLayout.getTabCount());
        mBinding.pager.setAdapter(pagerAdapter);
       // viewPager.setCurrentItem(2);
        mBinding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));

        // Set TabSelectedListener
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.pager.setCurrentItem(tab.getPosition());
                mBinding.tabLayout.getTabAt(0).setIcon(R.drawable.dutchpay_start_gray_24dp);
                mBinding.tabLayout.getTabAt(1).setIcon(R.drawable.dutchpay_join_gray_24dp);
                mBinding.tabLayout.getTabAt(2).setIcon(R.drawable.solo_payment_gray_24dp);
                mBinding.tabLayout.getTabAt(3).setIcon(R.drawable.send_money_gray_24dp);
                mBinding.tabLayout.getTabAt(4).setIcon(R.drawable.view_more_gray_24dp);

                switch (tab.getPosition()) {
                    case 0:
                        mBinding.tabLayout.getTabAt(0).setIcon(R.drawable.dutchpay_start_black_24dp);
                        break;
                    case 1:
                        mBinding.tabLayout.getTabAt(1).setIcon(R.drawable.dutchpay_join_black_24dp);
                        break;
                    case 2:
                        mBinding.tabLayout.getTabAt(2).setIcon(R.drawable.solo_payment_black_24dp);
                        break;
                    case 3:
                        mBinding.tabLayout.getTabAt(3).setIcon(R.drawable.send_money_black_24dp);
                        break;
                    case 4:
                        mBinding.tabLayout.getTabAt(4).setIcon(R.drawable.view_more_black_24dp);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
        customProgressDialog = new CustomProgressDialog(this, "QR코드를 생성하는중...");
        customProgressDialog.getWindow().setBackgroundDrawable(null);
        customProgressDialog.show();

        Intent intent = new Intent(this, QRCodeCreateActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
        finish();
    }

    @Override
    public void PayLisstenerSet(String tmp) {
        customProgressDialog = new CustomProgressDialog(this, "QR코드 스캔준비중...");
        customProgressDialog.getWindow().setBackgroundDrawable(null);
        customProgressDialog.show();

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

    @Override
    public void historyListenerSet() {
        customProgressDialog = new CustomProgressDialog(this, "결제내역 불러오는중...");
        customProgressDialog.getWindow().setBackgroundDrawable(null);
        customProgressDialog.show();

        Intent intent = new Intent(this, PaymentHistoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void sendMoneyListenerSet(String data){
        customProgressDialog = new CustomProgressDialog(this, "전화부호부 목록을 불러오는중...");
        customProgressDialog.getWindow().setBackgroundDrawable(null);
        customProgressDialog.show();

        Intent intent = new Intent(this, FindRemittancePartnerActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    @Override
    public void logoutListenerSet(){
        pref_AutoLogin = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref_AutoLogin.edit();
        editor.putString("sharedUserID", "");
        editor.putString("sharedUserPassword", "");
        editor.commit();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void customerInquiryListenerSet() {
        Intent intent = new Intent(MainActivity.this, CustomerServiceMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    private class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        String userPassword;
        String userName;
        String userPaymentPassword;
        String userPhoneNumber;
        String userEmail;
        String userPushID;
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
                    userPaymentPassword = object.getString("userPaymentPassword");
                    userName = object.getString("userName");
                    userPhoneNumber = object.getString("userPhoneNumber");
                    userEmail = object.getString("userEmail");
                    userDutchMoney = object.getInt("userDutchMoney");
                    userState = object.getInt("userState");
                    userPushID = object.getString("userPushID");
                    count++;
                }

                userInfo.setUserInfo(userID, userPassword, userPaymentPassword, userName, userPhoneNumber, userEmail, userDutchMoney, userState , userPushID);
                OneSignal.addSubscriptionObserver(mSubscriptionObserver);
                MainTV.setText(String.format("%,d", userInfo.getUserDutchMoney())+"원");

                //user의 결제상태가 진행중이라면 앱 내에 저장되어있는 QRCODE데이터를 Load하여 QRCodeCreateActivity로 전달.
                if (userState == 1) {

                    customProgressDialog = new CustomProgressDialog(MainActivity.this, "QR코드를 생성하는중...");
                    customProgressDialog.getWindow().setBackgroundDrawable(null);
                    customProgressDialog.show();

                    Intent intent = new Intent(MainActivity.this, QRCodeCreateActivity.class);
                    intent.putExtra("data", userInfo.getUserQRCode());
                    startActivity(intent);

                }

                //host가 결제를 확정지었을 경우 진입.
                else if (userState == 2) {
                    Intent intent = new Intent(MainActivity.this, ConfirmedDutchPayActivity.class);
                    startActivity(intent);
                    finish();
                }

                //user가 결제를 완료했을때 결제현황Activity진입
                else if( userState == 3){
                    Intent intent = new Intent(MainActivity.this, ConfirmationPaymentActivity.class);
                    startActivity(intent);
                    finish();
                }

                else if( userState == 4){
                    Intent intent = new Intent(MainActivity.this, PaymentQRCodeCreateActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //user의 결제상태가 진행중이라면 앱 종료시 QRCODE의 data를 앱 내에 저장.
        if (userInfo.getUserState() == 1) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("qrData", userInfo.getUserQRCode());
            editor.commit();
        }
    }


}