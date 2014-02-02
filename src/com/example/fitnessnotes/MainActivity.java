package com.example.fitnessnotes;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.PorterDuff;
import android.view.*;


public class MainActivity extends Activity {

	private EditText edRep, edWei, ed;
	private int tempInt = 0;
	private DatabaseHelper database;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edRep=(EditText)findViewById(R.id.editTextRepetitions);
		edWei=(EditText)findViewById(R.id.exitTextWeight);
		
	    edRep.setOnTouchListener(new View.OnTouchListener()
	        {
	            public boolean onTouch(View arg0, MotionEvent arg1)
	            {
	            	ed = (EditText) arg0;
	                return false;
	            }
	        });
	    
	    edWei.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
            	ed = (EditText) arg0;
                return false;
            }
        });	
	    
	    
	    database = new DatabaseHelper(this);
	    database.open();

	    List<Serie> values = database.getAllComments();

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	  }

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

		
		
// if either Repetion or Weight is selected (double clicked) -> use repetition
		if (ed == null) {
			Toast.makeText(this, "no field set, using Repetitions", Toast.LENGTH_SHORT).show();
			ed = (EditText) findViewById(R.id.editTextRepetitions);
		}
		
		
		
		if (btn.getTag().toString().equals("0")) {
			ed.setText("0");
			Toast.makeText(this, "set to zero", Toast.LENGTH_SHORT).show();
			
		} else {
			
//			if there the EditText is empty or not integer, then set to zero 
			try {
				tempInt=Integer.parseInt(ed.getText().toString());
			} catch (Exception ex) {
				tempInt =0;
			}
						
//			add the Tag of the button to the current value of the TextEdit
			tempInt=tempInt + Integer.parseInt(btn.getTag().toString());
			ed.setText(Integer.toString(tempInt)); 
		}		
	}

	// This handler reacts on buttons store
	public void HandleClickStore(View arg0) {
	}

}
