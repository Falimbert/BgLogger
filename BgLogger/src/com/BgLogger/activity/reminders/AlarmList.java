package com.BgLogger.activity.reminders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.BgLogger.ActionBarActivity;
import com.BgLogger.MedicineHistory;
import com.BgLogger.R;
import com.BgLogger.model.alarm.Alarm;
import com.BgLogger.model.alarm.AlarmDao;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmList extends ActionBarActivity{

	private AlarmDao data;
	private AlarmListAdapter adapter;
	private Cursor c;
	private ListView list;
	private ArrayList<Alarm> alarms;
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd hh:mm aa");
	private AlarmManagerBroadcastReceiver receiver;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.alarm_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list);
		title(getString(R.string.reminder));
		TextView tv = (TextView)findViewById(R.id.tvCommontv);
		tv.setText("Reminders");
		list = (ListView)findViewById(android.R.id.list);
	//	registerForContextMenu(list);

		
		setList();
		adapter = new AlarmListAdapter(AlarmList.this, alarms);
		list.setAdapter(adapter);
		data.close();
	
	}
	private void setList() {
		Alarm myAlarm;
		alarms = new ArrayList<Alarm>();
		data = new AlarmDao(AlarmList.this);
		data.openToRead();
		c = data.queueAll();
		if (c != null && c.getCount() != 0) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				myAlarm = new Alarm();
				try {
					myAlarm.setAlarmTime(sdf.parse(c.getString(c.getColumnIndex(AlarmDao.ALARM_TIME_FIELD_NAME))));
					myAlarm.getAlarmTimeFormatted();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myAlarm.setEnabled(c.getInt(c
						.getColumnIndex(AlarmDao.ALARM_ENABLED_FIELD_NAME)) > 0);
				myAlarm.setId(c.getInt(c.getColumnIndex(AlarmDao.ID_FIELD_NAME)));
				myAlarm.setTestType(c.getString(c
						.getColumnIndex(AlarmDao.TEST_TYPE_FIELD_NAME)));
				alarms.add(myAlarm);

			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_add_new_alarm:
			Intent i = new Intent(AlarmList.this, AddNewAlarm.class);
			startActivityForResult(i, 1);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClickActivity(Alarm a){
		
		Intent i =new Intent(AlarmList.this,AddNewAlarm.class);
		Bundle basket = new Bundle();
		basket.putString("date", a.getAlarmTimeFormatted());
		basket.putString("test", a.getTestType());
		basket.putInt("id", a.getId());
		basket.putInt("requestcode",2);
		i.putExtras(basket);
		startActivityForResult(i, 2);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 2 && resultCode == RESULT_OK){
			this.data.openToWrite();
			this.data.update((Alarm)data.getSerializableExtra("data"), data.getIntExtra("id", 0),null);
			setList();
			adapter.setList(alarms);
			adapter.notifyDataSetChanged();
			
		}
		if(requestCode == 1 && resultCode == RESULT_OK){
			setList();
			adapter.setList(alarms);
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onLongClickActivity(Alarm model, int position){
		data = new AlarmDao(AlarmList.this);
		data.openToWrite();
		data.delete(model.getId());
		Toast.makeText(AlarmList.this, "The alarm has been deleted!", Toast.LENGTH_LONG).show();
		//alarms.remove(position);
		setList();
		adapter.setList(alarms);
		adapter.notifyDataSetChanged();
		data.close();
		receiver.deleteAlarms(AlarmList.this, model.getTestType(), (int)model.getAlarmTime().getTime());
		
		
		
	}
	
	public void onToggleActivity( int position, boolean isChecked){
		alarms.get(position).setEnabled(isChecked);
		data = new AlarmDao(AlarmList.this);
		data.openToWrite();
		data.update(alarms.get(position), alarms.get(position).getId(), null);
		data.close();
	}

	public void setAlarmEnabled(long id, boolean isenabled) {
		/*
		 * // TODO Auto-generated method stub AlarmCreateDelete modify = new
		 * AlarmCreateDelete(AlarmList.this); data.queueSpecific(columns,
		 * selection, selectionArgs, groupBy, having, orderBy)
		 * 
		 * Alarm model = dbHelper.getAlarm(id); model.isEnabled = isEnabled;
		 * dbHelper.updateAlarm(model);
		 * 
		 * AlarmManagerHelper.setAlarms(this);
		 */

	}

}