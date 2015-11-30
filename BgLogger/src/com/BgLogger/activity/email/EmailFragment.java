package com.BgLogger.activity.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.BgLogger.MedicineDBAdapter;
import com.BgLogger.R;
import com.BgLogger.model.glucose.BloodGlucoseLogDao;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EmailFragment extends Fragment {

	private BloodGlucoseLogDao data;
	private MedicineDBAdapter medicineData;
	private ListView listView;
	private ArrayList<String> email = new ArrayList<String>();
	private ArrayList<String> validEmails = new ArrayList<String>();
	private Cursor allResultsCursor = null;
	private Cursor pdfResultsCursor = null;
	private Cursor medication = null;
	private static Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
	private static Font medFont = new Font(Font.TIMES_ROMAN, 14,
			Font.BOLDITALIC);
	private View view;
	private ArrayList<HashMap<String, String>> takenMedicine = new ArrayList<HashMap<String, String>>();
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm aa");
	private String date, test;

	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.common_list, container, false);
		TextView tv = (TextView)rootView.findViewById(R.id.tvCommontv);
		tv.setText("Select the test below to email the corresponding details.");

		// get the list view reference
		listView = (ListView) rootView.findViewById(android.R.id.list);

		// create an array containing the column names of the database
		String[] names = { BloodGlucoseLogDao.TEST_TYPE_FIELD_NAME,
				BloodGlucoseLogDao.LOG_TIME_FIELD_NAME };

		// create an array containing the id's of the text views where test list
		// is displayed
		int[] array = { R.id.tvTest, R.id.tvDate };

		data = new BloodGlucoseLogDao(getActivity());
		data.openToRead();
		allResultsCursor = data.queueAll();

		// create an adapter with data in the returned cursor
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_item_each, allResultsCursor, names, array);

		// add this adapter to the list view
		listView.setAdapter(adapter);

		data.close();

		// on list item selected
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, final View view1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				view = view1;
				// pop up an alert box to get the email address
				final Dialog alert = new Dialog(getActivity());

				alert.setContentView(R.layout.email_alert);
				final TextView message = (TextView) alert
						.findViewById(R.id.tvAlertMessage);
				final EditText emailAddress = (EditText) alert
						.findViewById(R.id.etEmailAddress);
				Button ok = (Button) alert.findViewById(R.id.bEmailOk);
				Button contacts = (Button) alert
						.findViewById(R.id.bEmailContacts);

				alert.setTitle("Email Address");
				message.setText("Please enter the email address below");

				alert.show();

				ok.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						email.add(emailAddress.getText().toString());
						// check for the validity of email address
						isValidEmail();
						if (validEmails.size() != 0) {
							alert.dismiss();
							emailSetup(view);
						} else if (validEmails.size() == 0) {
							message.setText("Enter a valid email address!");
							message.setTextColor(Color.RED);

						}

					}

				});

				contacts.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						alert.dismiss();
						Intent i = new Intent(getActivity(),
								GetEmailAddress.class);
						startActivityForResult(i, 1);

					}

				});

			}
		});
		return rootView;

	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == -1) {
			email = data.getStringArrayListExtra("emailArray");
			// check for the validity of email address
			isValidEmail();
			if (validEmails.size() == 0) {
				Toast.makeText(getActivity(),
						"No valid email address was found", Toast.LENGTH_LONG)
						.show();
			} else {
				emailSetup(view);
			}

		}
	}

	private void emailSetup(View v) {
		// get the test type of the selected test result
		test = ((TextView) v.findViewById(R.id.tvTest)).getText().toString();

		// get the date of the selected test result
		date = ((TextView) v.findViewById(R.id.tvDate)).getText().toString();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

		String orderBy = null;
		String having = null;
		String groupBy = null;
		String[] columns = { BloodGlucoseLogDao.READING_FIELD_NAME,
				BloodGlucoseLogDao.MEASUREMENT_UNIT_FIELD_NAME };
		String selection = BloodGlucoseLogDao.TEST_TYPE_FIELD_NAME
				+ " = ? and " + BloodGlucoseLogDao.LOG_TIME_FIELD_NAME + " = ?";
		String[] selectionArgs = { test, date };

		data.openToRead();
		// get the cursor containing the data related to the selected
		// list item
		pdfResultsCursor = data.queueSpecific(columns, selection,
				selectionArgs, groupBy, having, orderBy);
		getMedication();

		createPdf();
		openPdf();
		data.close();
		medicineData.close();
		// set up a new intent to send the created pdf file
		Intent i = new Intent(getActivity(), SendEmail.class);
		i.putExtra("test", test);
		i.putExtra("date", date);
		i.putStringArrayListExtra("emails", validEmails);
		startActivity(i);

	}

	// code to create a test result pdf
	public void createPdf() {

		Document doc = null;
		// get the column index of the reading
		int iReading = pdfResultsCursor
				.getColumnIndex(BloodGlucoseLogDao.READING_FIELD_NAME);

		// get the column index of the measurement unit
		int iUnit = pdfResultsCursor
				.getColumnIndex(BloodGlucoseLogDao.MEASUREMENT_UNIT_FIELD_NAME);

		try {
			// create a new document

			doc = new Document(PageSize.A5, 20, 20, 20, 20);

			// get the path of the pdf folder on the external storage

			String path = Environment.getExternalStorageDirectory().getPath()
					+ "/pdf";

			File dir = new File(path);

			// make the folder if it does not exist
			if (!dir.exists())
				dir.mkdirs();

			// log message
			Log.d("PDFCreator", "PDF Path: " + path);

			// create a new file
			File file = new File(dir, test + ".pdf");
			FileOutputStream fOut = new FileOutputStream(file);
			PdfWriter.getInstance(doc, fOut);

			// open the document
			doc.open();

			// first add metadata to the pdf
			doc.addAuthor("Hansika Hewamalage");
			doc.addCreator("Hansika Hewamalage");

			// add a title for the pdf
			Paragraph title = new Paragraph(test + " test done on " + date,
					catFont);
			title.setAlignment(Paragraph.ALIGN_CENTER);
			doc.add(title);
			// keep an empty line
			doc.add(Chunk.NEWLINE);
			pdfResultsCursor.moveToFirst();
			// add content for the pdf

			Paragraph p1 = new Paragraph("Test Result: "
					+ pdfResultsCursor.getString(iReading) + " "
					+ pdfResultsCursor.getString(iUnit));
			Font paraFont = new Font(Font.COURIER);
			p1.setAlignment(Paragraph.ALIGN_LEFT);
			p1.setFont(paraFont);

			// add paragraph to document
			doc.add(p1);

			doc.add(Chunk.NEWLINE);

			if (takenMedicine.size() > 0) {
				Paragraph medTitle = new Paragraph("Medicine taken from "
						+ takenMedicine.get(takenMedicine.size() - 1).get(
								"date") + " to "
						+ takenMedicine.get(0).get("date"), medFont);
				doc.add(medTitle);
				doc.add(Chunk.NEWLINE);
				Paragraph medicineDetails;
				for (int i = 0; i < takenMedicine.size(); i++) {
					medicineDetails = new Paragraph(takenMedicine.get(i).get(
							"date")
							+ " : "
							+ takenMedicine.get(i).get("name")
							+ " "
							+ takenMedicine.get(i).get("dosage")
							+ " "
							+ takenMedicine.get(i).get("unit"));
					doc.add(medicineDetails);
				}

			}

		} catch (DocumentException de) {
			Log.e("PDFCreator", "DocumentException:" + de);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			doc.close();
		}
	}

	// code to open the created pdf file
	public void openPdf() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/pdf";

		File file = new File(path, test + ".pdf");
		// set the file type to pdf
		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		startActivity(intent);
	}

	// code to retrieve the medicine taken before the test.
	public void getMedication() {
		Cursor medication;
		medicineData = new MedicineDBAdapter(getActivity());
		medicineData.openToRead();

		Calendar d = Calendar.getInstance();
		try {
			d.setTime(sdf.parse(date));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		d.add(Calendar.DAY_OF_MONTH, -10);
		String[] columns = { MedicineDBAdapter.KEY_MedicationDATE,
				MedicineDBAdapter.KEY_MedicationName,
				MedicineDBAdapter.KEY_MedicationDosage,
				MedicineDBAdapter.KEY_MedicationUnit };

		String selectionArgs[] = { sdf.format(d.getTime()), date };
		String selection = MedicineDBAdapter.KEY_MedicationDATE
				+ " BETWEEN ? AND ?";
		medication = medicineData.queueSpecific(columns, selection,
				selectionArgs, null, null, MedicineDBAdapter.KEY_MedicationDATE
						+ " desc", null);
		// int count = medication.getCount();

		// int count =
		// medication.getColumnIndex(MedicineDBAdapter.KEY_MedicationDosage);
		for (medication.moveToFirst(); !medication.isAfterLast(); medication
				.moveToNext()) {
			HashMap<String, String> s = new HashMap<String, String>();

			s.put("unit", medication.getString(medication
					.getColumnIndex(MedicineDBAdapter.KEY_MedicationUnit)));
			s.put("date", medication.getString(medication
					.getColumnIndex(MedicineDBAdapter.KEY_MedicationDATE)));
			s.put("name", medication.getString(medication
					.getColumnIndex(MedicineDBAdapter.KEY_MedicationName)));
			s.put("dosage", medication.getString(medication
					.getColumnIndex(MedicineDBAdapter.KEY_MedicationDosage)));

			takenMedicine.add(s);
		}

	}

	private void isValidEmail() {
		for (int i = 0; i < email.size(); i++) {
			if (TextUtils.isEmpty(email.get(i))) {
				continue;
			} else if (android.util.Patterns.EMAIL_ADDRESS
					.matcher(email.get(i)).matches()) {
				validEmails.add(email.get(i));
			}
		}
	}
}