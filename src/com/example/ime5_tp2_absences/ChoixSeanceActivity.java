package com.example.ime5_tp2_absences;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Seances;
import model.Signature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import GesturePanel.Point;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChoixSeanceActivity extends Activity implements OnClickListener {
	private GlobalState globalState;
	public static int ACTICITY_REQUEST_CODE = 42;
	Object signature;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_seance);
		
		globalState = (GlobalState) getApplication();
		
		Spinner spinner = (Spinner) findViewById(R.id.choixConversation_choixConv);
		Button button = (Button) findViewById(R.id.choixConversation_btnOK);
		button.setOnClickListener(this);
		TextView titre = (TextView) findViewById(R.id.choixSeance_titre);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		titre.setText(getString(R.string.seances_titre) + " " + format.format(new Date()));
		
		ArrayList<Seances> list = getSeances();
		
		ArrayAdapter<Seances> dataAdapter = new ArrayAdapter<Seances>(this,
				android.R.layout.simple_dropdown_item_1line, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

	}
	
	private ArrayList<Seances> getSeances(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String response = globalState.requete("action=getListeSeances&date="+format.format(new Date()));
		
		System.out.println("response : " + response);
		
		ArrayList<Seances> seances = new ArrayList<Seances>();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray seancesJson = json.getJSONArray("seances");
			for(int i = 0; i < seancesJson.length(); i++){
				JSONObject jsonSeance = seancesJson.getJSONObject(i);
				try {
					Seances s = new Seances(jsonSeance);
					
					System.out.println("getSeances : " + s.getId());
					
					seances.add(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == ACTICITY_REQUEST_CODE && resultCode == RESULT_OK)
    		signature = new Signature((ArrayList<Point>) data.getSerializableExtra("signature"));
    	
    	System.out.println("onActivityResult : " + signature);
    	super.onActivityResult(requestCode, resultCode, data);
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

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, SeanceActivity.class);
		
		Spinner spinner = (Spinner) findViewById(R.id.choixConversation_choixConv);
		intent.putExtra("seance", (Seances) spinner.getSelectedItem());
		
		startActivity(intent);
	}
}
