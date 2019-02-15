package com.example.mp_3.dutchpayapp.Class.ListItemClass;

public class ListViewItem_DutchStart {
    private int listNum;
    private String hostID;
    private String userID;
    private int Amount;
    private int directInputAmount;

    private boolean prePaymentCheck;

    public ListViewItem_DutchStart(int listNum , String hostID, String userID, int amount, int directInputAmount) {
        this.hostID = hostID;
        this.userID = userID;
        Amount = amount;
        this.directInputAmount = directInputAmount;
        this.listNum = listNum;
    }

    public int getListNum() {
        return listNum;
    }

    public void setListNum(int listNum) {
        this.listNum = listNum;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getDirectInputAmount() {
        return directInputAmount;
    }

    public void setDirectInputAmount(int directInputAmount) {
        this.directInputAmount = directInputAmount;
    }

    public boolean isPrePaymentCheck() {
        return prePaymentCheck;
    }

    public void setPrePaymentCheck(boolean prePaymentCheck) {
        this.prePaymentCheck = prePaymentCheck;
    }
}
