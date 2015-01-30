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
import android.database.Cursor;


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
             //note.sound=soundUri;
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
    public void deleteSMS(Context context, String message, String number) {
    try {
    	
        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor c = context.getContentResolver().query(
                uriSms,
                null, null, null, null);
                long count = c.getCount();
                int loopcnt=0;
                Toast toast19 = Toast.makeText(context, 
                                 "COUNT: "+String.valueOf(count), Toast.LENGTH_SHORT);
                    toast19.show();
        if (c != null && c.moveToLast()) {
            do {
            	loopcnt++;
                long id = c.getLong(0);
                long threadId = c.getLong(1);
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));
                String date = c.getString(c.getColumnIndex("date"));
                 if(loopcnt > 650){
                 	Toast toast17 = Toast.makeText(context, 
                                 "MSG: " + address+":"+body, Toast.LENGTH_SHORT);
                    	toast17.show();
                 }
                if (address.equals(number)) {
                    // mLogger.logInfo("Deleting SMS with id: " + threadId);
                   Toast toast37 = Toast.makeText(context, 
                                 "COL 4: "+c.getColumnName(4), Toast.LENGTH_SHORT);
                    toast37.show();
                    context.getContentResolver().delete(
                            Uri.parse("content://sms/conversations/" + threadId), null,
                            null);
                    Toast toast7 = Toast.makeText(context, 
                                 "DELETE: " + "Success...... ", Toast.LENGTH_SHORT);
                    toast7.show();
                }
            } while (c.moveToPrevious());
            //Toast toast5 = Toast.makeText(context, 
              //                   "address:  " + address + ";body: " + body
                //                + ";date:  " + date + "; 3 > "
                  //              + c.getString(c.getColumnIndex("status")) , Toast.LENGTH_SHORT);
                    //toast5.show();
                     Toast toast5 = Toast.makeText(context, 
                                 "loop count: "+String.valueOf(loopcnt) , Toast.LENGTH_SHORT);
                    toast5.show();
        }
    } catch (Exception e) {
        
        Toast toast8 = Toast.makeText(context, 
                                 e.toString(), Toast.LENGTH_SHORT);
                    toast8.show();
                    showNotification(context,"Exception",e.toString());
                    Toast toast9 = Toast.makeText(context, 
                                 "Delete SMS Exception", Toast.LENGTH_SHORT);
                    toast9.show();
    }
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
                    deleteSMS(context,message,senderNum);
                     
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
