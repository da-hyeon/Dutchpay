package com.example.mp_3.dutchpayapp.Class.RequestClass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentCompleted_DBDeleteRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/DutchPay_PaymentCompletedDelete.php";
    private Map<String , String> parameters;

    public PaymentCompleted_DBDeleteRequest(String userID, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null);
        parameters = new HashMap<>();
        parameters.put("userID" , userID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
