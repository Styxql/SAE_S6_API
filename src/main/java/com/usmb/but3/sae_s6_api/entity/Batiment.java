package com.usmb.but3.sae_s6_api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente un bâtiment dans le système.
 * Un bâtiment possède un nom et éventuellement une image associée.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_batiment_bat")
public class Batiment {

    /**
     * Identifiant unique du bâtiment.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "bat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom du bâtiment.
     * Obligatoire (non nul) et limité à 100 caractères.
     */
    @Column(name = "bat_nom", length = 100, nullable = false)
    private String nom;

    /**
     * URL de l'image représentant le bâtiment.
     * Optionnel et peut contenir jusqu'à 1024 caractères.
     */
    @Column(name = "bat_urlimg", length = 1024, nullable = true)
    private String urlImg;

     /**
     * Liste des salles associées à ce bâtiment.
     * Relation OneToMany. La suppression d’un bâtiment entraîne la suppression des salles associées.
     * Utilise @JsonBackReference pour éviter les boucles .
     */
    @OneToMany(mappedBy = "batiment", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Salle> salles;

    /**
     * Compare deux objets Batiment pour déterminer s'ils sont égaux.
     * Deux bâtiments sont égaux s'ils ont le même id, nom et urlImg.
     * @param obj L'objet à comparer avec le bâtiment actuel.
     * @return true si les deux objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Batiment other = (Batiment) obj;
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
        return true;
    }

    /**
     * Calcule le hashCode du bâtiment à partir de son id, nom et urlImg.
     * @return Un entier représentant le hash du bâtiment.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((urlImg == null) ? 0 : urlImg.hashCode());
        return result;
    }
}
