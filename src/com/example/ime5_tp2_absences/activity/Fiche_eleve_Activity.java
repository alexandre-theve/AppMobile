package com.example.ime5_tp2_absences.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import model.Data;

import model.Seances;

import com.example.ime5_tp2_absences.GetDataAsyncTask;
import com.example.ime5_tp2_absences.GetDataEleve;
import com.example.ime5_tp2_absences.GlobalState;
import com.example.ime5_tp2_absences.LogoutAsyncTask;
import com.example.ime5_tp2_absences.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fiche_eleve_Activity extends Activity{
	private GlobalState globalState;
	private GetDataEleve eleveAsyncTask;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fiche_eleve);
		
		globalState = (GlobalState) getApplication();

		Data eleve = (Data) getIntent().getSerializableExtra("eleve");
	  // Toast.makeText(this, eleve.getEleve().getNom(), Toast.LENGTH_SHORT).show();

		EditText nomEditText = (EditText) findViewById(R.id.nom_eleve);
		EditText prenomEditText = (EditText) findViewById(R.id.prenom_eleve); 
		nomEditText.setText(eleve.getEleve().getNom());
		prenomEditText.setText(eleve.getEleve().getPrenom());
		
		eleveAsyncTask = new GetDataEleve(globalState, eleve);
		eleveAsyncTask.execute();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(this, PreferencesActivity.class);
			startActivity(intent);
			break;
		case R.id.action_compte:
			// Ouverture de l'activité compte
			Intent intent2 = new Intent(this, MonCompteActivity.class);
			startActivity(intent2);

			break;
		case R.id.action_logout:
			globalState.logout();
			break;
		}
		return super.onOptionsItemSelected(item);
		}
}

