package com.example.ime5_tp2_absences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Data;
import model.Seances;
import model.Signature;
import model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ime5_tp2_absences.DataAdapter.DataAdapterSelected;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class GetDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Data>> {
	private Context context;
	private ListView listView;
	private GlobalState globalState;
	private Seances seance;
	private ArrayList<Data> eleves;
	
	public GetDataAsyncTask(Context context, GlobalState globalState, ListView listView, Seances seance) {
		this.context = context;
		this.globalState = globalState;
		this.listView = listView;
		this.seance = seance;
	}

	@Override
	protected ArrayList<Data> doInBackground(Void... params) {
		String response = globalState.requete("action=getListeEleves&idSeance=" + seance.getId());

		System.out.println("response : " + response);

		eleves = new ArrayList<Data>();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray elevesJson = json.getJSONArray("eleves");
			for (int i = 0; i < elevesJson.length(); i++) {
				JSONObject jsonEleve = elevesJson.getJSONObject(i);
				try {
					Users eleve = new Users(jsonEleve);

					System.out.println("getEleves : " + eleve + " - " + jsonEleve.getString("boolRetard"));
					Boolean boolRetard;
					if(!jsonEleve.getString("boolRetard").equals("null"))
						boolRetard = jsonEleve.getInt("boolRetard") == 1;
					else
						boolRetard = false;
					Boolean boolPresence;
					if(!jsonEleve.getString("boolPresence").equals("null"))
						boolPresence = jsonEleve.getInt("boolPresence") == 1;
					else
						boolPresence = false;
					String signature = (!jsonEleve.getString("signature").equals("null")) ? jsonEleve.getString("signature") : "";
					Data data = new Data(i, eleve, seance, boolRetard, "", "", boolPresence, new Signature(signature));

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
	protected void onPostExecute(final ArrayList<Data> eleves) {
		DataAdapter adapter = new DataAdapter(context, eleves, LayoutInflater.from(context));
		adapter.setOnDataAdapterSelected((DataAdapterSelected) context);
		// On attribut à notre listView l'adapter que l'on vient de créer
		listView.setAdapter(adapter);
	}

	/**
	 * @return the eleves
	 */
	public ArrayList<Data> getEleves() {
		return eleves;
	}

	/**
	 * @param eleves the eleves to set
	 */
	public void setEleves(ArrayList<Data> eleves) {
		this.eleves = eleves;
	}
}

