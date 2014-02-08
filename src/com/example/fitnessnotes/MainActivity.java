package com.example.fitnessnotes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.PorterDuff;
import android.view.*;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends Activity {

	private EditText edRep, edWei, edName, ed;
	private int tempInt = 0;
	private DatabaseHelper database;
	private ListView listviewHistory;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edName = (EditText) findViewById(R.id.editExerciese);
		edRep = (EditText) findViewById(R.id.editTextRepetitions);
		edWei = (EditText) findViewById(R.id.exitTextWeight);
		listviewHistory = (ListView) findViewById(R.id.listViewHistory);

		edRep.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				ed = (EditText) arg0;
				return false;
			}
		});

		edWei.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				ed = (EditText) arg0;
				return false;
			}
		});

//		This listener hides the soft keybord when there is not focus on EditeText Excercise Name  
		edName.setOnFocusChangeListener( new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				if(v.getId() == R.id.editExerciese && !hasFocus) {

		            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);			
			}
		}
		});
		
		database = new DatabaseHelper(this);
		database.open();
		updateHistory();

		listviewHistory
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					// set on click event to pre load values
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// When clicked, show a toast with the TextView text
						Pattern pattern = Pattern
								.compile(".*:   (.*?):   (.*)x(.*)");
						Matcher matcher = pattern.matcher(((TextView) view)
								.getText());

						if (matcher.find()) {
							System.out.println(matcher.group(1));
						}

						edName.setText(matcher.group(1));
						edRep.setText(matcher.group(2));
						edWei.setText(matcher.group(3));

						Toast.makeText(getApplicationContext(),
								"values pre loaded", Toast.LENGTH_SHORT).show();
					}
				});

		// set on long click event to delete
		listviewHistory
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {

						deleteEntry(position, ((TextView) view).getText()
								.toString());

						return true;
					}
				});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_settings:
			Intent i = new Intent(this, UserPreferencesActivity.class);
			startActivity(i);
			break;

		case R.id.action_export:

			SharedPreferences _sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			String nameFile = _sharedPreferences.getString("FileCSV",
					"exportedFitnessNotes.csv");
			exportToCSV(nameFile);

			break;
		}

		return true;
	}

	private void deleteEntry(final int position, String text) {

		new AlertDialog.Builder(this)
				.setTitle("Delete entry")
				.setMessage(
						"Are you sure to delete #" + position + "(" + text
								+ ")this entry?")

				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// continue with delete

								database.deleteEntry(position);
								updateHistory();

								Toast.makeText(getApplicationContext(),
										"entry deleted", Toast.LENGTH_SHORT)
										.show();

							}
						})

				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
							}
						}).show();

	}

	private void updateHistory() {

		List<String> values = database.getAllSeries();

		// use the SimpleCursorAdapter to show the
		// elements in a ListView

		adapter = new ArrayAdapter<String>(this, R.layout.my_layout_history,
				values);

		// android.R.layout.simple_list_item_1, values);
		listviewHistory.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// This handler reacts on buttons 0, +2, +5, +10
	public void HandleClickAddButton(View arg0) {
		Button btn = (Button) arg0;

		// if either Repetion or Weight is selected (double clicked) -> use
		// repetition
		if (ed == null) {
			Toast.makeText(this, "no field set, using Repetitions",
					Toast.LENGTH_SHORT).show();
			ed = (EditText) findViewById(R.id.editTextRepetitions);
		}

		if (btn.getTag().toString().equals("0")) {
			ed.setText("0");
			Toast.makeText(this, "set to zero", Toast.LENGTH_SHORT).show();

		} else {

			// if there the EditText is empty or not integer, then set to zero
			try {
				tempInt = Integer.parseInt(ed.getText().toString());
			} catch (Exception ex) {
				tempInt = 0;
			}

			// add the Tag of the button to the current value of the TextEdit
			tempInt = tempInt + Integer.parseInt(btn.getTag().toString());
			ed.setText(Integer.toString(tempInt));
		}

	}

	// This handler reacts on buttons store
	public void HandleClickStore(View arg0) {

		if (!TextUtils.isEmpty(edName.getText().toString())) {
	
			if (!TextUtils.isEmpty(edRep.getText().toString())) {
				edRep.setText("0");
			}
			
			if (!TextUtils.isEmpty(edWei.getText().toString())) {
				edWei.setText("0");
			}
			
			Serie serie = new Serie(edName.getText().toString(),
					Integer.parseInt(edRep.getText().toString()),
					Integer.parseInt(edWei.getText().toString()));

			database.storeSerie(serie);
			updateHistory();
			edName.setText("");
			edWei.setText("");
			edRep.setText("");
			
		}

	}

	public void exportToCSV(String fileName) {
		// Export the DB to CSV file

		if (isExternalStorageWritable()) {

			try {

				File root = android.os.Environment
						.getExternalStorageDirectory();
				File dir = new File(root.getAbsolutePath() + "/download");
				dir.mkdirs();
				File file = new File(dir, fileName);
				FileOutputStream fops = new FileOutputStream(file);

				database.exportToCSV(fops);

				fops.close();
				Toast.makeText(
						getApplicationContext(),
						"Saved successfully into External Storage \n(/downloads/"
								+ fileName + ")", Toast.LENGTH_SHORT).show();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"External Storage not accessable", Toast.LENGTH_LONG)
					.show();
		}

	}

	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

}
