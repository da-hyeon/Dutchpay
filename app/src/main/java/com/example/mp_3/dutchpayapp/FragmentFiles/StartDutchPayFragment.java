package com.example.mp_3.dutchpayapp.FragmentFiles;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRCreate_DBAddRequest;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;
import com.example.mp_3.dutchpayapp.databinding.FragmentStartDutchPayBinding;

import org.json.JSONObject;

public class StartDutchPayFragment extends Fragment {

    FragmentStartDutchPayBinding mBinding;
    
    private UserInfo userInfo;
    
    String money = "";

    private Vibrator vibrator;
    int vibratorTime = 50;
    private DataListener dataListener;

    public static StartDutchPayFragment newInstance() {
        Bundle args = new Bundle();

        StartDutchPayFragment fragment = new StartDutchPayFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
    public void onDetach() {
        super.onDetach();

        dataListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_start_dutch_pay, container, false);
        
        userInfo = UserInfo.getInstance();

        if (savedInstanceState != null) {
            money = savedInstanceState.getString("tvMoney");
            if(money.length() > 0) {
                mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            } else {
                mBinding.tvMoney.setText("0원");
            }
        }

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        mBinding.btn0.setOnClickListener(v -> {
            if(money.length() > 0 ) {
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
            textViewWatcher();
            vibrator.vibrate(vibratorTime);
            int cut_l = money.length()-1;
            if(cut_l < 0){
                cut_l = 0;
            }

            money = money.substring(0, cut_l);
            if(money.equals("")){
                mBinding.tvMoney.setText("0원");
            } else {
                mBinding.tvMoney.setText(String.format("%,d", Integer.parseInt(money)) + "원");
            }

        });

        mBinding.btnClear.setOnClickListener(v -> {
            textViewWatcher();
            vibrator.vibrate(vibratorTime);
            money = "";
            mBinding.tvMoney.setText(money+"0원");
        });

        mBinding.btnCode.setOnClickListener(v -> {
            if (money.length() > 1) {
                //응답받기
                Response.Listener<String> responseListener = response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            //mmoney값 전달
                            dataListener.dataListenerSet(money);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DB Error : ", "에러에러");
                    }
                };
                QRCreate_DBAddRequest qrCreateDBAddRequest = new QRCreate_DBAddRequest(userInfo.getUserID(), money, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(qrCreateDBAddRequest);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                AlertDialog dialog = builder.setMessage("최소 10원 이상이어야 합니다.")
                        .setPositiveButton("확인", (dialog1, which) -> {

                        }).setCancelable(false)
                        .create();
                dialog.show();
            }
        });

        mBinding.btnGroup.setOnClickListener(v -> {
            //mmoney값 전달
            //dataListener.dataListenerSet(money);
        });


        return mBinding.getRoot();
    }

    //ViewPager는 보통 3~4개 정도의 프래그먼트를 메모리에 유지하고 나머지는 해제한다.
    //이때 프래그먼트가 갑자기 메모리에서 해제되어도 표시되는 내용이 보존되도록
    //onSavedInstanceState() 메서드를 재정의 한다.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //복원 데이터 저장
        outState.putString("tvMoney",  money);
    }

    public void textViewWatcher() {
        if(money.length() >= 7){
            if(Integer.parseInt(money) > 2000000){
                mBinding.txtMaxAmountText.setVisibility(View.VISIBLE);
                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                mBinding.tvMoney.startAnimation(shake);
                mBinding.txtMaxAmountText.startAnimation(shake);
                vibrator.vibrate(300);
                mBinding.tvMoney.setText(String.format("%,d", 2000000)+"원");
                money = "2000000";
            }
            else {
                mBinding.txtMaxAmountText.setVisibility(View.GONE);
            }
        }
    }
}