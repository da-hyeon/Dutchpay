package com.example.mp_3.dutchpayapp.Class.Adapter.TabPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mp_3.dutchpayapp.FragmentFiles.JoinDutchPayFragment;
import com.example.mp_3.dutchpayapp.FragmentFiles.SendMoneyFragment;
import com.example.mp_3.dutchpayapp.FragmentFiles.SoloPaymentFragment;
import com.example.mp_3.dutchpayapp.FragmentFiles.StartDutchPayFragment;
import com.example.mp_3.dutchpayapp.FragmentFiles.ViewMoreFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    //1번 들어옴
    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return StartDutchPayFragment.newInstance();
            case 1:
                JoinDutchPayFragment joinDutchPayFragment = new JoinDutchPayFragment();
                return joinDutchPayFragment;
            case 2:
                SoloPaymentFragment soloPaymentFragment = new SoloPaymentFragment();
                return soloPaymentFragment;
            case 3:
                SendMoneyFragment paymentHistoryFragment = new SendMoneyFragment();
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
