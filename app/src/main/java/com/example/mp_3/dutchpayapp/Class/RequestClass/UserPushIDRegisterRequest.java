package com.example.mp_3.dutchpayapp.Class.RequestClass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserPushIDRegisterRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/DutchPay_UserPushIDRegister.php";
    private Map<String , String> parameters;

    public UserPushIDRegisterRequest(String userID , String userPushID ,  Response.Listener<String> listener){
        super(Method.POST , URL , listener , null);
        parameters = new HashMap<>();
        parameters.put("userID" , userID);
        parameters.put("userPushID" , userPushID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
