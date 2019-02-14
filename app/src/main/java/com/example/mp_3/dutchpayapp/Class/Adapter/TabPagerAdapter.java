package com.example.mp_3.dutchpayapp.Class.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.mp_3.dutchpayapp.Class.SingletonClass.GlobalVariable;
import com.example.mp_3.dutchpayapp.Fragment.JoinDutchPayFragment;
import com.example.mp_3.dutchpayapp.Fragment.PaymentHistoryFragment;
import com.example.mp_3.dutchpayapp.Fragment.SoloPaymentFragment;
import com.example.mp_3.dutchpayapp.Fragment.StartDutchPayFragment;
import com.example.mp_3.dutchpayapp.Fragment.ViewMoreFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;
    private GlobalVariable globalVariable;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
        globalVariable = GlobalVariable.getInstance();
    }

    //1번 들어옴
    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return StartDutchPayFragment.newInstance();
            case 1:
                Log.d("getStartPageState : ", globalVariable.getStartPageState() + "");
                JoinDutchPayFragment joinDutchPayFragment = new JoinDutchPayFragment();
                return joinDutchPayFragment;
            case 2:
                SoloPaymentFragment soloPaymentFragment = new SoloPaymentFragment();
                return soloPaymentFragment;
            case 3:
                PaymentHistoryFragment paymentHistoryFragment = new PaymentHistoryFragment();
                return paymentHistoryFragment;
            case 4:
                ViewMoreFragment viewMoreFragment = new ViewMoreFragment();
                return viewMoreFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
