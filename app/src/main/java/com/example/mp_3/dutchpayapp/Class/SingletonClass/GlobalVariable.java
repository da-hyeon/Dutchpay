package com.example.mp_3.dutchpayapp.Class.SingletonClass;

public class GlobalVariable {

    private static int startPageState;     //결제 진행 페이지

    private static class GlobalVariableHolder {
        public static final GlobalVariable ourInstance = new GlobalVariable();
    }

    public static GlobalVariable getInstance() {
        return GlobalVariableHolder.ourInstance;
    }

    private GlobalVariable() {
    }

    public int getStartPageState() {
        return startPageState;
    }

    public void setStartPageState(int startPageState) {
        this.startPageState = startPageState;
    }
}
