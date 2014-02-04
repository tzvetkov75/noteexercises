package com.example.fitnessnotes;

import java.sql.Date;
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

	public List<Serie> getAllSeries() {

		List<Serie> series = new ArrayList<Serie>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, ALL_COLUMNS,
				null, null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			Serie serie = cursorToSerie(cursor);
			series.add(serie);
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

	private Serie cursorToSerie(Cursor cursor) {

		Serie serie = new Serie();
		
		Date date= new Date(cursor.getLong(0));
		
		serie.setDate(date);
		serie.setName(cursor.getString(1));
		serie.setRepetition(cursor.getInt(2));
		serie.setWeight(cursor.getInt(3));

		return serie;
	}

}
