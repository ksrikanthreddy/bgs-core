package com.red_folder.phonegap.plugin.backgroundservice;

import android.content.BroadcastReceiver;
import android.os.BatteryManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.R;



public class PowerConnectionReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) { 
        Toast toast = Toast.makeText(context, "started", Toast.LENGTH_SHORT);
        try{
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                            status == BatteryManager.BATTERY_STATUS_FULL;
    
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        final NotificationManager mgr=
                (NotificationManager) context.getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);
            Notification note=new Notification(R.drawable.star_big_on,
                                                            "Android Example Status message!",
                                                            System.currentTimeMillis());
             
            //After uncomment this line you will see number of notification arrived
            //note.number=2;
            mgr.notify(100, note);
        }
        catch(Exception e){
            Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }
}
