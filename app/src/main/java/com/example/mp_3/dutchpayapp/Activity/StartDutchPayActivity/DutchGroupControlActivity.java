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
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRCancel_DBDeleteRequest;
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

public class DutchGroupControlActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private ArrayList<ListViewItem_DutchStart> listViewItemList;
    private DutchStartListViewAdapter adapter;
    private ListView list;
    private TextView tv_totalCost;

    UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch_group_control);

        toolbar = findViewById(R.id.tv_memberCount);
        userInfo = UserInfo.getInstance();

        listViewItemList = new ArrayList<>();
        list = (ListView) findViewById(R.id.lv_dutch_host);
        tv_totalCost = (TextView) findViewById(R.id.tv_totalCost);

        new BackGroundTask().execute();

        Button next = (Button) findViewById(R.id.btn_next_host);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DutchGroupControlActivity.this);
                AlertDialog dialog = builder.setMessage("금액을 확정지으시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < listViewItemList.size(); i++) {
                                    //응답받기
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
                                    ParticipantInfoUpdateRequest participantInfoUpdateRequest = new ParticipantInfoUpdateRequest(listViewItemList.get(i).getUserID(), listViewItemList.get(i).getDirectInputAmount() + "", listViewItemList.get(i).isPrePaymentCheck(), responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(DutchGroupControlActivity.this);
                                    queue.add(participantInfoUpdateRequest);

                                }
                                Intent intent = new Intent(DutchGroupControlActivity.this, ConfirmedDutchPayActivity.class);
                                startActivity(intent);
                            }
                        }).setCancelable(false)
                        .create();
                dialog.show();
            }
        });
    }

    private class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        String hostID;
        String userID;
        int Amount;
        int directInputAmount;


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
                    directInputAmount = object.getInt("directInputAmount");

                    listViewItemList.add(new ListViewItem_DutchStart(count, hostID, userID, Amount, directInputAmount));
                    count++;
                }

                toolbar.setTitle("모집된 멤버 ( " + count + "명 )");
                tv_totalCost.setText("총 금액 " + Amount + "원");
                adapter = new DutchStartListViewAdapter(getApplicationContext(), listViewItemList, count);
                list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
