package com.example.mp_3.dutchpayapp.Activity.SendMoneyActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Class.RequestClass.RemittanceHistory_DBAddRequest;
import com.example.mp_3.dutchpayapp.Class.RequestClass.UserDutchMoneyChangeRequest;
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

public class SendMoneyPasswordCheckActivity extends AppCompatActivity {

    private TextView txt_title;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private ImageButton back;
    private Button clear;

    private ImageView radio_pw[] = new ImageView[6];
    private Vibrator vibrator;

    private String tmp = "";
    private String amount;
    private String phoneNumber;

    int vibratorTime = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_send_money_password_check);

        txt_title = (TextView) findViewById(R.id.txt_title);
        amount = getIntent().getExtras().getString("amount");
        phoneNumber = getIntent().getExtras().getString("phoneNumber");

        txt_title.setText("비밀번호가 일치하면\n" + String.format("%,d", Integer.parseInt(amount)) + "원을 송금합니다.");


        int radioID[] = {R.id.radio_pw1, R.id.radio_pw2, R.id.radio_pw3, R.id.radio_pw4, R.id.radio_pw5, R.id.radio_pw6};

        for (int i = 0; i < radio_pw.length; i++) {
            radio_pw[i] = (ImageView) findViewById(radioID[i]);
        }

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn0 = (Button) findViewById(R.id.btn_0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "0";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn1 = (Button) findViewById(R.id.btn_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "1";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn2 = (Button) findViewById(R.id.btn_2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "2";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn3 = (Button) findViewById(R.id.btn_3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "3";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn4 = (Button) findViewById(R.id.btn_4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "4";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn5 = (Button) findViewById(R.id.btn_5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "5";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn6 = (Button) findViewById(R.id.btn_6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "6";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn7 = (Button) findViewById(R.id.btn_7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "7";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn8 = (Button) findViewById(R.id.btn_8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "8";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
        btn9 = (Button) findViewById(R.id.btn_9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp += "9";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });

        back = (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tmp.equals("")) {
                    int cut_l = tmp.length() - 1;

                    if (cut_l < 0) {
                        cut_l = 0;
                    }
                    tmp = tmp.substring(0, cut_l);
                    ImageViewChange(tmp);
                    vibrator.vibrate(vibratorTime);
                }
            }
        });

        clear = (Button) findViewById(R.id.btn_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = "";
                ImageViewChange(tmp);
                vibrator.vibrate(vibratorTime);
            }
        });
    }

    public void ImageViewChange(String password) {
        Log.d("password : ", password);
        int length = password.length();

        if (password.length() < radio_pw.length) {
            for (int i = 0; i < length; i++) {
                radio_pw[i].setImageResource(R.drawable.password_entered);
            }
            for (int i = length; i < radio_pw.length; i++) {
                radio_pw[i].setImageResource(R.drawable.passworkd_notenterd);
            }
        } else {
            radio_pw[radio_pw.length - 1].setImageResource(R.drawable.password_entered);
            new BackGroundTask().execute();
            //해당 연락처가 DB에 있다면
            //DB에서 연락처를 조회하고 target의 userID , userMoney , pushID를 빼옴.
            //userID를 조회하여 target의 userMoney를 + 시킴
            //성공했다면 pushID로 푸시발송
            //해당 연락처가 DB에 없다면
            //finish()
        }
    }

    private class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        String userID;
        String userName;
        String userPhoneNumber;
        String userPushID;
        int userDutchMoney;

        UserInfo userInfo;

        @Override
        protected void onPreExecute() {
            try {
                Log.d("phoneNumber", phoneNumber);
                target = "http://kjg123kg.cafe24.com/DutchPay_SendMoney_ReceiverInfo.php?userPhoneNumber=" + URLEncoder.encode(phoneNumber, "UTF-8");

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
                    userID = object.getString("userID");
                    userName = object.getString("userName");
                    userPhoneNumber = object.getString("userPhoneNumber");
                    userDutchMoney = object.getInt("userDutchMoney");
                    userPushID = object.getString("userPushID");
                    count++;
                }
                //연락처가 없을경우
                if (count == 0) {
                    try {
                        OneSignal.postNotification(new JSONObject("{'contents': {'en':'" + phoneNumber + "번호로 " + String.format("%,d", Integer.parseInt(amount)) + "원을 송금하셨습니다.'}, 'include_player_ids': ['" + userInfo.getUserPushID() + "']}"), null);

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber.replace("-", ""), null, userInfo.getUserName() + "님이 당신에게\n" + String.format("%,d", Integer.parseInt(amount)) + "원을 송금하셨습니다. \n\n아래 링크를 눌러 더치페이 앱에서 바로 확인하세요.", null, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    RemittanceHistory_DBAddRequest(amount+"", userInfo.getUserID(), phoneNumber );

                } else {
                    //송금자
                    SendMoneyRequest(userInfo.getUserID() , userInfo.getUserDutchMoney() - Integer.parseInt(amount));
                    //수금자
                    SendMoneyRequest(userName , userDutchMoney + Integer.parseInt(amount));
                    //푸쉬발송
                    try {
                        OneSignal.postNotification(new JSONObject("{'contents': {'en':'" + userName + "님에게 " + String.format("%,d", Integer.parseInt(amount)) + "원을 송금하셨습니다.'}, 'include_player_ids': ['" + userInfo.getUserPushID() + "']}"), null);
                        OneSignal.postNotification(new JSONObject("{'contents': {'en':'" + userInfo.getUserName() + "님이 " + userName + "님에게 " + String.format("%,d", Integer.parseInt(amount)) + "원을 송금하셨습니다.'}, 'include_player_ids': ['" + userPushID + "']}"), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RemittanceHistory_DBAddRequest(amount+"", userInfo.getUserID(), userID );
                }
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("오류", "송금불가");
                finish();
            }
        }

        public void SendMoneyRequest(String userID, int userDutchMoney) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {


                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DB Error : ", "에러에러");
                    }
                }
            };
            UserDutchMoneyChangeRequest userDutchMoneyChangeRequest = new UserDutchMoneyChangeRequest(userID, userDutchMoney + "", responseListener);
            RequestQueue queue = Volley.newRequestQueue(SendMoneyPasswordCheckActivity.this);
            queue.add(userDutchMoneyChangeRequest);
        }

        private void RemittanceHistory_DBAddRequest(String remittanceAmount , String remittererID , String receiverID ) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {

                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DB Error : ", "에러에러");
                    }
                }
            };

            RemittanceHistory_DBAddRequest  remittanceHistory_dbAddRequest = new RemittanceHistory_DBAddRequest(remittanceAmount, remittererID, receiverID , responseListener);
            RequestQueue queue = Volley.newRequestQueue(SendMoneyPasswordCheckActivity.this);
            queue.add(remittanceHistory_dbAddRequest);
        }
    }

}
