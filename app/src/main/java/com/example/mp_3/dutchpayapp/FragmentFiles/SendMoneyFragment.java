package com.example.mp_3.dutchpayapp.FragmentFiles;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;
import com.example.mp_3.dutchpayapp.databinding.FragmentSendMoneyBinding;

public class SendMoneyFragment extends Fragment {

    FragmentSendMoneyBinding mBinding;

    private String money = "";

    private Vibrator vibrator;
    int vibratorTime = 50;

    public DataListener dataListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DataListener) {
            dataListener = (DataListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_money, container, false);

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        mBinding.btn0.setOnClickListener(v -> {
            if (money.length() > 0) {
                money = money + "0";
                mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            }
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });

        mBinding.btn1.setOnClickListener(v -> {
            money = money + "1";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });


        mBinding.btn2.setOnClickListener(v -> {
            money = money + "2";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });


        mBinding.btn3.setOnClickListener(v -> {
            money = money + "3";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });

        mBinding.btn4.setOnClickListener(v -> {
            money = money + "4";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });

        mBinding.btn5.setOnClickListener(v -> {
            money = money + "5";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });


        mBinding.btn6.setOnClickListener(v -> {
            money = money + "6";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });

        mBinding.btn7.setOnClickListener(v -> {
            money = money + "7";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });

        mBinding.btn8.setOnClickListener(v -> {
            money = money + "8";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });

        mBinding.btn9.setOnClickListener(v -> {
            money = money + "9";
            mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            vibrator.vibrate(vibratorTime);
            textViewWatcher();
        });

        mBinding.btnBack.setOnClickListener(v -> {
            vibrator.vibrate(vibratorTime);
            int cut_l = money.length() - 1;
            if (cut_l < 0) {
                cut_l = 0;
            }

            money = money.substring(0, cut_l);
            if (money.equals("")) {
                mBinding.tvMoney.setText("0원");
            } else {
                mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            }
            textViewWatcher();
        });

        mBinding.btnClear.setOnClickListener(v -> {
            money = "";
            mBinding.tvMoney.setText(money + "0원");
            textViewWatcher();
        });

        mBinding.btnSendMoney.setOnClickListener(v -> {
            dataListener.sendMoneyListenerSet(money);
        });

        return mBinding.getRoot();
    }


    public void textViewWatcher() {
        if (money.length() >= 7) {
            if (Integer.parseInt(money) > 2000000) {
               mBinding.txtMaxAmountText.setVisibility(View.VISIBLE);
               Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
               mBinding.tvMoney.startAnimation(shake);
               mBinding.txtMaxAmountText.startAnimation(shake);
               vibrator.vibrate(300);
               mBinding.tvMoney.setText(String.format("%,d", 2000000) + "원");
               money = "2000000";
            } else {
               mBinding.txtMaxAmountText.setVisibility(View.GONE);
            }
        }
    }
}
