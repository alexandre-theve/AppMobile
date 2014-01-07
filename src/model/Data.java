/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Alex
 */
public class Data implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Users eleve;
    private Seances seance;
    private boolean boolRetard;
    private String remarque;
    private String urlCertificat;
    private boolean boolPresence;
    private Signature signature = null;

    public Data() {
    }

    public Data(Integer id) {
        this.id = id;
    }

    public Data(Integer id, Users eleve, Seances seance, boolean boolRetard, String remarque, String urlCertificat, boolean boolPresence) {
        this.id = id;
        this.eleve = eleve;
        this.seance = seance;
        this.boolRetard = boolRetard;
        this.remarque = remarque;
        this.urlCertificat = urlCertificat;
        this.boolPresence = boolPresence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getEleve() {
        return eleve;
    }

    public void setEleve(Users eleve) {
        this.eleve = eleve;
    }

    public Seances getSeance() {
        return seance;
    }

    public void setSeance(Seances seance) {
        this.seance = seance;
    }

    public boolean getBoolRetard() {
        return boolRetard;
    }

    public void setBoolRetard(boolean boolRetard) {
        this.boolRetard = boolRetard;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getUrlCertificat() {
        return urlCertificat;
    }

    public void setUrlCertificat(String urlCertificat) {
        this.urlCertificat = urlCertificat;
    }

    public boolean getBoolPresence() {
        return boolPresence;
    }

    public void setBoolPresence(boolean boolPresence) {
        this.boolPresence = boolPresence;
    }

    public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
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
        if (!(object instanceof Data)) {
            return false;
        }
        Data other = (Data) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Data[ id=" + id + " ]";
    }
    
}
