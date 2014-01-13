package com.example.ime5_tp2_absences;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Data;
import model.Seances;
import model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ime5_tp2_absences.DataAdapter.DataAdapterSelected;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SendDataAsyncTask extends AsyncTask<Void, Void, Boolean> {
	private Context context;
	private ListView listView;
	private Button valider;
	private GlobalState globalState;
	private ArrayList<Data> datas;
	private Seances seance;
	
	public SendDataAsyncTask(Context context, GlobalState globalState, ListView listView, Button valider, ArrayList<Data> datas, Seances seances) {
		this.context = context;
		this.listView = listView;
		this.valider = valider;
		this.globalState = globalState;
		this.datas = datas;
		this.seance = seances;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
			jsonWriter.setIndent("");
			jsonWriter.beginArray();
			for (Data value : datas) {
		    	 jsonWriter.beginObject();
		    	 jsonWriter.name("idEleve").value(""+value.getEleve().getId());
		    	 jsonWriter.name("idSeance").value(""+value.getSeance().getId());
		    	 jsonWriter.name("boolPresence").value("" + (value.getBoolPresence() ? 1 : 0));
		    	 jsonWriter.name("boolRetard").value("" + (value.getBoolRetard() ? 1 : 0));
		    	 if(value.getSignature() != null)
		    		 jsonWriter.name("signature").value(value.getSignature().getStringifiedSignature());
		    	 else
		    		 jsonWriter.name("signature").value("");
		         jsonWriter.endObject();
		     }
		     jsonWriter.endArray();
		     jsonWriter.close();
   
		     
		     String response = globalState.requete("action=setPresences&data="+URLEncoder.encode(out.toString()));
		     
		     Log.i("Absences", "Sending " + "action=setPresences&data="+URLEncoder.encode(out.toString()) + " : " + response);
		     
		     return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		valider.setEnabled(false);
		listView.setEnabled(false);
		seance.setValidee(true);
		super.onPostExecute(result);
	}
}

