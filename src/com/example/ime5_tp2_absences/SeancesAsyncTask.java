package com.example.ime5_tp2_absences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Seances;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SeancesAsyncTask extends AsyncTask<Void, Void, ArrayList<Seances>> {
	private Context context;
	private Spinner spinner;
	private GlobalState globalState;
	
	public SeancesAsyncTask(Context context, GlobalState globalState, Spinner spinner) {
		this.context = context;
		this.globalState = globalState;
		this.spinner = spinner;
	}

	@Override
	protected ArrayList<Seances> doInBackground(Void... params) {
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
	protected void onPostExecute(final ArrayList<Seances> seances) {
		ArrayAdapter<Seances> dataAdapter = new ArrayAdapter<Seances>(context,
				android.R.layout.simple_dropdown_item_1line, seances);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}
}

