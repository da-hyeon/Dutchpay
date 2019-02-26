package com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.example.mp_3.dutchpayapp.Activity.PaymentActivity.PaymentPasswordCheckActivity;
import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.ConfirmationPaymentListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.FireBaseClass.FirebasePost;
import com.example.mp_3.dutchpayapp.Class.Handler.BackPressCloseHandler;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_ConfirmationPayment;
import com.example.mp_3.dutchpayapp.Class.RequestClass.PaymentCompleted_DBDeleteRequest;
import com.example.mp_3.dutchpayapp.Class.RequestClass.UserStateChangeRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ConfirmationPaymentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_participantsCount;
    private TextView txt_autoInfoText;

    private ArrayList<ListViewItem_ConfirmationPayment> listViewItemList;

    private BackPressCloseHandler backPressCloseHandler;

    private SharedPreferences pref;
    private String targetHostID;

    private DatabaseReference mPostReference;
    private UserInfo userInfo;
    private ListView list;

    private int currentTotalParticipantCount;

    private String totalAmount;
    private String totalParticipantCount;

    private ConfirmationPaymentListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_payment);

        //뒤로가기
        backPressCloseHandler = new BackPressCloseHandler(this);

        pref = getSharedPreferences("hostID", MODE_PRIVATE);
        targetHostID = pref.getString("hostID", "");
        currentTotalParticipantCount = 0;


        userInfo = UserInfo.getInstance();
        mPostReference = FirebaseDatabase.getInstance().getReference("userInfo");

        toolbar = (Toolbar) findViewById(R.id.tv_memberCount);

        listViewItemList = new ArrayList<>();

        list = (ListView) findViewById(R.id.lv_participants);
        tv_participantsCount = (TextView) findViewById(R.id.tv_participantsCount);

        txt_autoInfoText = (TextView) findViewById(R.id.txt_autoInfoText);
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
        txt_autoInfoText.startAnimation(startAnimation);

        new BackGroundTask().execute();
    }

    private void DBDeleteRequest(){
        //응답받기
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        userInfo.setUserState(0);

                    } else{
                        userInfo.setUserState(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("DB Error : ", "에러에러");
                }
            }
        };
        PaymentCompleted_DBDeleteRequest paymentCompleted_dbDeleteRequest = new PaymentCompleted_DBDeleteRequest(userInfo.getUserID(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ConfirmationPaymentActivity.this);
        queue.add(paymentCompleted_dbDeleteRequest);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
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
                if (targetHostID.equals(""))
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

                    listViewItemList.add(new ListViewItem_ConfirmationPayment(userID, assignedAmount));
                    count++;
                }

                toolbar.setTitle("모집된 멤버 ( " + count + "명 )");
                tv_participantsCount.setText("결제 인원 : " + currentTotalParticipantCount + " / " + count);
                totalAmount = Amount + "";
                totalParticipantCount = count + "";
                adapter = new ConfirmationPaymentListViewAdapter(listViewItemList);
                list.setAdapter(adapter);

                //호스트일때
                if (targetHostID.equals("")) {
                    mPostReference.child(userInfo.getUserID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            currentTotalParticipantCount = (int) dataSnapshot.getChildrenCount();

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                                for (int i = 0; i < listViewItemList.size(); i++) {
                                    if (listViewItemList.get(i).getUserID().equals(get.userID)) {
                                        listViewItemList.get(i).setPrePaymentCheck(true);
                                        if (!totalParticipantCount.equals("")) {
                                            tv_participantsCount.setText("결제 인원 : " + currentTotalParticipantCount + " / " + totalParticipantCount);
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            //모두 결제 완료시
                            if (totalParticipantCount != null && !ConfirmationPaymentActivity.this.isFinishing()) {
                                if (!totalParticipantCount.equals("")) {
                                    if (currentTotalParticipantCount == Integer.parseInt(totalParticipantCount)) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmationPaymentActivity.this);
                                        AlertDialog dialog = builder.setTitle("결제 완료")
                                                .setMessage("참여인원이 전부 결제하였습니다. 결제 QR코드를 출력합니다.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        DBDeleteRequest();
                                                        mPostReference.child(userInfo.getUserID()).removeValue();

                                                        //응답받기
                                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    JSONObject jsonResponse = new JSONObject(response);
                                                                    boolean success = jsonResponse.getBoolean("success");

                                                                    if (success) {
                                                                        //PaymentQRCodeCreateActivity으로 이동
                                                                        Intent intent = new Intent(ConfirmationPaymentActivity.this, PaymentQRCodeCreateActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    } else{

                                                                    }
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                    Log.d("DB Error : ", "에러에러");
                                                                }
                                                            }
                                                        };
                                                        UserStateChangeRequest userStateChangeRequest = new UserStateChangeRequest(userInfo.getUserID(), userInfo.getUserDutchMoney()+"", "4" , responseListener);
                                                        RequestQueue queue = Volley.newRequestQueue(ConfirmationPaymentActivity.this);
                                                        queue.add(userStateChangeRequest);
                                                    }
                                                }).setCancelable(false)
                                                .create();
                                        dialog.show();


                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    mPostReference.child(targetHostID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            currentTotalParticipantCount = (int) dataSnapshot.getChildrenCount();

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                                for (int i = 0; i < listViewItemList.size(); i++) {
                                    if (listViewItemList.get(i).getUserID().equals(get.userID)) {
                                        listViewItemList.get(i).setPrePaymentCheck(true);
                                        if (!totalParticipantCount.equals("")) {
                                            tv_participantsCount.setText("결제 인원 : " + currentTotalParticipantCount + " / " + totalParticipantCount);
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }


                            if (totalParticipantCount != null && !ConfirmationPaymentActivity.this.isFinishing()) {
                                if (!totalParticipantCount.equals("")) {
                                    if (currentTotalParticipantCount == Integer.parseInt(totalParticipantCount)) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmationPaymentActivity.this);
                                        AlertDialog dialog = builder.setTitle("결제 완료")
                                                .setMessage("참여인원이 전부 결제하였습니다. 결제 QR코드를 출력합니다.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        DBDeleteRequest();

                                                        //postFirebaseDatabase();

                                                        //메인으로 이동
                                                        Intent intent = new Intent(ConfirmationPaymentActivity.this, MainActivity.class);
                                                        intent.putExtra("userID", userInfo.getUserID());
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }).setCancelable(false)
                                                .create();
                                        dialog.show();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
