package com.example.ime5_tp2_absences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ime5_tp2_absences.DataAdapter.DataAdapterSelected;

import model.Data;
import model.Seances;
import model.Signature;
import model.Users;
import GesturePanel.Point;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SeanceActivity extends Activity implements DataAdapterSelected {
	private Seances seance;
	private ListView elevesListView;
	private ArrayList<Data> eleves;
	private GlobalState globalState;

	public static int ACTICITY_REQUEST_CODE = 42;
	
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
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == ACTICITY_REQUEST_CODE && resultCode == RESULT_OK) {
    		//signature = new Signature((ArrayList<Point>) data.getSerializableExtra("signature"));
    		
    		Data eleve = (Data) data.getSerializableExtra("data");
    		System.out.println("onActivityResult : " + eleve);
    		
    		
    		Data editedEleve = eleves.get(eleves.indexOf(eleve));
    		editedEleve.setSignature(eleve.getSignature());
    		editedEleve.setBoolPresence(true);
    		editedEleve.setBoolRetard(eleve.getBoolRetard());
    		
    		((BaseAdapter)elevesListView.getAdapter()).notifyDataSetChanged();
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
	
	private void fillUI(){
		TextView titre = (TextView) findViewById(R.id.seance_titre);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		titre.setText(getString(R.string.seance_titre) + seance.getNom() + " - " + format.format(seance.getDtDebut()));
		
		elevesListView = (ListView) findViewById(R.id.seance_liste);
		eleves = getEleves();
		
		DataAdapter adapter = new DataAdapter(this, eleves, LayoutInflater.from(this));
		adapter.setOnDataAdapterSelected(this);
	    //On attribut à notre listView l'adapter que l'on vient de créer
		elevesListView.setAdapter(adapter);
	}
	
	private ArrayList<Data> getEleves(){
		String response = globalState.requete("action=getListeEleves&idSeance="+seance.getId());
		
		System.out.println("response : " + response);
		
		ArrayList<Data> eleves = new ArrayList<Data>();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray elevesJson = json.getJSONArray("eleves");
			for(int i = 0; i < elevesJson.length(); i++){
				JSONObject jsonEleve = elevesJson.getJSONObject(i);
				try {
					Users eleve = new Users(jsonEleve);
					
					System.out.println("getEleves : " + eleve);
					Data data = new Data(i, eleve, seance, false, "", "", false);
					
					eleves.add(data);
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

	@Override
	public void onClickData(Data item) {		
		if(!item.getBoolPresence()) {
			Intent intent = new Intent(this, SignatureActivity.class);
			intent.putExtra("data", item);
			startActivityForResult(intent, ACTICITY_REQUEST_CODE);	
		} else {
			item.setBoolPresence(false);
			item.setBoolRetard(false);
			((BaseAdapter)elevesListView.getAdapter()).notifyDataSetChanged();
		}
	}

	@Override
	public void onLongClickData(Data item) {
		Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
		
		/*Intent intent = new Intent(this, Compte.class);
		intent.putExtra("eleve", item.getEleve());
		startActivity(intent);*/
	}
}
