package com.example.fitnessnotes;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SeriesDB";
	public static final String TABLE_NAME = "series";
	
	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME , null, DATABASE_VERSION);

	}
	
	 public MySQLiteHelper(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	 }
	 

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// SQL statement to create book table
        String CREATE_SERIES_TABLE = "CREATE TABLE "+ TABLE_NAME+ " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        		"date int," +
                "name TEXT, "+
                "repetition INTEGER," + 
                "weight INTEGER," +
                "trainingId INTEGER)";
 
        // create books table
        db.execSQL(CREATE_SERIES_TABLE);
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
//		Drop the table 
		   db.execSQL("DROP TABLE IF EXISTS ");
		   onCreate(db);
		
	}
	
	

}
