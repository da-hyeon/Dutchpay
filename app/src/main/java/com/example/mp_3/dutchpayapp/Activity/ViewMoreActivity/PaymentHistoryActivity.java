package com.example.mp_3.dutchpayapp.Activity.ViewMoreActivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mp_3.dutchpayapp.MainActivity;
import com.example.mp_3.dutchpayapp.Class.Adapter.TabPagerAdapter.TabPagerAdapter_history;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;

public class PaymentHistoryActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DataListener dataListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.detail_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("송금").setIcon(R.drawable.remittance_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setText("결제").setIcon(R.drawable.send_money_gray_24dp));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.detail_pager);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter_history pagerAdapter = new TabPagerAdapter_history(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabLayout.getTabAt(0).setIcon(R.drawable.remittance_gray_24dp);
                tabLayout.getTabAt(1).setIcon(R.drawable.send_money_gray_24dp);

                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.remittance_black_24dp);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.send_money_black_24dp);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        MainActivity.customProgressDialog.dismiss();
    }
}
