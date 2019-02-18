package com.example.mp_3.dutchpayapp.Class.ListItemClass;

public class ListViewItem_DutchStartConfirmed {
    private int listNum;
    private String hostID;
    private String userID;
    private int amount;
    private int assignedAmount;
    private int distributedAmount;

    private boolean prePaymentCheck;

    public ListViewItem_DutchStartConfirmed(int listNum , String hostID, String userID, int amount, int assignedAmount, int prePaymentCheck) {
        this.listNum = listNum;
        this.hostID = hostID;
        this.userID = userID;
        this.amount = amount;
        this.assignedAmount = assignedAmount;
        this.distributedAmount = distributedAmount;

        if(prePaymentCheck == 1)
            this.prePaymentCheck = true;
        else
            this.prePaymentCheck = false;
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
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAssignedAmount() {
        return assignedAmount;
    }

    public void setAssignedAmount(int assignedAmount) {
        this.assignedAmount = assignedAmount;
    }

    public boolean isPrePaymentCheck() {
        return prePaymentCheck;
    }

    public void setPrePaymentCheck(boolean prePaymentCheck) {
        this.prePaymentCheck = prePaymentCheck;
    }

    public int getDistributedAmount() {
        return distributedAmount;
    }

    public void setDistributedAmount(int distributedAmount) {
        this.distributedAmount = distributedAmount;
    }
}
