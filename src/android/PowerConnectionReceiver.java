package com.red_folder.phonegap.plugin.backgroundservice;

import android.content.BroadcastReceiver;
import android.os.BatteryManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.R;
import android.widget.Toast;
import android.media.AudioManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.os.Bundle;


public class PowerConnectionReceiver extends BroadcastReceiver {
    int notifyID = 11;
    boolean isServiceStarted=true;
    private AudioManager myAudioManager;
    final SmsManager sms = SmsManager.getDefault();
    public void showNotification(Context context, String title, String description){
    	try{
    	myAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    	//myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    	//myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    	//myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
    	final NotificationManager mgr=
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification note=new Notification(context.getApplicationInfo().icon,
                                                            title,
                                                            System.currentTimeMillis());
             note.sound=soundUri;
		Intent contentIntent = new Intent();
             PendingIntent appIntent = PendingIntent.getActivity(context, 0, contentIntent, 0);
	        note.setLatestEventInfo(context, title, description, appIntent);
            //After uncomment this line you will see number of notification arrived
            //note.number=2;
            mgr.notify(notifyID, note);
    	}
    	catch(Exception e){
    		Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
            	toast.show();
            	Toast toast2 = Toast.makeText(context, "show notification exception", Toast.LENGTH_SHORT);
                toast2.show();
    	}
    }
    
    public void cancelNotification(Context ctx, int notifyId) {
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
    	nMgr.cancel(notifyId);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      	Toast toast4 = Toast.makeText(context,"On Receive" , Toast.LENGTH_SHORT);
    	toast4.show();
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
 
        try {
             
            if (bundle != null) {
                 
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                 
                for (int i = 0; i < pdusObj.length; i++) {
                     
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                     
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                   // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast3 = Toast.makeText(context, 
                                 "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast3.show();
                     
                } // end for loop
              } // bundle is null
 
        }
        catch(Exception e){
            Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            Toast toast1 = Toast.makeText(context, "on receive exception", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }
}
