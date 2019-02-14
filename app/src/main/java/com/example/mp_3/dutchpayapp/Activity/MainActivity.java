package com.example.mp_3.dutchpayapp.Activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.QRCodeCreateActivity;
import com.example.mp_3.dutchpayapp.Class.Adapter.TabPagerAdapter;
import com.example.mp_3.dutchpayapp.Class.Handler.BackPressCloseHandler;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.GlobalVariable;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Fragment.StartDutchPayFragment;
import com.example.mp_3.dutchpayapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements StartDutchPayFragment.DataListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BackPressCloseHandler backPressCloseHandler;
    private String userID;

    private GlobalVariable globalVariable;
    private TabPagerAdapter pagerAdapter;

    private UserInfo userInfo;

    TextView MainTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backPressCloseHandler = new BackPressCloseHandler(this);
        globalVariable = GlobalVariable.getInstance();
        userInfo = UserInfo.getInstance();

        //userID
        userID = getIntent().getExtras().getString("userID");
        Log.d("userID : " , userID);

        new BackGroundTask().execute();

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
                Log.d("getPosition : ", tab.getPosition() + "");

                if (tab.getPosition() == 0) {

                    if (globalVariable.getStartPageState() == 0) {

                    }
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

    @Override
    public void dataListenerSet(String tmp) {
        Intent intent = new Intent(this, QRCodeCreateActivity.class);
        intent.putExtra("tmp", tmp);
        startActivity(intent);
        finish();
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
                    userEmail = object.getString("userEmail");
                    userDutchMoney = object.getInt("userDutchMoney");
                    count++;
                }

                userInfo.setUserInfo(userID, userPassword, userName, userEmail, userDutchMoney);

                MainTV.setText("더치머니 "+ userInfo.getUserDutchMoney() + "원 보유");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}