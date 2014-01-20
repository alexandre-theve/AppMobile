package com.example.ime5_tp2_absences;

import java.util.ArrayList;

import model.Data;
import model.Seances;
import model.Signature;
import model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GetDataEleve extends AsyncTask<Void, Void, Boolean>{
	private GlobalState globalState;
	private Data eleve;
	private TextView nbSeance, nbAbsence, nbRetard;


	public GetDataEleve(GlobalState globalState,Data eleve, TextView nbSeance, TextView nbAbsence, TextView nbRetard) {
		this.globalState = globalState;
		this.eleve=eleve;
		this.nbSeance = nbSeance;
		this.nbAbsence = nbAbsence;
		this.nbRetard = nbRetard;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		String response = globalState.requete("action=getnombreSeance&id_eleve=" + eleve.getEleve().getId());
		System.out.println("response : " + response);
		
		try {
			JSONObject json = new JSONObject(response);
			eleve.setNbSeances(json.getInt("nb_seance"));
			eleve.setNbAbsences(json.getInt("nb_absence"));
			eleve.setNbRetards(json.getInt("nb_retard"));
			
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if(result) {
			nbSeance.setText(""+eleve.getNbSeances());
			nbAbsence.setText(""+eleve.getNbAbsences());
			nbRetard.setText(""+eleve.getNbRetards());
		}
	}
}
