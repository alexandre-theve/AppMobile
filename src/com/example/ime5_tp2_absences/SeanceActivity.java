package com.example.ime5_tp2_absences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Seances;
import model.Users;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class SeanceActivity extends Activity {
	private Seances seance;
	private ListView elevesListView;
	private ArrayList<Users> eleves;
	private GlobalState globalState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seance);
		
		globalState = (GlobalState) getApplication();
		
		seance = (Seances) getIntent().getSerializableExtra("seance");
		
		if(seance == null)
			seance = new Seances();
		
		fillUI();
	}
	
	private void fillUI(){
		TextView titre = (TextView) findViewById(R.id.seance_titre);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		titre.setText(getString(R.string.seance_titre) + seance.getNom() + " - " + format.format(seance.getDtDebut()));
		
		elevesListView = (ListView) findViewById(R.id.seance_liste);
		eleves = getEleves();
		
		UserAdapter adapter = new UserAdapter(eleves, LayoutInflater.from(this));
		
	    //On attribut à notre listView l'adapter que l'on vient de créer
		elevesListView.setAdapter(adapter);
	}
	
	private ArrayList<Users> getEleves(){
		String response = globalState.requete("action=getListeEleves&idSeance="+seance.getId());
		
		System.out.println("response : " + response);
		
		ArrayList<Users> eleves = new ArrayList<Users>();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray elevesJson = json.getJSONArray("eleves");
			for(int i = 0; i < elevesJson.length(); i++){
				JSONObject jsonEleve = elevesJson.getJSONObject(i);
				try {
					Users user = new Users(jsonEleve);
					
					System.out.println("getEleves : " + user);
					
					eleves.add(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eleves;
	}
}
