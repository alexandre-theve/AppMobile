package com.example.ime5_tp2_absences;

import java.util.ArrayList;

import model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ChoixSeance extends Activity {
	private GlobalState globalState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_seance);
		
		globalState = (GlobalState) getApplication();
		
		Spinner spinner = (Spinner) findViewById(R.id.choixConversation_choixConv);
		
		ArrayList<String> list = getSeances();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}
	
	private ArrayList<String> getSeances(){
		String response = globalState.requete("action=getListeSeances");
		ArrayList<String> seances = new ArrayList<String>();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray seancesJson = json.getJSONArray("seances");
			for(int i = 0; i < seancesJson.length(); i++){
				String jsonSeance = seancesJson.getString(i);
				seances.add(jsonSeance);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return seances;
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
			Intent intent = new Intent(this, Preferences.class);
			
			startActivity(intent);
			break;
		case R.id.action_compte:
			// Ouverture de l'activité compte
			Toast t = Toast
					.makeText(this, "non implémenté", Toast.LENGTH_SHORT);
			t.show();
			break;
		case R.id.action_logout:
			logout();
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void logout(){
		String response = globalState.requete("action=logout");
		Log.i("TP2", "response : " + response);
		JSONObject json;
		try {
			json = new JSONObject(response);
			Boolean connecte = json.getBoolean("connecte");
			if(connecte){
				Toast.makeText(this,"La deconnexion a échouée! " + json.getString("feedback"), Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(this,"Deconnecté", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
