package com.usmb.but3.sae_s6_api.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entité Salle.
 * Représente une salle située dans un bâtiment, avec un type spécifique,
 * une capacité d'accueil, et éventuellement une image.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_salle_sal")
public class Salle {

    /**
     * Identifiant unique de la salle.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "sal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom de la salle.
     * Obligatoire, limité à 100 caractères.
     */
    @Column(name = "sal_nom", length = 100, nullable = false)
    private String nom;

    /**
     * URL d'une image représentant la salle.
     * Optionnel, limité à 1024 caractères.
     */
    @Column(name = "sal_urlimg", length = 1024, nullable = true)
    private String urlImg;

    /**
     * Capacité d’accueil de la salle (en nombre de personnes).
     * Optionnel.
     */
    @Column(name = "sal_capacite")
    private Integer capacite;

    /**
     * Bâtiment dans lequel se trouve la salle.
     * Relation obligatoire ManyToOne vers l’entité Batiment.
     */
    @ManyToOne
    @JoinColumn(name = "bat_id", nullable = false)
    private Batiment batiment;

    /** Liste des équipements installés dans cette salle. */
    @OneToMany(mappedBy = "salle", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<EquipementInstalle> equipementInstalles;

    /** Liste des capteurs installés dans cette salle. */
    @OneToMany(mappedBy = "salle", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<CapteurInstalle> capteurInstalles;

    /**
     * Type de la salle (ex : amphithéâtre, salle de cours, laboratoire...).
     * Relation obligatoire ManyToOne vers l’entité TypeSalle.
     */
    @ManyToOne
    @JoinColumn(name = "typ_id", nullable = false)
    private TypeSalle typeSalle;

    /**
     * Compare deux objets Salle pour déterminer s’ils sont égaux.
     * L’égalité est basée sur tous les attributs de l’entité.
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
        Salle other = (Salle) obj;
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
        if (urlImg == null) {
            if (other.urlImg != null)
                return false;
        } else if (!urlImg.equals(other.urlImg))
            return false;
        if (capacite == null) {
            if (other.capacite != null)
                return false;
        } else if (!capacite.equals(other.capacite))
            return false;
        if (batiment == null) {
            if (other.batiment != null)
                return false;
        } else if (!batiment.equals(other.batiment))
            return false;
        if (typeSalle == null) {
            if (other.typeSalle != null)
                return false;
        } else if (!typeSalle.equals(other.typeSalle))
            return false;
        return true;
    }

    /**
     * Calcule le hashCode de l'objet Salle.
     * Basé sur l’ensemble des champs pour cohérence avec equals().
     * @return Code de hachage de la salle.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((urlImg == null) ? 0 : urlImg.hashCode());
        result = prime * result + ((capacite == null) ? 0 : capacite.hashCode());
        result = prime * result + ((batiment == null) ? 0 : batiment.hashCode());
        result = prime * result + ((typeSalle == null) ? 0 : typeSalle.hashCode());
        return result;
    }

}
