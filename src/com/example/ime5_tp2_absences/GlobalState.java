package com.example.ime5_tp2_absences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.Users;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class GlobalState extends Application {
	private DefaultHttpClient client;
	public boolean connecte = false;
	private Users user = new Users();
	private String sType;

	public void createClient() {
		this.client = new DefaultHttpClient();
	}

	// piste : rendre cette fonction statique ?
	// PB : on ne peut utiliser this dans un contexte statique
	public void alerter(String s) {
		Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);
		t.show();
	}

	public boolean verifReseau() {
		// On v�rifie si le r�seau est disponible,
		// si oui on change le statut du bouton de connexion
		ConnectivityManager cnMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cnMngr.getActiveNetworkInfo();

		sType = "Aucun r�seau d�tect�";
		Boolean bStatut = false;
		if (netInfo != null) {

			State netState = netInfo.getState();

			if (netState.compareTo(State.CONNECTED) == 0) {
				bStatut = true;
				int netType = netInfo.getType();
				switch (netType) {
				case ConnectivityManager.TYPE_MOBILE:
					sType = "R�seau mobile d�tect�";
					break;
				case ConnectivityManager.TYPE_WIFI:
					sType = "R�seau wifi d�tect�";
					break;
				}

			}
		}

		//alerter(sType);
		return bStatut;

	}

	public String requete(String qs) {
		
		if (qs != null) {
			// l'url � utiliser correspond � celle d�finie dans les pr�f�rences
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String urlData = preferences.getString("urlData", "http://192.168.1.24/android_absences/data.php");
			
			String q = urlData  + "?" + qs;
			Log.i("TP2", "Querying : " + q);
					
			HttpGet req = new HttpGet(q);
			HttpResponse reponse;
			HttpEntity corpsReponse;
			InputStream is;
			try {
				reponse = client.execute(req); // ex�cuter requ�te
				corpsReponse = reponse.getEntity(); // r�cup�re le r�sultat
				is = corpsReponse.getContent();
				String txtReponse = convertStreamToString(is);

				return txtReponse;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "";

	}

	private String convertStreamToString(InputStream in) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}
}