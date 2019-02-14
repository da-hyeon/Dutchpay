package com.example.mp_3.dutchpayapp.Class.RequestClass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class QRCreate_DBAddRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/DutchPay_QRCreate.php";
    private Map<String , String> parameters;

    public QRCreate_DBAddRequest(String userID , String Amount, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null);
        parameters = new HashMap<>();
        parameters.put("userID" , userID);
        parameters.put("Amount" , Amount);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
