package com.sample.mytodolist;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by nat on 5/2/16.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    private NotificationManager alarmNotificationManager;

    @Override
    public void onReceive(final Context context, Intent intent) {



        Log.d("AlarmReceiver","Attempting to startwarkeful seravice");

      /*  Intent myIntent = new Intent(context, AlarmService.class);
        startWakefulService(context,myIntent);

        setResultCode(Activity.RESULT_OK);*/
        sendNotification(context,"Wake up Sid");
        Log.d("AlarmReceiver", "hopefully just called to startwarkeful seravice");
    }
    private void sendNotification(Context context ,String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context,Alset.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder alarmNotficationBuilder =
                new NotificationCompat.Builder(context);
        alarmNotficationBuilder.setContentTitle("Alarm Test");
        alarmNotficationBuilder.setSmallIcon(R.drawable.ic_alarm);
        alarmNotficationBuilder.setContentText(msg);
        alarmNotficationBuilder.setAutoCancel(true);


        alarmNotficationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alarmNotficationBuilder.build());
        Log.d("AlarmService", "Notification sent.");

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();


    /*    Intent i = new Intent(context,Alset.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/
    }

}