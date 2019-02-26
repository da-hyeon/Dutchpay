package com.example.mp_3.dutchpayapp.Fragment;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRScan_DBAddRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONObject;

import java.io.IOException;

public class JoinDutchPayFragment extends Fragment {

    private SurfaceView cameraPreview;
    private TextView txtResult;
    private TextView txt_cameraInfo;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private final int RequestCameraPermissionID = 1001;

    private UserInfo userInfo;

    private LinearLayout layout_QRCodeStart;
    private LinearLayout layout_QRCodeCamera;

    private ImageButton btn_QRCodeStart;

    private  SharedPreferences pref;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_dutch_pay, null);
        pref = this.getActivity().getSharedPreferences("hostID",Context.MODE_PRIVATE);
        userInfo = UserInfo.getInstance();

        layout_QRCodeStart = (LinearLayout) view.findViewById(R.id.layout_QRCodeStart);
        layout_QRCodeCamera = (LinearLayout) view.findViewById(R.id.layout_QRCodeCamera);



        cameraPreview = (SurfaceView) view.findViewById(R.id.cameraPreview);
        txtResult = (TextView) view.findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        txt_cameraInfo = (TextView) view.findViewById(R.id.txt_cameraInfo);
        Animation startAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink_animation);
        txt_cameraInfo.startAnimation(startAnimation);


        btn_QRCodeStart = (ImageButton) view.findViewById(R.id.btn_QRCodeStart) ;
        btn_QRCodeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_QRCodeStart.setVisibility(View.GONE);
                layout_QRCodeCamera.setVisibility(View.VISIBLE);


                //Add Event
                cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            //Request permission
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
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
                                    cameraSource.stop();
                                    //Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                                    //vibrator.vibrate(1000);

                                    //응답받기
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                if (success) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                    AlertDialog dialog = builder.setMessage("스캔에 성공했습니다.")
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    layout_QRCodeStart.setVisibility(View.VISIBLE);
                                                                    layout_QRCodeCamera.setVisibility(View.GONE);
                                                                }
                                                            }).setCancelable(false)
                                                            .create();
                                                    dialog.show();
                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                    AlertDialog dialog = builder.setMessage("이미 스캔하셨습니다.")
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                }
                                                            }).setCancelable(false)
                                                            .create();
                                                    dialog.show();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.d("DB Error : ", "에러에러");
                                            }
                                        }
                                    };
                                    String QRCodeInfoSeperate[] = qrcodes.valueAt(0).displayValue.split(",");

                                    String hostID;
                                    String amount;

                                    if(QRCodeInfoSeperate.length == 2) {
                                         hostID = QRCodeInfoSeperate[0];
                                         amount = QRCodeInfoSeperate[1];
                                        QRScan_DBAddRequest qrScanDbAddRequest = new QRScan_DBAddRequest(hostID , userInfo.getUserID(), amount ,responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(getContext());
                                        queue.add(qrScanDbAddRequest);
                                        txtResult.setText(qrcodes.valueAt(0).displayValue);

                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("hostID", hostID);
                                        editor.commit();
                                    } else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        AlertDialog dialog = builder.setMessage("QR코드 정보가 잘못되었습니다.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                                            //Request permission
                                                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                                                            return;
                                                        }
                                                        try {
                                                            cameraSource.start(cameraPreview.getHolder());
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).setCancelable(false)
                                                .create();
                                        dialog.show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        return view;
    }
}
