package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entité Marque.
 * Représente une marque à laquelle peuvent être associés des capteurs ou équipements.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_marque_mar")
public class Marque {

    /**
     * Identifiant unique de la marque.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "mar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom de la marque.
     * Obligatoire, limité à 100 caractères.
     */
    @Column(name = "mar_nom", length = 100, nullable = false)
    private String nom;

    /**
     * Compare deux objets Marque pour déterminer s’ils sont égaux.
     * Deux marques sont égales si elles ont le même identifiant et le même nom.
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
        Marque other = (Marque) obj;
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
     * Calcule le hashCode de l’objet Marque.
     * Basé sur l’identifiant et le nom.
     * @return Code de hachage de l’objet.
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
