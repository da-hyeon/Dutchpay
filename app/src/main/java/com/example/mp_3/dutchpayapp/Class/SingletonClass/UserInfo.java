package com.example.mp_3.dutchpayapp.Class.SingletonClass;

public class UserInfo {

    private String userID;
    private String userPassword;
    private String userPaymentPassword;
    private String userName;
    private String userPhoneNumber;
    private String userEmail;
    private int userDutchMoney;
    private int userState;          // 0 : 결제 진행x  , 1 : 결제 진행중
    private String userQRCode;
    private String userPushID;

    private UserInfo(){

    }

    private static class UserInfoHolder {
        public static final UserInfo ourInstance = new UserInfo();
    }

    public static UserInfo getInstance() {
        return UserInfoHolder.ourInstance;
    }

    public void setUserInfo(String userID, String userPassword, String userPaymentPassword, String userName, String userPhoneNumber ,String userEmail, int userDutchMoney , int userState , String userPushID) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userPaymentPassword = userPaymentPassword;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userDutchMoney = userDutchMoney;
        this.userState = userState;
        this.userPushID = userPushID;
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

    public String getUserPaymentPassword() {
        return userPaymentPassword;
    }

    public void setUserPaymentPassword(String userPaymentPassword) {
        this.userPaymentPassword = userPaymentPassword;
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

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public String getUserQRCode() {
        return userQRCode;
    }

    public void setUserQRCode(String userQRCode) {
        this.userQRCode = userQRCode;
    }

    public String getUserPushID() {
        return userPushID;
    }

    public void setUserPushID(String userPushID) {
        this.userPushID = userPushID;
    }
}
