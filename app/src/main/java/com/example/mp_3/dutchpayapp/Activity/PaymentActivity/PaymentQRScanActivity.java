package com.example.mp_3.dutchpayapp.Activity.PaymentActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.MainActivity;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class PaymentQRScanActivity extends AppCompatActivity {

    private SurfaceView cameraPreview;
    private TextView txtResult;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private final int RequestCameraPermissionID = 1001;

    private UserInfo userInfo;

    private LinearLayout layout_QRCodeStart;
    private LinearLayout layout_QRCodeCamera;

    private ImageButton btn_QRCodeStart;

    //커스텀 필요 -> DB 수정 필요

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_join_dutch_pay);

        userInfo = UserInfo.getInstance();

        layout_QRCodeStart = (LinearLayout) findViewById(R.id.layout_QRCodeStart);
        layout_QRCodeCamera = (LinearLayout) findViewById(R.id.layout_QRCodeCamera);

        btn_QRCodeStart = (ImageButton) findViewById(R.id.btn_QRCodeStart) ;

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        btn_QRCodeStart = (ImageButton)findViewById(R.id.btn_QRCodeStart);
        btn_QRCodeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_QRCodeStart.setVisibility(View.GONE);
                layout_QRCodeCamera.setVisibility(View.VISIBLE);


                //Add Event
                cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            //Request permission
                            ActivityCompat.requestPermissions(new PaymentQRScanActivity(), new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                            return;
                        }
                        try {
                            cameraSource.start(cameraPreview.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                        cameraSource.stop();
                    }
                });

                barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<Barcode> detections) {
                        final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                        if (qrcodes.size() != 0) {
                            txtResult.post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    }
                });
            }
        });
        MainActivity.customProgressDialog.dismiss();
    }

}