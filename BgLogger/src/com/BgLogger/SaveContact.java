package com.BgLogger;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaveContact extends ActionBarActivity implements OnClickListener {
	private ReportDataManipulator dh;
	static final int DIALOG_ID = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save);
		title(getString(R.string.new_medical_contact));
		View add = findViewById(R.id.Button01add);
		add.setOnClickListener(this);

		Button buttonsaveback = (Button) findViewById(R.id.Button01home);
		buttonsaveback.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent conchomebackintent = new Intent();
				setResult(RESULT_OK, conchomebackintent);
				finish();
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Button01home:
			Intent i = new Intent(this, contactReportActivity.class);
			startActivity(i);
			break;
		case R.id.Button01add:
			View editText1 = (EditText) findViewById(R.id.name);
			View editText2 = (EditText) findViewById(R.id.number);
			View editText3 = (EditText) findViewById(R.id.skypeId);
			View editText4 = (EditText) findViewById(R.id.address);
			String myEditText1 = ((TextView) editText1).getText().toString();
			String myEditText2 = ((TextView) editText2).getText().toString();
			String myEditText3 = ((TextView) editText3).getText().toString();
			String myEditText4 = ((TextView) editText4).getText().toString();

			if (!((myEditText1.equals("")) || (myEditText2.equals("")))) {
				this.dh = new ReportDataManipulator(this);
				this.dh.insert(myEditText1, myEditText2, myEditText3,
						myEditText4);
				showDialog(DIALOG_ID);
			}

			break;
		}
	}

	protected final Dialog onCreateDialog(final int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_ID:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Information saved successfully! Add Another Info?")
					.setCancelable(false)
					.setPositiveButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									SaveContact.this.finish();
								}
							})
					.setNegativeButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			dialog = alert;
			break;

		default:
		}
		return dialog;
	}
}