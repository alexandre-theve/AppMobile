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
import android.widget.Toast;

public class GetDataEleve extends AsyncTask<Void, Void, Integer>{
	private GlobalState globalState;
	private Integer nb_seance;
	private Data eleve;


	public GetDataEleve(GlobalState globalState,Data eleve) {
	
		this.globalState = globalState;
		this.eleve=eleve;		
	}

	@Override
	protected Integer doInBackground(Void... params) {
		String response = globalState.requete("action=getnombreSeance&id_eleve=" + eleve.getEleve().getId());
		System.out.println("response : " + response);
		
		
		try {
			JSONObject json = new JSONObject(response);
			nb_seance= json.getInt("nb_seance");
			
			System.out.println("response : " + nb_seance);	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
    return nb_seance;
		}
		
	}
