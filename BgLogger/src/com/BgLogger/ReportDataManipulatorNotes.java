
package com.BgLogger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.List;

public class ReportDataManipulatorNotes
{
    public static final  String DATABASE_NAME = "mydatabaseNotes.db";
    private static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "notestable";
    private static Context context;
    static SQLiteDatabase db;

    private SQLiteStatement insertStmt;
       
    private static final String INSERT = "insert into " + TABLE_NAME + " (note) values (?)";
    public ReportDataManipulatorNotes(Context context) {
    	ReportDataManipulatorNotes.context = context;
        OpenHelper openHelper = new OpenHelper(ReportDataManipulatorNotes.context);
        ReportDataManipulatorNotes.db = openHelper.getWritableDatabase();
        this.insertStmt = ReportDataManipulatorNotes.db.compileStatement(INSERT);
    }
    public long insert(String note) {
       
        this.insertStmt.bindString(1, note);
       
        return this.insertStmt.executeInsert();
    }

    public List<String[]> selectAll()
    {
        List<String[]> list = new ArrayList<String[]>();
        Cursor cursor = db.query(TABLE_NAME, new String[] { "id", "note", }, null, null, null, null, "note asc");
        int x=0;
        if (cursor.moveToFirst()) {
           do {
                String[] b1=new String[]{cursor.getString(0),cursor.getString(1)};
                list.add(b1);
                x=x+1;
           } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
           cursor.close();
        }
        cursor.close();
        return list;
   }

   private static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
             super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
             db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY, note TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
             db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
             onCreate(db);
        }
   }
}
