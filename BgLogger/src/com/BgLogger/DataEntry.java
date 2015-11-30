package com.BgLogger;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.view.View;
import com.BgLogger.R;
import com.BgLogger.activity.glucose.AddGlucoseLogActivity;
import com.BgLogger.activity.insulin.AddInsulinLogActivity;
import com.BgLogger.activity.slidingpane.Sliding;
import com.BgLogger.model.glucose.BloodGlucoseLogDao;
import com.BgLogger.model.insulin.InsulinLogDao;

public class DataEntry extends Sliding {
	/** Called when the activity is first created. */

	Button logbutton;
	Button listbutton;
	Button mainbutton;
	Button calculatorbutton;
	
	// slide menu items
		private String[] navMenuTitles;
		private TypedArray navMenuIcons;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.data_entry1);
		
		addListenerOnButton();
		ViewStub Myhome = (ViewStub) findViewById(R.id.homeView);
		Myhome.setVisibility(View.VISIBLE);

		Button addRecordImageButton = (Button) findViewById(R.id.AddRecordButton);
		addRecordImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(),
						AddGlucoseLogActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		


		
	}

	public void addListenerOnButton() {
		final ImageButton BtHome = (ImageButton) findViewById(R.id.imageButton1);
		final ImageButton BtMeals = (ImageButton) findViewById(R.id.imageButton2);
		final ImageButton BtExercise = (ImageButton) findViewById(R.id.imageButton3);
		final ImageButton BtInsulin = (ImageButton) findViewById(R.id.imageButton4);
		final ImageButton BtGraph = (ImageButton) findViewById(R.id.imageButton5);

		final ViewStub Myhome = (ViewStub) findViewById(R.id.homeView);
		final ViewStub Mymeals = (ViewStub) findViewById(R.id.mealsView);
		final ViewStub Myexercise = (ViewStub) findViewById(R.id.exerciseView);
		final ViewStub Myinsulin = (ViewStub) findViewById(R.id.insulineView);
		final ViewStub Myreports = (ViewStub) findViewById(R.id.reportView);

		// //////////HOME////////////////////////////////////
		BtHome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BtHome.setImageResource(R.drawable.dropover);
				BtMeals.setImageResource(R.drawable.meal);
				BtExercise.setImageResource(R.drawable.exce);
				BtInsulin.setImageResource(R.drawable.med);
				BtGraph.setImageResource(R.drawable.graph);

				Myhome.setVisibility(View.VISIBLE);
				Mymeals.setVisibility(View.GONE);
				Myexercise.setVisibility(View.GONE);
				Myinsulin.setVisibility(View.GONE);
				Myreports.setVisibility(View.GONE);

				final BloodGlucoseLogDao bloodGlucoseLogDao = new BloodGlucoseLogDao(
						v.getContext());

				Button addRecordImageButton = (Button) findViewById(R.id.AddRecordButton);
				addRecordImageButton
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								Intent myIntent = new Intent(v.getContext(),
										AddGlucoseLogActivity.class);
								startActivityForResult(myIntent, 0);
							}
						});
				Button deleteAllImageButton = (Button) findViewById(R.id.DeleteAllButton);
				deleteAllImageButton
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								bloodGlucoseLogDao.deleteAll();
							}
						});

				ListView glucoseLogListView = (ListView) findViewById(R.id.glucoseLogListView);

				SimpleCursorAdapter simpleCursorAdapter;
				Cursor cursor;

				bloodGlucoseLogDao.openToRead();
				cursor = bloodGlucoseLogDao.queueAll();

				String[] field = new String[] {
						BloodGlucoseLogDao.LOG_TIME_FIELD_NAME,
						BloodGlucoseLogDao.READING_FIELD_NAME };
				int[] viewId = new int[] { R.id.LogTimeTextView,
						R.id.GlucoseResultTextView };

				simpleCursorAdapter = new SimpleCursorAdapter(v.getContext(),
						R.layout.glucose_log_row, cursor, field, viewId);
				glucoseLogListView.setAdapter(simpleCursorAdapter);
			}
		});

		// //////////MEALS////////////////////////////////////
		BtMeals.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				BtHome.setImageResource(R.drawable.drop);
				BtMeals.setImageResource(R.drawable.mealover);
				BtExercise.setImageResource(R.drawable.exce);
				BtInsulin.setImageResource(R.drawable.med);
				BtGraph.setImageResource(R.drawable.graph);

				Mymeals.setVisibility(View.VISIBLE);
				Myhome.setVisibility(View.GONE);
				Myexercise.setVisibility(View.GONE);
				Myinsulin.setVisibility(View.GONE);
				Myreports.setVisibility(View.GONE);

				// /PLACE CODE HERE///

				Button logbutton = (Button) findViewById(R.id.log);
				logbutton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(),
								FoodlogActivity.class);
						startActivityForResult(myIntent, 0);
					}
				});

				Button listbutton = (Button) findViewById(R.id.listbutton);
				listbutton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(),
								foodlist.class);
						startActivityForResult(myIntent, 0);
					}
				});

				Button calculatebutton = (Button) findViewById(R.id.calculate);
				calculatebutton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(),
								mealcalculatorActivity.class);
						startActivityForResult(myIntent, 0);
					}
				});
			}
		});

		// /////////////EXERCISE////////////////////////////
		BtExercise.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				BtHome.setImageResource(R.drawable.drop);
				BtMeals.setImageResource(R.drawable.meal);
				BtExercise.setImageResource(R.drawable.exceover);
				BtInsulin.setImageResource(R.drawable.med);
				BtGraph.setImageResource(R.drawable.graph);

				Myexercise.setVisibility(View.VISIBLE);
				Myhome.setVisibility(View.GONE);
				Mymeals.setVisibility(View.GONE);
				Myinsulin.setVisibility(View.GONE);
				Myreports.setVisibility(View.GONE);

				// /PLACE CODE HERE///

				Button AddWorkoutButton = (Button) findViewById(R.id.AddWorkoutButton);
				Button AddMedicationButton = (Button) findViewById(R.id.AddMedicationButton);
				Button SeeMedicine = (Button) findViewById(R.id.bSeeMedication);
				Button DeleteMedicine = (Button) findViewById(R.id.dDeleteMedicineHistory);

				AddWorkoutButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(),
								ExerciseActivity.class);
						startActivityForResult(myIntent, 0);
					}
				});

				AddMedicationButton
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								Intent myIntent = new Intent(v.getContext(),
										MedicineActivity.class);
								startActivityForResult(myIntent, 0);
							}
						});
				SeeMedicine.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(DataEntry.this,
								MedicineHistory.class);
						startActivity(i);

					}
				});
				
				DeleteMedicine.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						MedicineDBAdapter db = new MedicineDBAdapter(DataEntry.this);
						db.openToWrite();
						db.delete_byName(null);
						db.close();
						
					}
				});

			}
		});

		// ////////////INSULIN//////////////////////////////
		BtInsulin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BtHome.setImageResource(R.drawable.drop);
				BtMeals.setImageResource(R.drawable.meal);
				BtExercise.setImageResource(R.drawable.exce);
				BtInsulin.setImageResource(R.drawable.medover);
				BtGraph.setImageResource(R.drawable.graph);

				Myinsulin.setVisibility(View.VISIBLE);
				Myhome.setVisibility(View.GONE);
				Mymeals.setVisibility(View.GONE);
				Myexercise.setVisibility(View.GONE);
				Myreports.setVisibility(View.GONE);

				Button addRecordImageButton = (Button) findViewById(R.id.AddInsulinRecordButton);
				addRecordImageButton
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								Intent myIntent = new Intent(v.getContext(),
										AddInsulinLogActivity.class);
								startActivityForResult(myIntent, 0);
							}
						});

				ListView insulinLogListView = (ListView) findViewById(R.id.InsulinLogListView);

				SimpleCursorAdapter simpleCursorAdapter;
				Cursor cursor;
				InsulinLogDao insulinLogDao = new InsulinLogDao(v.getContext());
				insulinLogDao.openToRead();
				cursor = insulinLogDao.queueAll();

				String[] field = new String[] {
						InsulinLogDao.LOG_TIME_FIELD_NAME,
						InsulinLogDao.DOSAGE_FIELD_NAME };
				int[] viewId = new int[] { R.id.InsulinLogTimeTextView,
						R.id.InsulinDosageTextView };

				simpleCursorAdapter = new SimpleCursorAdapter(v.getContext(),
						R.layout.insulin_log_row, cursor, field, viewId);
				insulinLogListView.setAdapter(simpleCursorAdapter);
			}
		});

		// ////////////REPORTING///////////////////////////
		BtGraph.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				BtHome.setImageResource(R.drawable.drop);
				BtMeals.setImageResource(R.drawable.meal);
				BtExercise.setImageResource(R.drawable.exce);
				BtInsulin.setImageResource(R.drawable.med);
				BtGraph.setImageResource(R.drawable.graphover);

				Myreports.setVisibility(View.VISIBLE);
				Myhome.setVisibility(View.GONE);
				Mymeals.setVisibility(View.GONE);
				Myexercise.setVisibility(View.GONE);
				Myinsulin.setVisibility(View.GONE);

				// /PLACE CODE HERE///

				Button mealButton = (Button) findViewById(R.id.mealbuttonReport);
				mealButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent mealReport = new Intent(v.getContext(),
								mealReportActivity.class);
						startActivityForResult(mealReport, 0);
					}
				});

				Button bloodButton = (Button) findViewById(R.id.bloodbuttonReport);
				bloodButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent bloodReport = new Intent(v.getContext(),
								contactReportActivity.class);
						startActivityForResult(bloodReport, 0);
					}
				});

				Button notesButton = (Button) findViewById(R.id.notesbuttonReport);
				notesButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent exerciseReport = new Intent(v.getContext(),
								exerciseReportActivity.class);
						startActivityForResult(exerciseReport, 0);
					}
				});

			}
		});

	}
}
