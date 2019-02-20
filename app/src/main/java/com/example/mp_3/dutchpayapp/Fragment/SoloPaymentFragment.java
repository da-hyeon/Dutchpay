package com.example.mp_3.dutchpayapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.QRCodeCreateActivity;
import com.example.mp_3.dutchpayapp.Class.DialogClass.PaymentNumber_Dialog;
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRCancel_DBDeleteRequest;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SoloPaymentFragment extends Fragment {

    DataListener dataListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof DataListener) {
            dataListener = (DataListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_solo_payment,null);

        //코드로 결제하기
        Button code = (Button) view.findViewById(R.id.btn_payment_code);
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //스캔 액티비티로 이동
                dataListener.PayLisstenerSet("");

            }
        });

        //결제번호로 결제하기
        Button number = (Button) view.findViewById(R.id.btn_payment_number);
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //결제번호 입력창
                PaymentNumber_Dialog dialog = new PaymentNumber_Dialog(getContext());
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }
        });


        return view;
    }
}