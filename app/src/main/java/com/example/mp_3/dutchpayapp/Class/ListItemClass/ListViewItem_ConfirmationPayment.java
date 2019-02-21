package com.example.mp_3.dutchpayapp.Class.ListItemClass;

public class ListViewItem_ConfirmationPayment {
    private String userID;
    private int assignedAmount;
    private boolean prePaymentCheck;

    public ListViewItem_ConfirmationPayment( String userID , int assignedAmount) {
        this.userID = userID;
        this.assignedAmount = assignedAmount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
}
