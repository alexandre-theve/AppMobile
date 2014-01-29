package com.example.ime5_tp2_absences;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LogoutAsyncTask extends AsyncTask<Void, Void, String> {
	private Context context;
	private GlobalState globalState;
	
	public LogoutAsyncTask(Context cont) {
		this.context = cont;
		globalState = (GlobalState) ((Activity) context).getApplication();
	}

	@Override
	protected String doInBackground(Void... params) {
		String response = globalState.requete("action=logout");
		
		return response;
	}

	@Override
	protected void onPostExecute(final String response) {
		globalState.goToLogin(context, response);
	}
}



