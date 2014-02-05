package com.example.fitnessnotes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
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

		database = new DatabaseHelper(this);
		database.open();
		updateHistory();
		
//		set on click event 

		listviewHistory.setOnItemClickListener (new AdapterView.OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			    // When clicked, show a toast with the TextView text
				Pattern pattern = Pattern.compile(".*:   (.*?):   (.*)x(.*)");
				Matcher matcher = pattern.matcher(((TextView) view).getText());
				
				if (matcher.find())
				{
				    System.out.println(matcher.group(1));
				}
				
				edName.setText(matcher.group(1));
				edRep.setText(matcher.group(2));
				edWei.setText(matcher.group(3));
				
//			    Toast.makeText(getApplicationContext(),
//			    		matcher.group(2), Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}

	private void updateHistory() {

		List<String> values = database.getAllSeries();

		// use the SimpleCursorAdapter to show the
		// elements in a ListView

		adapter = new ArrayAdapter<String>(this,
				R.layout.my_layout_history, values);
		
//				android.R.layout.simple_list_item_1, values);
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

		Serie serie = new Serie(edName.getText().toString(), Integer.parseInt(edRep.getText().toString()),Integer.parseInt(edWei.getText().toString()));  
		
		database.storeSerie(serie);
		updateHistory();
		edName.setText("");
		edWei.setText("");
		edRep.setText("");
	}

}
