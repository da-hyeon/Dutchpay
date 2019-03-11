package com.example.mp_3.dutchpayapp.Class.Handler;

import android.content.Context;

import com.example.mp_3.dutchpayapp.MainActivity;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

public class NotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
    private Context context;
    public NotificationReceivedHandler(Context context){
        this.context = context;
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        notification.displayType = OSNotification.DisplayType.None;
        ((MainActivity)context).recreate();
    }
}
