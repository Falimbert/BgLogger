package com.BgLogger.activiy.backup;

import java.io.IOException;

import com.BgLogger.ExerciseDBAdapter;
import com.BgLogger.FoodDBAdapter;
import com.BgLogger.ReportDataManipulator;
import com.BgLogger.ReportDataManipulatorNotes;
import com.BgLogger.model.GenericDao;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.os.ParcelFileDescriptor;

public class MyBackupAgent extends BackupAgentHelper {

	// names of the database files
	private static final String db1 = "../databases/" + GenericDao.DB_NAME;
	private static final String db2 = "../databases/"
			+ ExerciseDBAdapter.DB_Name;
	private static final String db3 = "../databases/" + FoodDBAdapter.DB_Name;
	private static final String db4 = "../databases/"
			+ ReportDataManipulator.DATABASE_NAME;
	private static final String db5 = "../databases/"
			+ ReportDataManipulatorNotes.DATABASE_NAME;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		FileBackupHelper helper1 = new FileBackupHelper(this, db1);
		addHelper(db1, helper1);

		FileBackupHelper helper2 = new FileBackupHelper(this, db2);
		addHelper(db2, helper2);

		FileBackupHelper helper3 = new FileBackupHelper(this, db3);
		addHelper(db3, helper3);

		FileBackupHelper helper4 = new FileBackupHelper(this, db4);
		addHelper(db4, helper4);

		FileBackupHelper helper5 = new FileBackupHelper(this, db5);
		addHelper(db5, helper5);

	}

	@Override
	public void onRestore(BackupDataInput data, int appVersionCode,
			ParcelFileDescriptor newState) throws IOException {
		// TODO Auto-generated method stub
		super.onRestore(data, appVersionCode, newState);
	}

	@Override
	public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
			ParcelFileDescriptor newState) throws IOException {
		// TODO Auto-generated method stub
		
	/*	synchronized (HostDatabase.dbLock) {
            super.onBackup(oldState, data, newState);
    }*/

		super.onBackup(oldState, data, newState);
	}

}
