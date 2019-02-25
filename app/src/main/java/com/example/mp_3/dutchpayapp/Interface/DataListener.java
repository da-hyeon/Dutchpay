package com.example.mp_3.dutchpayapp.Interface;

import java.util.ArrayList;

public interface DataListener {

    void dataListenerSet(String tmp);
    //StartDutch -> QRcreate

    void ArrayListenerSet(ArrayList<String> tmp);

    void PayLisstenerSet(String tmp);
    //SoloPay -> QRscan

    void historyListenerSet();

    void sendMoneyListenerSet(String tmp);

    void logoutListenerSet();
}