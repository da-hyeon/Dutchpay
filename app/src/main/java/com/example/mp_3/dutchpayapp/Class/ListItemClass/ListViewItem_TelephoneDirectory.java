package com.example.mp_3.dutchpayapp.Class.ListItemClass;

public class ListViewItem_TelephoneDirectory {
    String userID;
    String userName;
    String userPhoneNumber;

    public ListViewItem_TelephoneDirectory(String userID, String userName, String userPhoneNumber) {
        this.userID = userID;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}
