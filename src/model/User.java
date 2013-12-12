package model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String login;
	private String pass;
	private String nom;
	private String prenom;
	
	public User() {
		login = "";
		pass = "";
		nom = "";
		prenom = "";
	}
	
	public User(String login, String pass) {
		this.login = login;
		this.pass = pass;
	}
	
	public void fill(String jsonUser) throws JSONException {
		JSONObject json = new JSONObject(jsonUser);
		nom = json.getString("nom");
		prenom = json.getString("prenom");
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}
