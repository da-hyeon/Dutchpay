package com.example.mp_3.dutchpayapp.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Activity.PaymentHistoryActivity.DetailPaymentHistoryActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.QRCodeCreateActivity;
import com.example.mp_3.dutchpayapp.Class.Adapter.TabPagerAdapter.TabPagerAdapter;
import com.example.mp_3.dutchpayapp.Class.Handler.BackPressCloseHandler;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BackPressCloseHandler backPressCloseHandler;
    private String userID;

    private TabPagerAdapter pagerAdapter;

    private UserInfo userInfo;
    private SharedPreferences pref;

    private TextView MainTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뒤로가기
        backPressCloseHandler = new BackPressCloseHandler(this);

        //유저정보 load
        userInfo = UserInfo.getInstance();

        //앱 내에 저장된 데이터 Load
        pref = getSharedPreferences("QR_CODE", MODE_PRIVATE);
        userInfo.setUserQRCode(pref.getString("qrData", ""));


        //userID
        userID = getIntent().getExtras().getString("userID");
        Log.d("userID : " , userID);

        //DB접근 -> user정보 get
        new BackGroundTask().execute();



        //상단 보유 더치머니 textview
        MainTV = (TextView)findViewById(R.id.tv_main_money);


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
    }

    @Override
    public void dataListenerSet(String data) {
        Intent intent = new Intent(this, QRCodeCreateActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    @Override
    public void ArrayListenerSet (ArrayList<String> tmp) {
        Intent intent = new Intent(this, DetailPaymentHistoryActivity.class);
        intent.putExtra("tmp",tmp);
        startActivity(intent);
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    private class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        String userPassword;
        String userName;
        String userEmail;
        int userDutchMoney;
        int userState;

        UserInfo userInfo;
        boolean userStateChecked;
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
                    userEmail = object.getString("userEmail");
                    userDutchMoney = object.getInt("userDutchMoney");
                    userState = object.getInt("userState");
                    count++;
                }
                if(userState == 1)
                    userStateChecked = true;
                else
                    userStateChecked = false;

                userInfo.setUserInfo(userID, userPassword, userName, userEmail, userDutchMoney , userStateChecked);

                MainTV.setText("더치머니 "+ userInfo.getUserDutchMoney() + "원 보유");

                //user의 결제상태가 진행중이라면 앱 내에 저장되어있는 QRCODE데이터를 Load하여 QRCodeCreateActivity로 전달.
                if(userStateChecked) {
                    Intent intent = new Intent(MainActivity.this , QRCodeCreateActivity.class);
                    intent.putExtra("data", userInfo.getUserQRCode());
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
        if(userInfo.isUserState()) {

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("qrData", userInfo.getUserQRCode());
            editor.commit();
        }
    }
}