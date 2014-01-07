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
    private int idEleve;
    private int idSeance;
    private boolean boolRetard;
    private String remarque;
    private String urlCertificat;
    private boolean boolAbsent;
    private Signature signature = null;

    public Data() {
    }

    public Data(Integer id) {
        this.id = id;
    }

    public Data(Integer id, int idEleve, int idSeance, boolean boolRetard, String remarque, String urlCertificat, boolean boolAbsent) {
        this.id = id;
        this.idEleve = idEleve;
        this.idSeance = idSeance;
        this.boolRetard = boolRetard;
        this.remarque = remarque;
        this.urlCertificat = urlCertificat;
        this.boolAbsent = boolAbsent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
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

    public boolean getBoolAbsent() {
        return boolAbsent;
    }

    public void setBoolAbsent(boolean boolAbsent) {
        this.boolAbsent = boolAbsent;
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
