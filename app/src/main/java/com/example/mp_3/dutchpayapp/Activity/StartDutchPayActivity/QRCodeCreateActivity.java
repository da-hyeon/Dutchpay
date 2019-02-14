package com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRCancel_DBDeleteRequest;
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRScan_DBAddRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Fragment.StartDutchPayFragment;
import com.example.mp_3.dutchpayapp.R;
import com.google.zxing.WriterException;

import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeCreateActivity extends AppCompatActivity {

    private ImageView qrImage;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private UserInfo userInfo;

    private String qrKey;
    private String TAG = "GenerateQRCode";
    private String amount;

    private Button QR_Cancel;
    private Button QR_Finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_create);

        qrImage = (ImageView) findViewById(R.id.QR_Image);
        userInfo = UserInfo.getInstance();
        qrKey = userInfo.getUserID();

        //startDutchPayFragment에서 전달한 Amount의 data 받기
        amount = getIntent().getExtras().getString("data");

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        //QR코드 생성 data는 [userID + host가 설정한 금액]
        qrgEncoder = new QRGEncoder(
                qrKey+ "," + amount, null,
                QRGContents.Type.TEXT,
                smallerDimension);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v(TAG, e.toString());
        }

        //취소하기
        QR_Cancel = (Button)findViewById(R.id.QR_Cancel);
        QR_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeCreateActivity.this);
                AlertDialog dialog = builder.setMessage("정말 취소하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //응답받기
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                userInfo.setUserState(false);
                                                finish();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.d("DB Error : ", "에러에러");
                                        }
                                    }
                                };
                                QRCancel_DBDeleteRequest qrCancel_dbDeleteRequest = new QRCancel_DBDeleteRequest(userInfo.getUserID(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(QRCodeCreateActivity.this);
                                queue.add(qrCancel_dbDeleteRequest);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .create();
                dialog.show();
            }
        });

        //마감하기
        QR_Finish = (Button)findViewById(R.id.QR_Finish);
        QR_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo.setUserState(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity종료시 취소버튼없이 종료했고 , user가 결제상태가 진행중이라면 앱을 다시 시작했을떄 user에게 QR코드를 보여줄 필요가 있음.
        if(userInfo.isUserState()){
            //따라서 현재 QR코드의 Data를 userInfo에 저장
            userInfo.setUserQRCode(qrKey+ "," + amount);
        }
    }
}

