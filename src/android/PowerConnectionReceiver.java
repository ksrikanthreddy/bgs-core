package com.red_folder.phonegap.plugin.backgroundservice;

import android.content.BroadcastReceiver;
import android.os.BatteryManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.R;
import android.widget.Toast;


public class PowerConnectionReceiver extends BroadcastReceiver {
    
    public void showNotification(Context context, String title, String description){
    	try{
    	final NotificationManager mgr=
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification note=new Notification(R.drawable.star_big_on,
                                                            title,
                                                            System.currentTimeMillis());
		Intent contentIntent = new Intent();
             PendingIntent appIntent = PendingIntent.getActivity(context, 0, contentIntent, 0);
	        note.setLatestEventInfo(context, title, description, appIntent);
            //After uncomment this line you will see number of notification arrived
            //note.number=2;
            mgr.notify(100, note);	
    	}
    	catch(Exception e){
    		Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
            	toast.show();
    	}
    }
    @Override
    public void onReceive(Context context, Intent intent) { 
        Toast toast1 = Toast.makeText(context, "Safe Battery Enabled", Toast.LENGTH_SHORT);
        toast1.show();
        try{
       	String batteryStatus = "";
       	IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
    	boolean isFull = status == BatteryManager.BATTERY_STATUS_FULL;
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Toast toast5 = Toast.makeText(context, "status - "+String.valueOf(status), Toast.LENGTH_SHORT);
            toast5.show();
            Toast toast6 = Toast.makeText(context, "BATTERY_STATUS_CHARGING - "+String.valueOf(BatteryManager.BATTERY_STATUS_CHARGING), Toast.LENGTH_SHORT);
            toast6.show();
            Toast toast7 = Toast.makeText(context, "chargePlug - "+String.valueOf(chargePlug), Toast.LENGTH_SHORT);
            toast7.show();
            Toast toast8 = Toast.makeText(context, "BATTERY_PLUGGED_USB - "+String.valueOf(BatteryManager.BATTERY_PLUGGED_USB), Toast.LENGTH_SHORT);
            toast8.show();
        Toast toast2 = Toast.makeText(context, "isCharging - "+String.valueOf(isCharging), Toast.LENGTH_SHORT);
            toast2.show();
            Toast toast3 = Toast.makeText(context, "isFull - "+String.valueOf(isFull), Toast.LENGTH_SHORT);
            toast3.show();
            Toast toast4 = Toast.makeText(context, "usbCharge - "+String.valueOf(usbCharge), Toast.LENGTH_SHORT);
            toast4.show();
        if(usbCharge)
        {
        	batteryStatus = "USB";
        }
        if(acCharge)
        {
        	batteryStatus = "AC Power";
        }
        if(isCharging){
        	showNotification(context,"Safe Battery", batteryStatus + " Charging.");
        }
        if(isFull){
        	showNotification(context,"Safe Battery", "100% charged. Unplug Charger.");
        }
        }
        catch(Exception e){
            Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
