package com.sample.mytodolist;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        String str = intent.getStringExtra("notification");
        if (str != null)
            sendNotification(str);
        else {
            sendNotification("Wake up!");
        }
    }

    private void sendNotification(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder alarmNotficationBuilder =
                new NotificationCompat.Builder(this);
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
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();
    }
}
