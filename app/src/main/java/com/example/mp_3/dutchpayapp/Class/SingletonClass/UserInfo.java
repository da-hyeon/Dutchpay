package com.example.mp_3.dutchpayapp.Class.SingletonClass;

public class UserInfo {

    private String userID;
    private String userPassword;
    private String userName;
    private String userEmail;
    private int userDutchMoney;

    private UserInfo(){

    }

    private static class UserInfoHolder {
        public static final UserInfo ourInstance = new UserInfo();
    }

    public static UserInfo getInstance() {
        return UserInfoHolder.ourInstance;
    }

    public void setUserInfo(String userID, String userPassword, String userName, String userEmail, int userDutchMoney) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userDutchMoney = userDutchMoney;
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
}
