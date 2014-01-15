package com.example.ime5_tp2_absences.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Seances;
import model.Signature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.example.ime5_tp2_absences.GlobalState;
import com.example.ime5_tp2_absences.LogoutAsyncTask;
import com.example.ime5_tp2_absences.R;
import com.example.ime5_tp2_absences.R.id;
import com.example.ime5_tp2_absences.R.layout;
import com.example.ime5_tp2_absences.R.menu;
import com.example.ime5_tp2_absences.R.string;
import com.example.ime5_tp2_absences.SeancesAsyncTask;

import GesturePanel.Point;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChoixSeanceActivity extends Activity implements OnClickListener, OnDateSetListener {
	private GlobalState globalState;
	private Spinner spinner;
	private TextView titre;
	private Dialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_seance);

		globalState = (GlobalState) getApplication();

		spinner = (Spinner) findViewById(R.id.choixConversation_choixConv);
		Button button = (Button) findViewById(R.id.choixConversation_btnOK);
		button.setOnClickListener(this);
		titre = (TextView) findViewById(R.id.choixSeance_titre);
		titre.setOnClickListener(this);

		Calendar calender = Calendar.getInstance();
		mDialog = new DatePickerDialog(this,
				this, calender.get(Calendar.YEAR),
				calender.get(Calendar.MONTH),
				calender.get(Calendar.DAY_OF_MONTH));
		
		Date d = new Date();
		
		setTitre(d);
		
		SeancesAsyncTask seancesAsyncTask = new SeancesAsyncTask(this,globalState, spinner, d);
		seancesAsyncTask.execute();
	}
	
	private void setTitre(Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		titre.setText(getString(R.string.seances_titre) + " " + format.format(date));
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
			Intent intent1 = new Intent(this, MonCompteActivity.class);
			startActivity(intent1);

			break;
		case R.id.action_logout:
			this.logout();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void logout() {
		LogoutAsyncTask logoutAsyncTask = new LogoutAsyncTask(this);
		logoutAsyncTask.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.choixConversation_btnOK:
			Intent intent = new Intent(this, SeanceActivity.class);

			Spinner spinner = (Spinner) findViewById(R.id.choixConversation_choixConv);
			intent.putExtra("seance", (Seances) spinner.getSelectedItem());

			startActivity(intent);
			break;
		case R.id.choixSeance_titre:
			mDialog.show();
			break;
		}
	}

	@Override
	public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
		if (arg0.isShown()) {
			Log.i("WTF", "onDateSet");
			Date d = new Date(arg1- 1900, arg2, arg3);
			setTitre(d);
			SeancesAsyncTask seancesAsyncTask = new SeancesAsyncTask(this,globalState, spinner, d);
			seancesAsyncTask.execute();
		}
	}
}
