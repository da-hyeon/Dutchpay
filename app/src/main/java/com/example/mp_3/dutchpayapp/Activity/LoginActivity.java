package com.example.mp_3.dutchpayapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Class.Handler.BackPressCloseHandler;
import com.example.mp_3.dutchpayapp.Class.RequestClass.LoginRequest;
import com.example.mp_3.dutchpayapp.MainActivity;
import com.example.mp_3.dutchpayapp.R;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private BackPressCloseHandler backPressCloseHandler;

    private SharedPreferences pref;

    private String sharedUserID;
    private String sharedUserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //앱 내에 저장된 데이터 Load
        pref = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        sharedUserID = pref.getString("sharedUserID", "");
        sharedUserPassword = pref.getString("sharedUserPassword", "");

        if(!sharedUserID.equals("") && !sharedUserPassword.equals("")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            intent.putExtra("userID", sharedUserID);
            startActivity(intent);
            finish();
        }

        backPressCloseHandler = new BackPressCloseHandler(this);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setTitle("로그인");
        setSupportActionBar(toolbar);

        final EditText et_id =  findViewById(R.id.et_id);
        final EditText et_password = findViewById(R.id.et_password);





        //로그인버튼
        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(v -> {

            final String userID = et_id.getText().toString();
            final String userPassword = et_password.getText().toString();

            if(stringCheck(et_id , et_password)) {
                Response.Listener<String> responseLister = response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("로그인에 성공했습니다.")
                                    .setPositiveButton("확인", (dialog, which) -> {
                                        //login service
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("sharedUserID", userID);
                                        editor.putString("sharedUserPassword", userPassword);
                                        editor.commit();

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                        intent.putExtra("userID", userID);
                                        startActivity(intent);
                                        finish();
                                    }).setCancelable(false)
                                    .create();
                            dialog.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("계정을 다시 확인하세요.")
                                    .setNegativeButton("다시 시도", null)
                                    .create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLister);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    public boolean stringCheck(EditText id, EditText pw) {

        if (id.getText().toString().replace(" ", "").equals("")) {
            Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();

            return false;
        } else if (pw.getText().toString().replace(" ", "").equals("")) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
