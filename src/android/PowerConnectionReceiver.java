package com.red_folder.phonegap.plugin.backgroundservice;

import android.content.BroadcastReceiver;
import android.os.BatteryManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;



public class PowerConnectionReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) { 
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                            status == BatteryManager.BATTERY_STATUS_FULL;
    
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification note=new Notification(R.drawable.ic_launcher,
                                                            "Android Example Status message!",
                                                            System.currentTimeMillis());
             
            // This pending intent will open after notification click
            PendingIntent i=PendingIntent.getActivity(this, 0,
                                                    new Intent(this, MainActivity.class),
                                                    0);
             
            note.setLatestEventInfo(this, "Android Example Notification Title",
                                    "This is the android example notification message", i);
             
            //After uncomment this line you will see number of notification arrived
            //note.number=2;
            mgr.notify(100, note);
    }
}
