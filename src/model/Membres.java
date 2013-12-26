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
public class Membres implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private int idGroupe;
    private int idEleve;

    public Membres() {
    }

    public Membres(Integer id) {
        this.id = id;
    }

    public Membres(Integer id, int idGroupe, int idEleve) {
        this.id = id;
        this.idGroupe = idGroupe;
        this.idEleve = idEleve;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
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
        if (!(object instanceof Membres)) {
            return false;
        }
        Membres other = (Membres) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Membres[ id=" + id + " ]";
    }
    
}
