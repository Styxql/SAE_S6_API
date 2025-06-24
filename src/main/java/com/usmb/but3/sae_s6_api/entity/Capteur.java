package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entité capteur.
 * Un capteur est associé à une marque, possède des dimensions physiques,
 * une référence, une description et peut être lié à plusieurs paramètres.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_capteur_cap")
public class Capteur {

    /**
     * Identifiant unique du capteur.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "cap_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom du capteur.
     * Obligatoire, limité à 100 caractères.
     */
    @Column(name = "cap_nom", length = 100, nullable = false)
    private String nom;

    /**
     * Description du capteur.
     * Optionnel, limité à 500 caractères.
     */
    @Column(name = "cap_description", length = 500)
    private String description;

    /**
     * Référence du capteur (ex. code produit).
     * Optionnel, limité à 200 caractères.
     */
    @Column(name = "cap_reference", length = 200)
    private String reference;

    /**
     * Hauteur physique du capteur (en cm par exemple).
     * Obligatoire avec précision 6 et échelle 2 (ex : 9999.99).
     */
    @Column(name = "cap_hauteur", precision = 6, scale = 2, nullable = false)
    private BigDecimal hauteur;

    /**
     * Longueur physique du capteur.
     * Obligatoire avec la même précision que la hauteur.
     */
    @Column(name = "cap_longueur", precision = 6, scale = 2, nullable = false)
    private BigDecimal longueur;

    /**
     * Largeur physique du capteur.
     * Obligatoire, format identique aux autres dimensions.
     */
    @Column(name = "cap_largeur", precision = 6, scale = 2, nullable = false)
    private BigDecimal largeur;

    /**
     * URL d'une image représentant le capteur.
     * Optionnel, jusqu'à 1024 caractères.
     */
    @Column(name = "cap_urlimg", length = 1024, nullable = true)
    private String urlImg;

    /**
     * Marque associée à ce capteur.
     * Relation obligatoire ManyToOne (plusieurs capteurs peuvent appartenir à une même marque).
     */
    @ManyToOne
    @JoinColumn(name = "mar_id", nullable = false)
    private Marque marque;

    /**
     * Liste des paramètres associés à ce capteur.
     * Relation OneToMany. Gérée via l’attribut `capteur` dans `ParametreCapteur`.
     * @JsonManagedReference évite les boucles de sérialisation JSON.
     */
    @OneToMany(mappedBy = "capteur")
    @JsonManagedReference
    private List<ParametreCapteur> parametreCapteur;

    /**
     * Compare deux objets Capteur pour déterminer s'ils sont égaux.
     * Deux capteurs sont égaux s’ils ont les mêmes valeurs pour tous les attributs.
     * @param obj L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Capteur other = (Capteur) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (reference == null) {
            if (other.reference != null)
                return false;
        } else if (!reference.equals(other.reference))
            return false;
        if (hauteur == null) {
            if (other.hauteur != null)
                return false;
        } else if (!hauteur.equals(other.hauteur))
            return false;
        if (longueur == null) {
            if (other.longueur != null)
                return false;
        } else if (!longueur.equals(other.longueur))
            return false;
        if (largeur == null) {
            if (other.largeur != null)
                return false;
        } else if (!largeur.equals(other.largeur))
            return false;
        if (urlImg == null) {
            if (other.urlImg != null)
                return false;
        } else if (!urlImg.equals(other.urlImg))
            return false;
        if (marque == null) {
            if (other.marque != null)
                return false;
        } else if (!marque.equals(other.marque))
            return false;
        if (parametreCapteur == null) {
            if (other.parametreCapteur != null)
                return false;
        } else if (!parametreCapteur.equals(other.parametreCapteur))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((reference == null) ? 0 : reference.hashCode());
        result = prime * result + ((hauteur == null) ? 0 : hauteur.hashCode());
        result = prime * result + ((longueur == null) ? 0 : longueur.hashCode());
        result = prime * result + ((largeur == null) ? 0 : largeur.hashCode());
        result = prime * result + ((urlImg == null) ? 0 : urlImg.hashCode());
        result = prime * result + ((marque == null) ? 0 : marque.hashCode());
        result = prime * result + ((parametreCapteur == null) ? 0 : parametreCapteur.hashCode());
        return result;
    }

}
