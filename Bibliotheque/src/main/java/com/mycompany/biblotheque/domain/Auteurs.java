package com.mycompany.biblotheque.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Auteurs.
 */
@Entity
@Table(name = "auteurs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Auteurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "datedenaissance")
    private Instant datedenaissance;

    @OneToOne(mappedBy = "auteurs")
    @JsonIgnore
    private Livres livre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Auteurs nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Auteurs prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Instant getDatedenaissance() {
        return datedenaissance;
    }

    public Auteurs datedenaissance(Instant datedenaissance) {
        this.datedenaissance = datedenaissance;
        return this;
    }

    public void setDatedenaissance(Instant datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public Livres getLivre() {
        return livre;
    }

    public Auteurs livre(Livres livres) {
        this.livre = livres;
        return this;
    }

    public void setLivre(Livres livres) {
        this.livre = livres;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auteurs)) {
            return false;
        }
        return id != null && id.equals(((Auteurs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Auteurs{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", datedenaissance='" + getDatedenaissance() + "'" +
            "}";
    }
}
