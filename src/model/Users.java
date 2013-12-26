/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alex
 */
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String login;
    private String passe;
    private String nom;
    private String prenom;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(String login, String passe) {
		this.login = login;
		this.passe = passe;
	}
	
	public void fill(String jsonUser) throws JSONException {
		JSONObject json = new JSONObject(jsonUser);
		nom = json.getString("nom");
		prenom = json.getString("prenom");
	}
    
    public Users(Integer id, String login, String passe, String nom, String prenom) {
        this.id = id;
        this.login = login;
        this.passe = passe;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasse() {
        return passe;
    }

    public void setPasse(String passe) {
        this.passe = passe;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Users[ id=" + id + " ]";
    }
    
}
