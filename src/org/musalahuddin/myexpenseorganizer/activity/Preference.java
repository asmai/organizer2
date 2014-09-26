package org.musalahuddin.myexpenseorganizer.activity;

import org.musalahuddin.myexpenseorganizer.R;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class Preference extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("Preference", "oncreate");
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		setTitle("Settings");
		// get the action bar
		ActionBar actionBar = getActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
