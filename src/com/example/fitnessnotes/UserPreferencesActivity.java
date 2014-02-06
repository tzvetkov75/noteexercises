package com.example.fitnessnotes;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserPreferencesActivity extends PreferenceActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  
         getFragmentManager().beginTransaction().replace(android.R.id.content,
         new UserPreferencesFragment()).commit();
    }
}