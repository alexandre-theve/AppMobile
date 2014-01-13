package com.example.ime5_tp2_absences.activity;

import com.example.ime5_tp2_absences.R;
import com.example.ime5_tp2_absences.R.xml;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
