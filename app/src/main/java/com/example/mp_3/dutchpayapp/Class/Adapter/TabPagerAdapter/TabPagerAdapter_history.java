package com.example.mp_3.dutchpayapp.Class.Adapter.TabPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mp_3.dutchpayapp.FragmentFiles.SubFragment.DetailPaymentHistoryFragment;
import com.example.mp_3.dutchpayapp.FragmentFiles.SubFragment.DetailSubPaymentHistoryFragment;


public class TabPagerAdapter_history extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_history(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                DetailPaymentHistoryFragment detailPaymentHistoryFragment = new DetailPaymentHistoryFragment();
                return detailPaymentHistoryFragment;
            case 1:
                DetailSubPaymentHistoryFragment detailSubPaymentHistoryFragment = new DetailSubPaymentHistoryFragment();
                return detailSubPaymentHistoryFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
