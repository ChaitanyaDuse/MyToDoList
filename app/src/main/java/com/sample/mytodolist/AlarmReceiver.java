package com.sample.mytodolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by nat on 5/2/16.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {



        Log.d("AlarmReceiver","Attempting to startwarkeful seravice");

        Intent myIntent = new Intent(context, AlarmService.class);
        startWakefulService(context,myIntent);

        setResultCode(Activity.RESULT_OK);
        Log.d("AlarmReceiver", "hopefully just called to startwarkeful seravice");
    }

}