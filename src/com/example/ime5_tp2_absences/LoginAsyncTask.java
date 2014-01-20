package com.example.ime5_tp2_absences;

import model.Users;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.ime5_tp2_absences.activity.ChoixSeanceActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LoginAsyncTask extends AsyncTask<Users, Void, String> {
	private Context context;
	private GlobalState globalState;
	private View mLoginFormView;
	private View mLoginStatusView;
	private Class mainActvity;
	
	public LoginAsyncTask(Context cont, Class mainActivity) {
		this.context = cont;
		this.mainActvity = mainActivity;
		globalState = (GlobalState) ((Activity) context).getApplication();
		mLoginFormView = ((Activity) context).findViewById(R.id.login_form);
		mLoginStatusView = ((Activity) context).findViewById(R.id.login_status);
		showProgress(true);
	}

	@Override
	protected String doInBackground(Users... params) {
		Users user = params[0];
		globalState.setUser(user);
		String response = globalState.requete("login="+user.getLogin()+"&passe="+user.getPasse());
		Log.i("TP2", "response : " + response);
		
		return response;
	}

	@Override
	protected void onPostExecute(final String response) {
		showProgress(false);

		checkLogin(response);
	}

	private void checkLogin(String response) {
		try {
			JSONObject json = new JSONObject(response);
			Boolean connecte = json.getBoolean("connecte");
			
			if(connecte){
				globalState.getUser().fill(json);
				
				launchMainActivity();
			}
			else{
				Toast.makeText(context,"Identifiants invalides : " + json.getString("feedback"), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void launchMainActivity() {
		Intent intent = new Intent(context, mainActvity);
		context.startActivity(intent);
		((Activity) context).finish();
	}
	
	@Override
	protected void onCancelled() {
		showProgress(false);
	}
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = context.getResources().getInteger(
					android.R.integer.config_shortAnimTime);
	
			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
	
			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}