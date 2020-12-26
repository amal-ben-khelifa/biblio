package com.mycompany.biblotheque.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A JeuxEducatif.
 */
@Entity
@Table(name = "jeux_educatif")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JeuxEducatif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prix")
    private String prix;

    @OneToMany(mappedBy = "jeuxEducatif")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Bibliotheque> nomBiblios = new HashSet<>();

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

    public JeuxEducatif nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrix() {
        return prix;
    }

    public JeuxEducatif prix(String prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Set<Bibliotheque> getNomBiblios() {
        return nomBiblios;
    }

    public JeuxEducatif nomBiblios(Set<Bibliotheque> bibliotheques) {
        this.nomBiblios = bibliotheques;
        return this;
    }

    public JeuxEducatif addNomBiblio(Bibliotheque bibliotheque) {
        this.nomBiblios.add(bibliotheque);
        bibliotheque.setJeuxEducatif(this);
        return this;
    }

    public JeuxEducatif removeNomBiblio(Bibliotheque bibliotheque) {
        this.nomBiblios.remove(bibliotheque);
        bibliotheque.setJeuxEducatif(null);
        return this;
    }

    public void setNomBiblios(Set<Bibliotheque> bibliotheques) {
        this.nomBiblios = bibliotheques;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JeuxEducatif)) {
            return false;
        }
        return id != null && id.equals(((JeuxEducatif) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JeuxEducatif{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prix='" + getPrix() + "'" +
            "}";
    }
}
