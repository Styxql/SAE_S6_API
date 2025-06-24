package com.usmb.but3.sae_s6_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entité Equipement.
 * Représente un équipement physique appartenant à une marque et à un type d’équipement donné.
 * L’équipement possède un nom, des dimensions physiques et une image optionnelle.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_equipement_equ")
public class Equipement {

    /**
     * Identifiant unique de l’équipement.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "equ_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom de l’équipement.
     * Obligatoire, limité à 100 caractères.
     */
    @Column(name = "equ_nom", length = 100, nullable = false)
    private String nom;

    /**
     * Hauteur de l’équipement.
     * Obligatoire avec une précision de 6 et une échelle de 2 (ex : 9999.99).
     */
    @Column(name = "equ_hauteur", precision = 6, scale = 2, nullable = false)
    private BigDecimal hauteur;

    /**
     * Longueur de l’équipement.
     * Obligatoire avec le même format que la hauteur.
     */
    @Column(name = "equ_longueur", precision = 6, scale = 2, nullable = false)
    private BigDecimal longueur;

    /**
     * Largeur de l’équipement.
     * Obligatoire avec le même format que les autres dimensions.
     */
    @Column(name = "equ_largeur", precision = 6, scale = 2, nullable = false)
    private BigDecimal largeur;

    /**
     * URL d’une image représentant l’équipement.
     * Optionnel, jusqu’à 1024 caractères.
     */
    @Column(name = "equ_urlimg", length = 1024, nullable = true)
    private String urlImg;

    /**
     * Marque associée à l’équipement.
     * Relation obligatoire ManyToOne vers l’entité Marque.
     */
    @ManyToOne
    @JoinColumn(name = "mar_id", nullable = false)
    private Marque marque;

    /**
     * Type d’équipement (ex : projecteur, tableau...).
     * Relation obligatoire ManyToOne vers l’entité TypeEquipement.
     */
    @ManyToOne
    @JoinColumn(name = "teq_id", nullable = false)
    private TypeEquipement typeEquipement;

    /**
     * Compare deux objets Equipement pour déterminer s’ils sont égaux.
     * L’égalité est basée sur tous les attributs de l’équipement.
     * @param obj L’objet à comparer.
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
        Equipement other = (Equipement) obj;
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
        if (typeEquipement == null) {
            if (other.typeEquipement != null)
                return false;
        } else if (!typeEquipement.equals(other.typeEquipement))
            return false;
        return true;
    }

    /**
     * Calcule le hashCode de l’équipement.
     * Basé sur l’ensemble de ses attributs pour cohérence avec equals().
     * @return Code de hachage de l’objet Equipement.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((hauteur == null) ? 0 : hauteur.hashCode());
        result = prime * result + ((longueur == null) ? 0 : longueur.hashCode());
        result = prime * result + ((largeur == null) ? 0 : largeur.hashCode());
        result = prime * result + ((urlImg == null) ? 0 : urlImg.hashCode());
        result = prime * result + ((marque == null) ? 0 : marque.hashCode());
        result = prime * result + ((typeEquipement == null) ? 0 : typeEquipement.hashCode());
        return result;
    }
}
