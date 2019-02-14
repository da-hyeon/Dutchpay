package com.example.mp_3.dutchpayapp.Class.RequestClass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/DutchPay_Login.php";
    private Map<String , String> parameters;

    public LoginRequest(String userID , String userPassword, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null);
        parameters = new HashMap<>();
        parameters.put("userID" , userID);
        parameters.put("userPassword" , userPassword);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
