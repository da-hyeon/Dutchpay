package com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.DutchStartListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStart;
import com.example.mp_3.dutchpayapp.Class.RequestClass.ParticipantInfoUpdateRequest;
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

public class DutchGroupControlActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private ArrayList<ListViewItem_DutchStart> listViewItemList;
    private ArrayList<String> userPushIDList;

    private DutchStartListViewAdapter adapter;
    private ListView list;
    private TextView tv_totalCost;
    private Button btn_next_host;
    private Button btn_before_host;
    UserInfo userInfo;

    private QRCodeCreateActivity _QRCodeCreateActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch_group_control);

        _QRCodeCreateActivity = QRCodeCreateActivity._QRCodeCreateActivity;

        toolbar = findViewById(R.id.tv_memberCount);
        userInfo = UserInfo.getInstance();

        userPushIDList = new ArrayList<>();
        listViewItemList = new ArrayList<>();

        list = (ListView) findViewById(R.id.lv_dutch_host);
        tv_totalCost = (TextView) findViewById(R.id.tv_totalCost);

        new BackGroundTask_ParticipantUserList().execute();

        btn_next_host = (Button) findViewById(R.id.btn_next_host);
        btn_next_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DutchGroupControlActivity.this);
                AlertDialog dialog = builder.setMessage("금액을 확정지으시겠습니까?")
                        .setPositiveButton("확인", (dialog12, which) -> {
                            for (int i = 0; i < listViewItemList.size(); i++) {
                                //응답받기
                                Response.Listener<String> responseListener = response -> {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if (success) {

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("DB Error : ", "에러에러");
                                    }
                                };

                                ParticipantInfoUpdateRequest participantInfoUpdateRequest = new ParticipantInfoUpdateRequest(listViewItemList.get(i).getUserID(), listViewItemList.get(i).getAssignedAmount()+"" , listViewItemList.get(i).isPrePaymentCheck(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(DutchGroupControlActivity.this);
                                queue.add(participantInfoUpdateRequest);
                                Log.d(listViewItemList.get(i).getUserID() + "의 AssignedAmount : " , listViewItemList.get(i).getAssignedAmount()+"");
                            }

                            for(int i  = 0; i < userPushIDList.size(); i++ ) {
                                try {
                                    OneSignal.postNotification(new JSONObject("{'contents': {'en':'금액이 확정되었습니다. \n 결제를 진행해주세요.'}, 'include_player_ids': ['" + userPushIDList.get(i) + "']}"), null);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            Intent intent = new Intent(DutchGroupControlActivity.this, ConfirmedDutchPayActivity.class);
                            startActivity(intent);
                            _QRCodeCreateActivity.finish();
                            finish();

                        })
                        .setNegativeButton("취소", (dialog1, which) -> {

                        })
                        .setCancelable(false)
                        .create();
                dialog.show();
            }
        });

        btn_before_host = (Button) findViewById(R.id.btn_before_host);
        btn_before_host.setOnClickListener(v -> finish());
    }

    private class BackGroundTask_ParticipantUserList extends AsyncTask<Void, Void, String> {
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

                    listViewItemList.add(new ListViewItem_DutchStart(count, hostID, userID, Amount, assignedAmount , prePaymentCheck));
                    count++;
                }

                //금액이 정확히 나누어 떨어지지 않으면
                if(Amount % count != 0){
                    for( int i = 0; i < listViewItemList.size(); i++){
                        //나머지금액은 host에게 부과함.
                        if(listViewItemList.get(i).getHostID().equals(listViewItemList.get(i).getUserID())){
                            listViewItemList.get(i).setDistributedAmount((Amount / count) + Amount % count);
                            listViewItemList.get(i).setAssignedAmount((Amount / count) + Amount % count);
                        } else {
                            listViewItemList.get(i).setDistributedAmount((Amount / count));
                            listViewItemList.get(i).setAssignedAmount((Amount / count));
                        }
                    }
                } else {
                    for( int i = 0; i < listViewItemList.size(); i++){
                        listViewItemList.get(i).setDistributedAmount((Amount / count));
                        listViewItemList.get(i).setAssignedAmount((Amount / count));
                    }
                }

                toolbar.setTitle("모집된 멤버 ( " + count + "명 )");
                tv_totalCost.setText("총 금액 " + String.format("%,d", Amount)   + "원");
                adapter = new DutchStartListViewAdapter(getApplicationContext(), listViewItemList, count , Amount);
                list.setAdapter(adapter);

                new BackGroundTask_UserPushID().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class BackGroundTask_UserPushID extends AsyncTask<Void, Void, String> {

        private String target;

        private String userPushID;

        @Override
        protected void onPreExecute() {
            try {
                userPushIDList.clear();
                target = "http://kjg123kg.cafe24.com/DutchPay_UserPushIDRequest.php?userID=" + URLEncoder.encode(userInfo.getUserID(), "UTF-8");
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
                    userPushID = object.getString("userPushID");
                    userPushIDList.add(userPushID);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
