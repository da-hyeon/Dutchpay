package com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.DutchStartConfirmedListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.DutchStartListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStart;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStartConfirmed;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
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
    private Button btn_next_host;
    private Button btn_before_host;

    private SharedPreferences pref;

    private String targetHostID;
    private String totalAmount;
    private String totalParticipantCount;
    private String myAssignedAmount;

    private UserInfo userInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_dutch_pay);

        pref = getSharedPreferences("hostID", MODE_PRIVATE);
        targetHostID = pref.getString("hostID", "");

        toolbar = findViewById(R.id.tv_memberCount);
        userInfo = UserInfo.getInstance();

        listViewItemList = new ArrayList<>();
        list = (ListView) findViewById(R.id.lv_dutch_host);
        tv_totalCost = (TextView) findViewById(R.id.tv_totalCost);

        btn_next_host = (Button) findViewById(R.id.btn_next_host);

        new BackGroundTask().execute();

        btn_next_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmedDutchPayActivity.this , PaymentCallActivity.class);
                if(targetHostID.equals("")) {
                    intent.putExtra("hostID", userInfo.getUserID());
                } else{
                    intent.putExtra("hostID", targetHostID);
                }
                intent.putExtra("totalParticipantCount" , totalParticipantCount);
                intent.putExtra("totalAmount" , totalAmount);
                intent.putExtra("assignedAmount" , myAssignedAmount);
                startActivity(intent);
            }
        });

        btn_before_host = (Button) findViewById(R.id.btn_before_host);
        btn_before_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class BackGroundTask extends AsyncTask<Void, Void, String> {
        private String target;

        private String hostID;
        private String userID;
        private int Amount;
        private int assignedAmount;
        private int prePaymentCheck;


        @Override
        protected void onPreExecute() {
            try {


                //접속한 사람이 호스트일경우
                if(targetHostID.equals(""))
                      target = "http://kjg123kg.cafe24.com/DutchPay_JoinListSelect.php?userID=" + URLEncoder.encode(userInfo.getUserID(), "UTF-8");
                //접속한 사람이 참여자일경우
                else {
                    target = "http://kjg123kg.cafe24.com/DutchPay_JoinListSelect.php?userID=" + URLEncoder.encode(targetHostID, "UTF-8");
                }
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

                    if(userID.equals(userInfo.getUserID())){
                        myAssignedAmount = assignedAmount+"";
                    }

                    listViewItemList.add(new ListViewItem_DutchStartConfirmed(count, hostID, userID, Amount, assignedAmount , prePaymentCheck));
                    count++;
                }

                toolbar.setTitle("모집된 멤버 ( " + count + "명 )");
                tv_totalCost.setText("총 금액 " + String.format("%,d", Amount)   + "원");
                totalAmount = Amount+"";
                totalParticipantCount = count+"";
                adapter = new DutchStartConfirmedListViewAdapter(getApplicationContext(), listViewItemList, count , Amount);
                list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
