package com.sample.mytodolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Alset extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        Log.e("IM here ", "Im here");

        findViewById(R.id.dismiss_alarm).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), Alset.class);
                Bundle bundle = new Bundle();
                bundle.putString("notification","Wake up sid");
                myIntent.putExtras(bundle);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
                        myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent.cancel();
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
                ringtone.stop();
              //  Toast.makeText(Alset.this, "Stop the alrm now", Toast.LENGTH_LONG).show();

            }
        });

    }


}



