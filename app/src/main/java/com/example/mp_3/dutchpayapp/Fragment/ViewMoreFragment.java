package com.example.mp_3.dutchpayapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.R;

public class ViewMoreFragment extends Fragment {

    UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_more,null);

        userInfo = UserInfo.getInstance();

        TextView id = (TextView)view.findViewById(R.id.tv_id);
        TextView password = (TextView)view.findViewById(R.id.tv_password);
        TextView email = (TextView)view.findViewById(R.id.tv_email);
        TextView name = (TextView)view.findViewById(R.id.tv_name);
        TextView money = (TextView)view.findViewById(R.id.tv_money);

        //-------------------tmp_data--------------------------------//
        String Sid = "tmp_id";
        String Spassword = "tmp_password";
        String Semail = "tmp_email";
        String Sname = "tmp_name";
        String Smoney = "tmp_money";
        //-----------------------------------------------------------//

        //text setting
        id.setText("당신의 아이디는 "+ userInfo.getUserID() +" 입니다.");
        password.setText("당신의 비밀번호는 "+ userInfo.getUserPassword() +" 입니다.");
        email.setText("당신의 이메일은 "+ userInfo.getUserName() +" 입니다.");
        name.setText("당신의 이름은 "+ userInfo.getUserEmail() +" 입니다.");
        money.setText("당신이 보유한 더치머니는 "+ userInfo.getUserDutchMoney() +" 원 입니다.");

        return view;
    }
}