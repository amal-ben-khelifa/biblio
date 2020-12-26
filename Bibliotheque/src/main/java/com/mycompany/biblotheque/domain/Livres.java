package com.mycompany.biblotheque.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Livres.
 */
@Entity
@Table(name = "livres")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Livres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_livre")
    private String nomLivre;

    @Column(name = "genre")
    private String genre;

    @Column(name = "nombredepages")
    private String nombredepages;

    @Column(name = "langues")
    private String langues;

    @Column(name = "prix")
    private String prix;

    @OneToOne
    @JoinColumn(unique = true)
    private Auteurs auteurs;

    @OneToMany(mappedBy = "livre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Bibliotheque> nomBiblios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomLivre() {
        return nomLivre;
    }

    public Livres nomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
        return this;
    }

    public void setNomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
    }

    public String getGenre() {
        return genre;
    }

    public Livres genre(String genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNombredepages() {
        return nombredepages;
    }

    public Livres nombredepages(String nombredepages) {
        this.nombredepages = nombredepages;
        return this;
    }

    public void setNombredepages(String nombredepages) {
        this.nombredepages = nombredepages;
    }

    public String getLangues() {
        return langues;
    }

    public Livres langues(String langues) {
        this.langues = langues;
        return this;
    }

    public void setLangues(String langues) {
        this.langues = langues;
    }

    public String getPrix() {
        return prix;
    }

    public Livres prix(String prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Auteurs getAuteurs() {
        return auteurs;
    }

    public Livres auteurs(Auteurs auteurs) {
        this.auteurs = auteurs;
        return this;
    }

    public void setAuteurs(Auteurs auteurs) {
        this.auteurs = auteurs;
    }

    public Set<Bibliotheque> getNomBiblios() {
        return nomBiblios;
    }

    public Livres nomBiblios(Set<Bibliotheque> bibliotheques) {
        this.nomBiblios = bibliotheques;
        return this;
    }

    public Livres addNomBiblio(Bibliotheque bibliotheque) {
        this.nomBiblios.add(bibliotheque);
        bibliotheque.setLivre(this);
        return this;
    }

    public Livres removeNomBiblio(Bibliotheque bibliotheque) {
        this.nomBiblios.remove(bibliotheque);
        bibliotheque.setLivre(null);
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
        if (!(o instanceof Livres)) {
            return false;
        }
        return id != null && id.equals(((Livres) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Livres{" +
            "id=" + getId() +
            ", nomLivre='" + getNomLivre() + "'" +
            ", genre='" + getGenre() + "'" +
            ", nombredepages='" + getNombredepages() + "'" +
            ", langues='" + getLangues() + "'" +
            ", prix='" + getPrix() + "'" +
            "}";
    }
}
