package com.example.ime5_tp2_absences;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MonCompte extends Activity {
	private GlobalState state ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compte);
	
		state = (GlobalState) getApplication();
		
		EditText loginEditText = (EditText) findViewById(R.id.compte_edtLogin);
		EditText passEditText = (EditText) findViewById(R.id.compte_edtPasse);
		EditText nomEditText = (EditText) findViewById(R.id.compte_edtNom);
		EditText prenomEditText = (EditText) findViewById(R.id.compte_edtPrenom);
		
		loginEditText.setText(state.getUser().getLogin());
		passEditText.setText(state.getUser().getPass());
		nomEditText.setText(state.getUser().getNom());
		prenomEditText.setText(state.getUser().getPrenom());
}
}
