package com.example.mp_3.dutchpayapp.Class.Handler;

import android.content.Context;

import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

public class NotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
    private MainActivity _MainActivity;
    private Context context;
    public NotificationReceivedHandler(Context context){
        this.context = context;
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        _MainActivity = MainActivity._MainActivity;
        //
        notification.displayType = OSNotification.DisplayType.None;
        _MainActivity.recreate();
    }
}
