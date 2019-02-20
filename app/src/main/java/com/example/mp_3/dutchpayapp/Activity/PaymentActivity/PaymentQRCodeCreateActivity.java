package com.example.mp_3.dutchpayapp.Activity.PaymentActivity;

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

import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;
import com.google.zxing.WriterException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_qrcode_create);

        qrImage = (ImageView) findViewById(R.id.QR_Image_payment);
        userInfo = UserInfo.getInstance();
        qrKey = userInfo.getUserID();

        //--이전 액티비티에서 넘긴 결제 금액 받기
        amount = getIntent().getExtras().getString("data");

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity종료시 취소버튼없이 종료했고 , user가 결제상태가 진행중이라면 앱을 다시 시작했을떄 user에게 QR코드를 보여줄 필요가 있음.
        if(userInfo.getUserState() == 1){
            //따라서 현재 QR코드의 Data를 userInfo에 저장
            userInfo.setUserQRCode(qrKey+ "," + amount);
        }
    }
}