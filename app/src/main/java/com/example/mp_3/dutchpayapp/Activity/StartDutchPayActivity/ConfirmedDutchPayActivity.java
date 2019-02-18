package com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.DutchStartConfirmedListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.DutchStartListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStart;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStartConfirmed;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
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

public class ConfirmedDutchPayActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ArrayList<ListViewItem_DutchStartConfirmed> listViewItemList;
    private DutchStartConfirmedListViewAdapter adapter;
    private ListView list;
    private TextView tv_totalCost;

    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_dutch_pay);

        toolbar = findViewById(R.id.tv_memberCount);
        userInfo = UserInfo.getInstance();

        listViewItemList = new ArrayList<>();
        list = (ListView) findViewById(R.id.lv_dutch_host);
        tv_totalCost = (TextView) findViewById(R.id.tv_totalCost);

        new BackGroundTask().execute();
    }
    private class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        String hostID;
        String userID;
        int Amount;
        int assignedAmount;
        int prePaymentCheck;


        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/DutchPay_JoinListSelect.php?userID=" + URLEncoder.encode(userInfo.getUserID(), "UTF-8");
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
                    hostID = object.getString("hostID");
                    userID = object.getString("userID");
                    Amount = object.getInt("Amount");
                    assignedAmount = object.getInt("assignedAmount");
                    prePaymentCheck = object.getInt("prePayment");

                    listViewItemList.add(new ListViewItem_DutchStartConfirmed(count, hostID, userID, Amount, assignedAmount , prePaymentCheck));
                    count++;
                }

                toolbar.setTitle("모집된 멤버 ( " + count + "명 )");
                tv_totalCost.setText("총 금액 " + String.format("%,d", Amount)   + "원");
                adapter = new DutchStartConfirmedListViewAdapter(getApplicationContext(), listViewItemList, count , Amount);
                list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
