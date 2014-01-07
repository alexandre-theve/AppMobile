/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alex
 */
public class Seances implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Date dtDebut = new Date();
    private short duree;
    private String nom;
    private int idEnseignant;
    private int idGroupe;

    public Seances() {
    }

    public Seances(Integer id) {
        this.id = id;
    }

    public Seances(Integer id, Date dtDebut, short duree, String nom, int idEnseignant, int idGroupe) {
        this.id = id;
        this.dtDebut = dtDebut;
        this.duree = duree;
        this.nom = nom;
        this.idEnseignant = idEnseignant;
        this.idGroupe = idGroupe;
    }
    
    public Seances(JSONObject json) throws JSONException, ParseException {
		this.id = json.getInt("id");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.dtDebut = f.parse(json.getString("dtDebut"));
        this.duree = Short.parseShort(json.getString("duree"));
        this.nom = json.getString("nom");
        this.idEnseignant = json.getInt("idEnseignant");
        this.idGroupe = json.getInt("idGroupe");
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDtDebut() {
        return dtDebut;
    }

    public void setDtDebut(Date dtDebut) {
        this.dtDebut = dtDebut;
    }

    public short getDuree() {
        return duree;
    }

    public void setDuree(short duree) {
        this.duree = duree;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
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
        if (!(object instanceof Seances)) {
            return false;
        }
        Seances other = (Seances) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        return nom + " - " + f.format(dtDebut);
    }
    
}
