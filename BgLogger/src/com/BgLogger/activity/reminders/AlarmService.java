package com.BgLogger.activity.reminders;

import com.BgLogger.R;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmService extends IntentService{
	NotificationManager notificationManager;
	Notification myNotification;
	private static final int MY_NOTIFICATION_ID = 1;
	public AlarmService() {
		super("AlarmService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		String test = intent.getStringExtra("test");
		//setup a message to be shown with the reminder
		myNotification = new NotificationCompat.Builder(AlarmService.this)
				.setContentTitle("Notification!")
				.setContentText("Time for " + test + " checkup")
				.setTicker("Notification!").setWhen(System.currentTimeMillis())
				.setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(false)
				.setSmallIcon(R.drawable.ic_launcher).setVibrate(null).build();

		notificationManager = (NotificationManager) AlarmService.this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
		
		
	}

}
