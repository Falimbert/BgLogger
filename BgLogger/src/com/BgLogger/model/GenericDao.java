package com.BgLogger.model;
/**
 * @author		Scott Leonard
 * Created:		07/01/2012
 * @editor 	 	Limas Baginta,
 * Modified: 	29/11/2015
 */
import com.BgLogger.activiy.backup.MyBackupAgent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class GenericDao extends SQLiteOpenHelper {
	private SQLiteDatabase sqLiteDatabase;

	public static final String DB_NAME = "BGLOGGER_DATABASE.db";
	private static final int DB_Version = 1;
	private static final CursorFactory CURSOR_FACTORY = null;
	private MyBackupAgent backup;
	private String tableName;
	private String createScript;

	public GenericDao(Context context, String tableName, String createScript) {
		super(context, DB_NAME, CURSOR_FACTORY, DB_Version);

		this.tableName = tableName;
		this.createScript = createScript;

		sqLiteDatabase = getWritableDatabase();
		sqLiteDatabase.execSQL(createScript);
		sqLiteDatabase.close();
	}

	public GenericDao openToRead() throws SQLException {
		sqLiteDatabase = getReadableDatabase();
		return this;
	}

	public GenericDao openToWrite() throws SQLException {
		sqLiteDatabase = getWritableDatabase();
		return this;
	}

	public long insert(String tableName, ContentValues contentValues) {
		long id = sqLiteDatabase.insertOrThrow(tableName, null, contentValues);
		// backup.dataChanged();
		return id;
	}

	public void delete(int id) {
		String deleteSql = "delete from " + tableName + " where _id=" + id;
		sqLiteDatabase.rawQuery(deleteSql, null).moveToFirst();
	}

	public void deleteAll() {
		String deleteSql = "delete from " + tableName;
		sqLiteDatabase.rawQuery(deleteSql, null).moveToFirst();
	}

	public Cursor queueAll(String ordering) {
		Cursor cursor;
		if (ordering == null) {
			cursor = sqLiteDatabase.rawQuery("select rowid _id,* from "
					+ tableName, null);
		} else {
			cursor = sqLiteDatabase.rawQuery("select rowid _id,* from "
					+ tableName + " order by " + ordering, null);
		}
		return cursor;
	}

	public Cursor queueSpecific(String columns[], String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		Cursor cursor;
		cursor = sqLiteDatabase.query(tableName, columns, selection,
				selectionArgs, groupBy, having, orderBy);
		return cursor;

	}

	public void update(String table, ContentValues cv, String whereClause,
			String[] whereArgs) {

		sqLiteDatabase.update(tableName, cv, whereClause, whereArgs);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createScript);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {

	}
}
