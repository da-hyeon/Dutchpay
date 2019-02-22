package com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.example.mp_3.dutchpayapp.Class.Handler.BackPressCloseHandler;
import com.example.mp_3.dutchpayapp.Class.RequestClass.UserStateChangeRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;
import com.google.zxing.WriterException;

import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PaymentQRCodeCreateActivity extends AppCompatActivity {

    private ImageView qrImage;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private UserInfo userInfo;

    private String qrKey;
    private String TAG = "Pay_GenerateQRCode";
    private String amount;

    private Button ToBarCorde;
    private Button btn_successPayment;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_qrcode_create);

        qrImage = (ImageView) findViewById(R.id.QR_Image_payment);
        userInfo = UserInfo.getInstance();
        qrKey = userInfo.getUserID();

        backPressCloseHandler = new BackPressCloseHandler(this);

        //--이전 액티비티에서 넘긴 결제 금액 받기
        //amount = getIntent().getExtras().getString("data");

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        //--QR코드 생성 data는 [userID + 이전 액티비티가 넘긴 결제 금액 ]
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

        //바코드로 이동
        ToBarCorde = (Button)findViewById(R.id.btn_barcode);
        ToBarCorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //바코드 결제 페이지로 이동
//                Intent intent = new Intent(this,@@.class);
//                startActivity(intent);

            }
        });

        btn_successPayment = (Button)findViewById(R.id.btn_successPayment);
        btn_successPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //응답받기
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(PaymentQRCodeCreateActivity.this, MainActivity.class);
                                intent.putExtra("userID", userInfo.getUserID());
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
                UserStateChangeRequest userStateChangeRequest = new UserStateChangeRequest(userInfo.getUserID(), userInfo.getUserDutchMoney()+"" ,"0" , responseListener);
                RequestQueue queue = Volley.newRequestQueue(PaymentQRCodeCreateActivity.this);
                queue.add(userStateChangeRequest);



            }
        });
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}