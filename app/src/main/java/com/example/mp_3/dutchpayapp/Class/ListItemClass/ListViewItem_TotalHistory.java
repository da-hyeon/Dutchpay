package com.example.mp_3.dutchpayapp.Class.ListItemClass;

public class ListViewItem_TotalHistory {
    int historyID;
    String Date;
    String companyName;
    String participantName;
    int Amount;

    public ListViewItem_TotalHistory(int historyID, String date, String companyName, String participantName, int amount) {
        this.historyID = historyID;
        Date = date;
        this.companyName = companyName;
        this.participantName = participantName;
        Amount = amount;
    }

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
