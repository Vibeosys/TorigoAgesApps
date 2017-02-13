package com.vibeosys.lawyerdiary.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.vibeosys.lawyerdiary.activities.CaseDetailsActivity;
import com.vibeosys.lawyerdiary.activities.CasesActivity;

/**
 * Created by Vibeosys software on 11-10-2016.
 */

/**
 * Reminder Receiver give the notification reminder to the user
 * Use the alarm ring tone extends on WakefulBroadcastReceiver
 * wake on alarm Notification received
 */
public class ReminderReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        CasesActivity inst = CasesActivity.instance();
        //inst.setAlarmText("Alarm! Wake up! Wake up!");

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                ReminderService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
