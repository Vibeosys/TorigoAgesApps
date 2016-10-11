package com.vibeosys.lawyerdiary.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.activities.CaseDetailsActivity;
import com.vibeosys.lawyerdiary.activities.CasesActivity;

/**
 * Created by akshay on 11-10-2016.
 */
public class ReminderService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public ReminderService() {
        super(ReminderService.class.getSimpleName());
    }

    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification(getResources().getString(R.string.app_name) + " Notification");
    }

    private void sendNotification(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, CasesActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle(getResources().getString(R.string.app_name) + " Reminder").setSmallIcon(R.drawable.ic_icon)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);
        alamNotificationBuilder.setContentIntent(contentIntent);
        alamNotificationBuilder.setAutoCancel(true);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}