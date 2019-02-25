package com.example.mp_3.dutchpayapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Activity.LoginActivity;
import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;

public class ViewMoreFragment extends Fragment {

    UserInfo userInfo;

    Button btn_paymentHistory;
    private Button btn_Logout;

    private DataListener dataListener;
    private SharedPreferences pref_AutoLogin;
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
        View view = inflater.inflate(R.layout.fragment_view_more,null);

        userInfo = UserInfo.getInstance();

        TextView id = (TextView)view.findViewById(R.id.tv_id);
        TextView password = (TextView)view.findViewById(R.id.tv_password);
        TextView email = (TextView)view.findViewById(R.id.tv_email);
        TextView tv_phoneNumber = (TextView) view.findViewById(R.id.tv_phoneNumber);
        TextView name = (TextView)view.findViewById(R.id.tv_name);
        TextView money = (TextView)view.findViewById(R.id.tv_money);
        btn_paymentHistory = (Button) view.findViewById(R.id.btn_paymentHistory);
        btn_paymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataListener.historyListenerSet();
            }
        });
        btn_Logout = (Button) view.findViewById(R.id.btn_logout);
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataListener.logoutListenerSet();
            }
        });

        //text setting
        id.setText("당신의 아이디는 "+ userInfo.getUserID() +" 입니다.");
        password.setText("당신의 비밀번호는 "+ userInfo.getUserPassword() +" 입니다.");
        email.setText("당신의 이메일은 "+ userInfo.getUserName() +" 입니다.");
        name.setText("당신의 이름은 "+ userInfo.getUserEmail() +" 입니다.");
        tv_phoneNumber.setText("당신의 핸드폰번호는 "+ userInfo.getUserPhoneNumber()+" 입니다.");
        money.setText("당신이 보유한 더치머니는 "+ String.format("%,d", userInfo.getUserDutchMoney()) +" 원 입니다.");

        return view;
    }
}