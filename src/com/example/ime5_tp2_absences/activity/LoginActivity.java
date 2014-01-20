package com.example.ime5_tp2_absences.activity;

import model.Users;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.ime5_tp2_absences.GlobalState;
import com.example.ime5_tp2_absences.LoginAsyncTask;
import com.example.ime5_tp2_absences.LogoutAsyncTask;
import com.example.ime5_tp2_absences.R;
import com.example.ime5_tp2_absences.R.id;
import com.example.ime5_tp2_absences.R.layout;
import com.example.ime5_tp2_absences.R.menu;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	private View mLoginFormView;
	private View mLoginStatusView;
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
	}
	
	private void initUI(){
		getPreferences();
		
		CheckBox saveLoginCheckBox = (CheckBox) findViewById(R.id.login_cbRemember);
		saveLoginCheckBox.setChecked(save);
		
		Boolean autoLoad = preferences.getBoolean("autoload", false);

		EditText loginEditText = (EditText) findViewById(R.id.login_edtLogin);
		EditText passEditText = (EditText) findViewById(R.id.login_edtPasse);
		
		if(!autoLoad) {		
			globalState.setUser(new Users());
		} 
		
		loginEditText.setText(globalState.getUser().getLogin());
		passEditText.setText(globalState.getUser().getPasse());
		
		Button okButton = (Button) findViewById(R.id.login_btnOK);
		okButton.setEnabled(globalState.verifReseau());
		
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(this, PreferencesActivity.class);
			
			startActivity(intent);
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	
	
	private void getPreferences() {
		save = preferences.getBoolean("remember", false);
		globalState.setUser(new Users(preferences.getString("login", ""), preferences.getString("passe", "")));
	}
	
	private void savePreferences() {
		preferences.edit().putBoolean("remember", save);
		
		if(save) {
			preferences.edit().putString("login", globalState.getUser().getLogin());
			preferences.edit().putString("passe", globalState.getUser().getPasse());
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
		save = saveLoginCheckBox.isChecked();
			
		savePreferences();
		
		globalState.login(this, new Users(loginEditText.getText().toString(), passEditText.getText().toString()));
	}
	
	
}
