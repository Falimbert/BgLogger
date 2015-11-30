package com.BgLogger;

import com.BgLogger.R;
import com.BgLogger.activity.glucose.AddGlucoseLogActivity;
import com.BgLogger.activity.insulin.AddInsulinLogActivity;
import com.BgLogger.model.glucose.BloodGlucoseLogDao;
import com.BgLogger.model.insulin.InsulinLogDao;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
 
@SuppressLint("NewApi") public class DataEntryFragment extends Fragment{
     
	View rootView;
     
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        rootView = inflater.inflate(R.layout.data_entry, container, false);
     //   setContentView(R.layout.data_entry);
		addListenerOnButton();
		ViewStub Myhome = (ViewStub) rootView.findViewById(R.id.homeView);
		Myhome.setVisibility(View.VISIBLE);

		Button addRecordImageButton = (Button) rootView.findViewById(R.id.AddRecordButton);
		addRecordImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(),
						AddGlucoseLogActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
        return rootView;
    }
    
    
    public void addListenerOnButton() {
		final ImageButton BtHome = (ImageButton) rootView.findViewById(R.id.imageButton1);
		final ImageButton BtMeals = (ImageButton) rootView.findViewById(R.id.imageButton2);
		final ImageButton BtExercise = (ImageButton) rootView.findViewById(R.id.imageButton3);
		final ImageButton BtInsulin = (ImageButton) rootView.findViewById(R.id.imageButton4);
		final ImageButton BtGraph = (ImageButton) rootView.findViewById(R.id.imageButton5);

		final ViewStub Myhome = (ViewStub) rootView.findViewById(R.id.homeView);
		final ViewStub Mymeals = (ViewStub) rootView.findViewById(R.id.mealsView);
		final ViewStub Myexercise = (ViewStub) rootView.findViewById(R.id.exerciseView);
		final ViewStub Myinsulin = (ViewStub) rootView.findViewById(R.id.insulineView);
		final ViewStub Myreports = (ViewStub) rootView.findViewById(R.id.reportView);

		// //////////HOME////////////////////////////////////
		BtHome.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
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

				Button addRecordImageButton = (Button) rootView.findViewById(R.id.AddRecordButton);
				addRecordImageButton
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								Intent myIntent = new Intent(v.getContext(),
										AddGlucoseLogActivity.class);
								startActivityForResult(myIntent, 0);
							}
						});
				Button deleteAllImageButton = (Button) rootView.findViewById(R.id.DeleteAllButton);
				deleteAllImageButton
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								bloodGlucoseLogDao.deleteAll();
							}
						});

				ListView glucoseLogListView = (ListView) rootView.findViewById(R.id.glucoseLogListView);

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

				Button logbutton = (Button) rootView.findViewById(R.id.log);
				logbutton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(),
								FoodlogActivity.class);
						startActivityForResult(myIntent, 0);
					}
				});

				Button listbutton = (Button) rootView.findViewById(R.id.listbutton);
				listbutton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(),
								foodlist.class);
						startActivityForResult(myIntent, 0);
					}
				});

				Button calculatebutton = (Button) rootView.findViewById(R.id.calculate);
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

				Button AddWorkoutButton = (Button) rootView.findViewById(R.id.AddWorkoutButton);
				Button AddMedicationButton = (Button) rootView.findViewById(R.id.AddMedicationButton);
				Button SeeMedicine = (Button) rootView.findViewById(R.id.bSeeMedication);
				Button DeleteMedicine = (Button) rootView.findViewById(R.id.dDeleteMedicineHistory);

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
						Intent i = new Intent(getActivity(),
								MedicineHistory.class);
						startActivity(i);

					}
				});
				
				DeleteMedicine.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						MedicineDBAdapter db = new MedicineDBAdapter(getActivity());
						db.openToWrite();
						db.delete_byName(null);
						db.close();
						
					}
				});

			}
		});

		// ////////////INSULIN//////////////////////////////
		BtInsulin.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
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

				Button addRecordImageButton = (Button) rootView.findViewById(R.id.AddInsulinRecordButton);
				addRecordImageButton
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								Intent myIntent = new Intent(v.getContext(),
										AddInsulinLogActivity.class);
								startActivityForResult(myIntent, 0);
							}
						});

				ListView insulinLogListView = (ListView) rootView.findViewById(R.id.InsulinLogListView);

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

				Button mealButton = (Button) rootView.findViewById(R.id.mealbuttonReport);
				mealButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent mealReport = new Intent(v.getContext(),
								mealReportActivity.class);
						startActivityForResult(mealReport, 0);
					}
				});

				Button bloodButton = (Button) rootView.findViewById(R.id.bloodbuttonReport);
				bloodButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent bloodReport = new Intent(v.getContext(),
								contactReportActivity.class);
						startActivityForResult(bloodReport, 0);
					}
				});

				Button notesButton = (Button) rootView.findViewById(R.id.notesbuttonReport);
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
