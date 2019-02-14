package com.example.mp_3.dutchpayapp.Class.RequestClass;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class QRScan_DBAddRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/DutchPay_QRScan.php";
    private Map<String , String> parameters;

    public QRScan_DBAddRequest(String hostID , String userID, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null);
        parameters = new HashMap<>();
        parameters.put("hostID" , hostID);
        parameters.put("userID" , userID);
        Log.d("QRScan_DBAddRequest : " , "진입");
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
