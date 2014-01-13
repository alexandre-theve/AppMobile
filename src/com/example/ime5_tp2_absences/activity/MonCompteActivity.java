package com.example.ime5_tp2_absences.activity;

import com.example.ime5_tp2_absences.GlobalState;
import com.example.ime5_tp2_absences.R;
import com.example.ime5_tp2_absences.R.id;
import com.example.ime5_tp2_absences.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MonCompteActivity extends Activity {
	private GlobalState state ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compte);
	
		state = (GlobalState) getApplication();
		//test commit and push 
		
		EditText loginEditText = (EditText) findViewById(R.id.compte_edtLogin);
		EditText passEditText = (EditText) findViewById(R.id.compte_edtPasse);
		EditText nomEditText = (EditText) findViewById(R.id.compte_edtNom);
		EditText prenomEditText = (EditText) findViewById(R.id.compte_edtPrenom);
		
		loginEditText.setText(state.getUser().getLogin());
		passEditText.setText(state.getUser().getPasse());
		nomEditText.setText(state.getUser().getNom());
		prenomEditText.setText(state.getUser().getPrenom());
}
}
