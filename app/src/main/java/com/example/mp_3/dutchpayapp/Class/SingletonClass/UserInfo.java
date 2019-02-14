package com.example.mp_3.dutchpayapp.Class.SingletonClass;

public class UserInfo {

    private String userID;
    private String userPassword;
    private String userName;
    private String userEmail;
    private int userDutchMoney;
    private boolean userState;          // 0 : 결제 진행x  , 1 : 결제 진행중
    private String userQRCode;

    private UserInfo(){

    }

    private static class UserInfoHolder {
        public static final UserInfo ourInstance = new UserInfo();
    }

    public static UserInfo getInstance() {
        return UserInfoHolder.ourInstance;
    }

    public void setUserInfo(String userID, String userPassword, String userName, String userEmail, int userDutchMoney , boolean userState) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userDutchMoney = userDutchMoney;
        this.userState = userState;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserDutchMoney() {
        return userDutchMoney;
    }

    public void setUserDutchMoney(int userDutchMoney) {
        this.userDutchMoney = userDutchMoney;
    }

    public boolean isUserState() {
        return userState;
    }

    public void setUserState(boolean userState) {
        this.userState = userState;
    }

    public String getUserQRCode() {
        return userQRCode;
    }

    public void setUserQRCode(String userQRCode) {
        this.userQRCode = userQRCode;
    }
}
