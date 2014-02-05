package com.example.fitnessnotes;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

// This class provides a general API to add series in the DB 
public class DatabaseHelper {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private final static String[] ALL_COLUMNS = { "date", "name",
			"repetition", "weight", "trainingId" };

	public DatabaseHelper(Context context) {

		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<String> getAllSeries() {

		List<String> series = new ArrayList<String>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, ALL_COLUMNS,
				null, null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			String serie = cursorToText(cursor);
			series.add(0, serie);
			cursor.moveToNext();
		}

		// make sure to close the cursor
		cursor.close();
		return series;
	}
	
	 public void storeSerie(Serie serie) {
		 
		    ContentValues values = new ContentValues();
		    
		    values.put("date",serie.getDate().getTime());
		    values.put("name", serie.getName());
		    values.put("repetition", serie.getRepetition());
		    values.put("weight", serie.getWeight());
    
	        long insertId = database.insert(MySQLiteHelper.TABLE_NAME , null,  values);
	        
		  }

	private String cursorToText(Cursor cursor) {

		String serie = new String();
		
//		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm E dd/MM/yy");
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm E ");
		Date date= new Date(cursor.getLong(0));
		String sDate= sdf.format(date); 
				
		serie = sDate + "   " + cursor.getString(1) + ":  "+ cursor.getString(2)+"x"+cursor.getString(3) +"";
		
		return serie;
	}

}
