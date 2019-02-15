package com.example.mp_3.dutchpayapp.Class.ListItemClass;

public class ListViewItem_DutchStart {
    private String member;
    private int cost;
    private int userCost;

    public ListViewItem_DutchStart(String member, int cost, int userCost) {
        this.member = member;
        this.cost = cost;
        this.userCost = userCost;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getUserCost() {
        return userCost;
    }

    public void setUserCost(int userCost) {
        this.userCost = userCost;
    }
}
