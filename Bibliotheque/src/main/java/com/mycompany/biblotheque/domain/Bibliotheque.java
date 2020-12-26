package com.mycompany.biblotheque.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Bibliotheque.
 */
@Entity
@Table(name = "bibliotheque")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bibliotheque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_biblio")
    private String nomBiblio;

    @Column(name = "adresse")
    private String adresse;

    @ManyToOne
    @JsonIgnoreProperties(value = "nomBiblios", allowSetters = true)
    private Livres livre;

    @ManyToOne
    @JsonIgnoreProperties(value = "nomBiblios", allowSetters = true)
    private JeuxEducatif jeuxEducatif;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomBiblio() {
        return nomBiblio;
    }

    public Bibliotheque nomBiblio(String nomBiblio) {
        this.nomBiblio = nomBiblio;
        return this;
    }

    public void setNomBiblio(String nomBiblio) {
        this.nomBiblio = nomBiblio;
    }

    public String getAdresse() {
        return adresse;
    }

    public Bibliotheque adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Livres getLivre() {
        return livre;
    }

    public Bibliotheque livre(Livres livres) {
        this.livre = livres;
        return this;
    }

    public void setLivre(Livres livres) {
        this.livre = livres;
    }

    public JeuxEducatif getJeuxEducatif() {
        return jeuxEducatif;
    }

    public Bibliotheque jeuxEducatif(JeuxEducatif jeuxEducatif) {
        this.jeuxEducatif = jeuxEducatif;
        return this;
    }

    public void setJeuxEducatif(JeuxEducatif jeuxEducatif) {
        this.jeuxEducatif = jeuxEducatif;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bibliotheque)) {
            return false;
        }
        return id != null && id.equals(((Bibliotheque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bibliotheque{" +
            "id=" + getId() +
            ", nomBiblio='" + getNomBiblio() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
