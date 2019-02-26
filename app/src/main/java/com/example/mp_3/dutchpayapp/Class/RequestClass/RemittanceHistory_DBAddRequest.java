package com.example.mp_3.dutchpayapp.Class.RequestClass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RemittanceHistory_DBAddRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/DutchPay_RemittanceHistory_RegisterRequest.php";
    private Map<String , String> parameters;

    public RemittanceHistory_DBAddRequest(String remittanceAmount , String remittererID, String receiverID, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null);
        parameters = new HashMap<>();
        // 금액 , 송금자 , 수금자
        parameters.put("remittanceAmount" , remittanceAmount);
        parameters.put("remittererID" , remittererID);
        parameters.put("receiverID" , receiverID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
