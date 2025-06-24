package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entité TypeEquipement.
 * Représente le type d’un équipement (ex : projecteur, ordinateur, tableau interactif...).
 * Cette entité permet de catégoriser les équipements dans le système.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_typeequipement_teq")
public class TypeEquipement {

    /**
     * Identifiant unique du type d’équipement.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "teq_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom du type d’équipement.
     * Champ obligatoire, limité à 100 caractères.
     */
    @Column(name = "teq_nom", length = 100, nullable = false)
    private String nom;

    /**
     * Compare deux objets TypeEquipement pour déterminer s’ils sont égaux.
     * L’égalité est basée sur l’identifiant et le nom.
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
        TypeEquipement other = (TypeEquipement) obj;
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
        return true;
    }

    /**
     * Calcule le hashCode de l’objet TypeEquipement.
     * @return Code de hachage basé sur l’identifiant et le nom.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        return result;
    }

}
