package com.example.ime5_tp2_absences;

import model.User;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private Boolean save;
	private SharedPreferences preferences;
	private GlobalState globalState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		globalState = (GlobalState) getApplication();
		globalState.createClient();
		
		Button okButton = (Button) findViewById(R.id.login_btnOK);
		okButton.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		initUI();
		
		verifConnect();
	}
	
	private void verifConnect(){
		Boolean conecte = globalState.verifReseau();
		globalState.alerter(globalState.getsType());
		if(conecte){
			// verifier si logé, si oui remplir user et lancer activity
			
			String response = globalState.requete("");
			Log.i("TP2", "response : " + response);
			
			checkLogin(response);
		} 
	}
	
	private void initUI(){
		getPreferences();
		
		CheckBox saveLoginCheckBox = (CheckBox) findViewById(R.id.login_cbRemember);
		saveLoginCheckBox.setChecked(save);
		
		Boolean autoLoad = preferences.getBoolean("autoload", false);

		EditText loginEditText = (EditText) findViewById(R.id.login_edtLogin);
		EditText passEditText = (EditText) findViewById(R.id.login_edtPasse);
		
		if(!autoLoad) {		
			globalState.setUser(new User());
		} 
		
		loginEditText.setText(globalState.getUser().getLogin());
		passEditText.setText(globalState.getUser().getPass());
		
		Button okButton = (Button) findViewById(R.id.login_btnOK);
		okButton.setEnabled(globalState.verifReseau());
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
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getPreferences() {
		save = preferences.getBoolean("remember", false);
		globalState.setUser(new User(preferences.getString("login", ""), preferences.getString("passe", "")));
	}
	
	private void savePreferences() {
		preferences.edit().putBoolean("remember", save);
		
		if(save) {
			preferences.edit().putString("login", globalState.getUser().getLogin());
			preferences.edit().putString("passe", globalState.getUser().getPass());
		} else {
			preferences.edit().putString("login", "");
			preferences.edit().putString("passe", "");
		}
	}
	
	@Override
	public void onClick(View arg0) {
		CheckBox saveLoginCheckBox = (CheckBox) findViewById(R.id.login_cbRemember);
		EditText loginEditText = (EditText) findViewById(R.id.login_edtLogin);
		EditText passEditText = (EditText) findViewById(R.id.login_edtPasse);
		globalState.setUser(new User(loginEditText.getText().toString(), passEditText.getText().toString()));
		save = saveLoginCheckBox.isChecked();
			
		savePreferences();
		
		//if(globalState.verifReseau() && !login.equals("") && !pass.equals("")){
			
			
			String response = globalState.requete("login="+globalState.getUser().getLogin()+"&passe="+globalState.getUser().getPass());
			Log.i("TP2", "response : " + response);
			
			checkLogin(response);
		//} 
	}
	
	private void checkLogin(String response) {
		try {
			JSONObject json = new JSONObject(response);
			Boolean connecte = json.getBoolean("connecte");
			
			if(connecte){
				globalState.getUser().fill(response);
				
				launchSeanceActivity();
			}
			else{
				Toast.makeText(this,"Identifiants invalides : " + json.getString("feedback"), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private void launchSeanceActivity() {
		Intent intent = new Intent(this, ChoixSeance.class);
		startActivity(intent);
	}

}
