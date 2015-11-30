package com.BgLogger.activity.reminders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.BgLogger.R;
import com.BgLogger.model.alarm.Alarm;
import com.BgLogger.model.alarm.AlarmDao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlarmListAdapter extends BaseAdapter {

	private ArrayList<Alarm> alarms;
	private Context context;
	private AlarmDao data;

	public AlarmListAdapter(Context context, ArrayList<Alarm> alarms) {
		this.context = context;
		this.alarms = alarms;

	}
	
	public void setAlarms(ArrayList<Alarm> alarms){
		this.alarms = alarms;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		if(alarms != null){
			return alarms.size();
		}
		else return 0;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(alarms != null){
			return alarms.get(position);
		}
		else return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if(alarms != null){
			return alarms.get(position).getId();
		}
		return 0;
	}

	

	public View getView(final int position, View view, ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.alarm_list_item, parent, false);
		}
			
		final Alarm model = (Alarm) getItem(position);

		String date = model.getAlarmTimeFormatted();
		String[] dateParts = date.split("\\s+");
		
		TextView txtTime = (TextView) view.findViewById(R.id.alarm_item_time);
		txtTime.setText(dateParts[1]);
	//	txtTime.setText(String.format("%02d : %02d", model.getAlarmTime().getHours(),model.getAlarmTime().getMinutes()));
		
	//	Calendar calendar = Calendar.getInstance();
		TextView txtAmPm = (TextView) view.findViewById(R.id.alarm_item_ampm);
	//	calendar.setTime(model.getAlarmTime());
	//	if(calendar.AM_PM == Calendar.AM){
	//		txtAmPm.setText("AM");
	//	}else txtAmPm.setText("PM");
		txtAmPm.setText(dateParts[2]);
		
		TextView txtDate = (TextView) view.findViewById(R.id.alarm_item_date);
	//	txtDate.setText(String.format("%s %02d",model.getAlarmTime().getMonth(), model.getAlarmTime().getDay()));
		txtDate.setText(dateParts[0]);

		TextView txtTest = (TextView) view.findViewById(R.id.alarm_item_type);
		txtTest.setText(model.getTestType());
		
		ToggleButton btnToggle = (ToggleButton) view.findViewById(R.id.alarm_item_toggle);
		
		btnToggle.setChecked(model.isEnabled());
		
	//	view.setClickable(true);
		btnToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				((AlarmList) context).onToggleActivity(position, isChecked);
	
				
			}
		});
		
		
	//	view.setTag(Long.valueOf(model.id));
		view.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				
			
				((AlarmList)context).onClickActivity(model);
			}
		});
		
		view.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View view) {

				((AlarmList)context).onLongClickActivity(model,position);
			
				return true;
			}
		});
		
		return view;
	}

	public void setList(ArrayList<Alarm> alarms) {
		// TODO Auto-generated method stub
		this.alarms = alarms;
	}
	

	
	
}
