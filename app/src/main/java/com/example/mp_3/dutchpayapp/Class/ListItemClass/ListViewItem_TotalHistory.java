package com.example.mp_3.dutchpayapp.Class.ListItemClass;

import android.view.View;

import java.util.ArrayList;

public class ListViewItem_TotalHistory {

    private String delimiter;
    private String dateYMD;
    private String dateHMS;
    private String remittererID;
    private String receiverID;
    private int remittanceAmount;

    ////////////////////////////////////////////////

    private String price;
    private String pledgePrice;
    private String fromAddress;
    private String toAddress;
    private int requestsCount;
    private String date;
    private String time;

    private View.OnClickListener requestBtnClickListener;


    ////////////////////////////////////////////////
    public ListViewItem_TotalHistory(String delimiter, String dateYMD, String dateHMS, String remittererID, String receiverID, int remittanceAmount) {
        this.delimiter = delimiter;
        this.dateYMD = dateYMD;
        this.dateHMS = dateHMS;
        this.remittererID = remittererID;
        this.receiverID = receiverID;
        this.remittanceAmount = remittanceAmount;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDateYMD() {
        return dateYMD;
    }

    public void setDateYMD(String dateYMD) {
        this.dateYMD = dateYMD;
    }

    public String getDateHMS() {
        return dateHMS;
    }

    public void setDateHMS(String dateHMS) {
        this.dateHMS = dateHMS;
    }

    public String getRemittererID() {
        return remittererID;
    }

    public void setRemittererID(String remittererID) {
        this.remittererID = remittererID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public int getRemittanceAmount() {
        return remittanceAmount;
    }

    public void setRemittanceAmount(int remittanceAmount) {
        this.remittanceAmount = remittanceAmount;
    }

    ////////////////////////////////////////////////

    public ListViewItem_TotalHistory(String price, String pledgePrice, String fromAddress, String toAddress, int requestsCount, String date, String time) {
        this.price = price;
        this.pledgePrice = pledgePrice;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.requestsCount = requestsCount;
        this.date = date;
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPledgePrice() {
        return pledgePrice;
    }

    public void setPledgePrice(String pledgePrice) {
        this.pledgePrice = pledgePrice;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public int getRequestsCount() {
        return requestsCount;
    }

    public void setRequestsCount(int requestsCount) {
        this.requestsCount = requestsCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListViewItem_TotalHistory item = (ListViewItem_TotalHistory) o;

        if (requestsCount != item.requestsCount) return false;
        if (price != null ? !price.equals(item.price) : item.price != null) return false;
        if (pledgePrice != null ? !pledgePrice.equals(item.pledgePrice) : item.pledgePrice != null)
            return false;
        if (fromAddress != null ? !fromAddress.equals(item.fromAddress) : item.fromAddress != null)
            return false;
        if (toAddress != null ? !toAddress.equals(item.toAddress) : item.toAddress != null)
            return false;
        if (date != null ? !date.equals(item.date) : item.date != null) return false;
        return !(time != null ? !time.equals(item.time) : item.time != null);

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (pledgePrice != null ? pledgePrice.hashCode() : 0);
        result = 31 * result + (fromAddress != null ? fromAddress.hashCode() : 0);
        result = 31 * result + (toAddress != null ? toAddress.hashCode() : 0);
        result = 31 * result + requestsCount;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<ListViewItem_TotalHistory> getTestingList() {
        ArrayList<ListViewItem_TotalHistory> items = new ArrayList<>();
//            items.add(new ListViewItem_TotalHistory("$14", "$270", "W 79th St, NY, 10024", "W 139th St, NY, 10030", 3, "TODAY", "05:10 PM"));
//            items.add(new ListViewItem_TotalHistory("$23", "$116", "W 36th St, NY, 10015", "W 114th St, NY, 10037", 10, "TODAY", "11:10 AM"));
//            items.add(new ListViewItem_TotalHistory("$63", "$350", "W 36th St, NY, 10029", "56th Ave, NY, 10041", 0, "TODAY", "07:11 PM"));
//            items.add(new ListViewItem_TotalHistory("$19", "$150", "12th Ave, NY, 10012", "W 57th St, NY, 10048", 8, "TODAY", "4:15 AM"));
//            items.add(new ListViewItem_TotalHistory("$5", "$300", "56th Ave, NY, 10041", "W 36th St, NY, 10029", 0, "TODAY", "06:15 PM"));
        return items;
    }


//    int historyID;
//    String Date;
//    String companyName;
//    String participantName;
//    int Amount;
//
//    public ListViewItem_TotalHistory(int historyID, String date, String companyName, String participantName, int amount) {
//        this.historyID = historyID;
//        Date = date;
//        this.companyName = companyName;
//        this.participantName = participantName;
//        Amount = amount;
//    }
//
//    public int getHistoryID() {
//        return historyID;
//    }
//
//    public void setHistoryID(int historyID) {
//        this.historyID = historyID;
//    }
//
//    public String getDate() {
//        return Date;
//    }
//
//    public void setDate(String date) {
//        Date = date;
//    }
//
//    public String getCompanyName() {
//        return companyName;
//    }
//
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//
//    public String getParticipantName() {
//        return participantName;
//    }
//
//    public void setParticipantName(String participantName) {
//        this.participantName = participantName;
//    }
//
//    public int getAmount() {
//        return Amount;
//    }
//
//    public void setAmount(int amount) {
//        Amount = amount;
//    }
}
