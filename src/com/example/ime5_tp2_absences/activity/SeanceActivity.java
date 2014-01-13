package com.example.ime5_tp2_absences.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ime5_tp2_absences.DataAdapter;
import com.example.ime5_tp2_absences.GetDataAsyncTask;
import com.example.ime5_tp2_absences.GlobalState;
import com.example.ime5_tp2_absences.LogoutAsyncTask;
import com.example.ime5_tp2_absences.R;
import com.example.ime5_tp2_absences.SendDataAsyncTask;
import com.example.ime5_tp2_absences.DataAdapter.DataAdapterSelected;
import com.example.ime5_tp2_absences.R.id;
import com.example.ime5_tp2_absences.R.layout;
import com.example.ime5_tp2_absences.R.menu;
import com.example.ime5_tp2_absences.R.string;

import model.Data;
import model.Seances;
import model.Signature;
import model.Users;
import GesturePanel.Point;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SeanceActivity extends Activity implements DataAdapterSelected,
		OnClickListener {
	private Seances seance;
	private ListView elevesListView;
	private Button valider;
	private GetDataAsyncTask dataAsyncTask;
	private GlobalState globalState;

	public static int ACTICITY_REQUEST_CODE = 42;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seance);

		globalState = (GlobalState) getApplication();

		seance = (Seances) getIntent().getSerializableExtra("seance");

		if (seance == null)
			seance = new Seances();

		fillUI();
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
			this.logout();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void logout(){
		LogoutAsyncTask logoutAsyncTask = new LogoutAsyncTask(this);
		logoutAsyncTask.execute();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTICITY_REQUEST_CODE && resultCode == RESULT_OK) {
			// signature = new Signature((ArrayList<Point>)
			// data.getSerializableExtra("signature"));

			Data eleve = (Data) data.getSerializableExtra("data");
			System.out.println("onActivityResult : " + eleve + " - " + eleve.getSignature());

			Data editedEleve = dataAsyncTask.getEleves().get(dataAsyncTask.getEleves().indexOf(eleve));
			editedEleve.setSignature(eleve.getSignature());
			editedEleve.setBoolPresence(true);
			editedEleve.setBoolRetard(eleve.getBoolRetard());

			((BaseAdapter) elevesListView.getAdapter()).notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void fillUI() {
		TextView titre = (TextView) findViewById(R.id.seance_titre);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		titre.setText(getString(R.string.seance_titre) + seance.getNom()
				+ " - " + format.format(seance.getDtDebut()));

		elevesListView = (ListView) findViewById(R.id.seance_liste);
		elevesListView.setEnabled(!seance.getValidee());
		
		valider = (Button) findViewById(R.id.seance_valider);
		valider.setOnClickListener(this);
		valider.setEnabled(!seance.getValidee());
		
		dataAsyncTask = new GetDataAsyncTask(this, globalState, elevesListView, seance);
		dataAsyncTask.execute();
	}

	@Override
	public void onClickData(Data item) {
		if(!seance.getValidee()) {
			if (!item.getBoolPresence()) {
				Intent intent = new Intent(this, SignatureActivity.class);
				intent.putExtra("data", item);
				startActivityForResult(intent, ACTICITY_REQUEST_CODE);
			} else {
				item.setBoolPresence(false);
				item.setBoolRetard(false);
				((BaseAdapter) elevesListView.getAdapter()).notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onLongClickData(Data item) {
		Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();

		/*
		 * Intent intent = new Intent(this, Compte.class);
		 * intent.putExtra("eleve", item.getEleve()); startActivity(intent);
		 */
	}

	@Override
	public void onClick(View arg0) {
		Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();

		sendData();
	}

	private void sendData() {
		//Envoi de la liste des presents
		SendDataAsyncTask sendDataAsyncTask = new SendDataAsyncTask(this, globalState, elevesListView, valider, dataAsyncTask.getEleves(), seance);
		sendDataAsyncTask.execute();
	}
}
