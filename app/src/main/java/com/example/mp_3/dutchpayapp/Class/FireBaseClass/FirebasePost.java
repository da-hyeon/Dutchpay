package com.example.mp_3.dutchpayapp.Class.FireBaseClass;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {
    public String hostID;
    public String userID;
    public boolean completeState;

    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String hostID, String userID, boolean completeState) {
        this.hostID = hostID;
        this.userID = userID;
        this.completeState = completeState;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("hostID", hostID);
        result.put("userID", userID);
        result.put("completeState", completeState);
        return result;
    }
}
