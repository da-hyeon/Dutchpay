package com.example.mp_3.dutchpayapp.FragmentFiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;
import com.example.mp_3.dutchpayapp.databinding.FragmentViewMoreBinding;

public class ViewMoreFragment extends Fragment {

    FragmentViewMoreBinding mBinding;

    UserInfo userInfo;

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
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_view_more, container , false);

        userInfo = UserInfo.getInstance();

        mBinding.cardViewTransactionHistory.setOnClickListener(v -> {
            dataListener.historyListenerSet();
        });

       // btn_paymentHistory.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        dataListener.historyListenerSet();
        //    }
        //});
       // btn_Logout = (Button) view.findViewById(R.id.btn_logout);
        //btn_Logout.setOnClickListener(new View.OnClickListener() {
         //   @Override
      //      public void onClick(View v) {
      //          dataListener.logoutListenerSet();
      //      }
      //  });

        //btn_customerInquiry = (Button) view.findViewById(R.id.btn_customerInquiry);
        //btn_customerInquiry.setOnClickListener(new View.OnClickListener() {
         //   @Override
          //  public void onClick(View v) {
           //     dataListener.customerInquiryListenerSet();
            //}
        //});

        //text setting
        mBinding.resultUserIDText.setText("아이디 : "+ userInfo.getUserID() +" 입니다.");
        mBinding.resultUserPasswordText.setText("비밀번호 : "+ userInfo.getUserPassword() +" 입니다.");
        mBinding.resultUserNameText.setText("이름 : "+ userInfo.getUserName());
        mBinding.resultUserEmailText.setText("이메일 : "+ userInfo.getUserEmail() );

        mBinding.resultUserPhoneNumberText.setText(userInfo.getUserPhoneNumber());

        mBinding.resultRetentionAmountText.setText(String.format("%,d", userInfo.getUserDutchMoney()) +"원");

        return mBinding.getRoot();
    }
}