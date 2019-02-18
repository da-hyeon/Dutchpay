package com.example.mp_3.dutchpayapp.Class.RequestClass;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ParticipantInfoUpdateRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/DutchPay_directInputAmountChange.php";
    private Map<String , String> parameters;

    public ParticipantInfoUpdateRequest(String userID, String assignedAmount , boolean prePaymentCheck, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null);
        parameters = new HashMap<>();
        parameters.put("userID" , userID);
        parameters.put("assignedAmount" , assignedAmount);
        if(prePaymentCheck)
            parameters.put("prePaymentCheck" , "1");
        else
            parameters.put("prePaymentCheck" , "0");
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
