package com.BgLogger.activity.reminders;

import java.util.Calendar;
import java.util.Date;

import com.BgLogger.R;
import com.BgLogger.R.drawable;
import com.BgLogger.model.alarm.AlarmDao;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	AlarmDao db;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		db = new AlarmDao(context);
		db.openToRead();
		
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Cursor cursor = db.queueAll();
			while (cursor.moveToNext()) {
				if (cursor.getInt(cursor
						.getColumnIndex(AlarmDao.ALARM_ENABLED_FIELD_NAME)) > 0) {
					Date alarmDate = new Date(cursor.getLong(cursor
							.getColumnIndex(AlarmDao.ALARM_TIME_FIELD_NAME)));
					Calendar aCal = Calendar.getInstance();
					aCal.setTime(alarmDate);
					String test = cursor.getString(cursor
							.getColumnIndex(AlarmDao.TEST_TYPE_FIELD_NAME));

					scheduleAlarms(test, aCal, context);
				}
			}
		}
		db.close();
		
	}

	public static void scheduleAlarms(String test_type, Calendar alarm_date,
			Context context) {
		Long time = alarm_date.getTimeInMillis();

		NotificationManager notificationManager;
		Notification myNotification;
		// setup a new intent and set the class which will execute when receiver
		// triggers.

		Intent intentAlarm = new Intent(context, AlarmService.class);

		intentAlarm.putExtra("test", test_type);

		// create an receiver manager object
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		PendingIntent pi = PendingIntent.getService(context, (int)alarm_date.getTimeInMillis(), intentAlarm, 0);

		// set the receiver for the time passed with the calendar object
		alarmManager.set(AlarmManager.RTC_WAKEUP, time, pi);

	}
	
	public static void deleteAlarms(Context context, String test, int id){
		AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		Intent i = new Intent(context,AlarmService.class);
		i.putExtra("test", test);
		PendingIntent p = PendingIntent.getService(context, id, i, 0);
		am.cancel(p);
		p.cancel();
	}
	
	public static void updateAlarms(){
		
	}

}
