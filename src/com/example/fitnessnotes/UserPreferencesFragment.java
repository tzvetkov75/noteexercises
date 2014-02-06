package com.example.fitnessnotes;

import android.os.Bundle;
import android.preference.PreferenceFragment;



public class UserPreferencesFragment extends PreferenceFragment {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        addPreferencesFromResource(R.xml.my_preferences);
    }
    
}
