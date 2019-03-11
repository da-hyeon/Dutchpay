package com.example.mp_3.dutchpayapp.FragmentFiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mp_3.dutchpayapp.Class.DialogClass.PaymentNumber_Dialog;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;

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
        ImageView code = (ImageView) view.findViewById(R.id.btn_payment_code);
        code.setOnClickListener(v -> {

            //스캔 액티비티로 이동
            dataListener.PayLisstenerSet("");

        });

        //결제번호로 결제하기
        ImageView number = (ImageView) view.findViewById(R.id.btn_payment_number);
        number.setOnClickListener(v -> {

            //결제번호 입력창
            PaymentNumber_Dialog dialog = new PaymentNumber_Dialog(getContext());
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        });
        return view;
    }
}