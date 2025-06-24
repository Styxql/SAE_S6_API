package com.usmb.but3.sae_s6_api.entity;

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
 * Entité EquipementInstalle.
 * Représente un équipement installé dans une salle, avec un certain nombre d'exemplaires.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_equipementinstalle_ein")
public class EquipementInstalle {

    /**
     * Identifiant unique de l'équipement installé.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "ein_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre d'exemplaires de l'équipement installés dans la salle.
     * Ce champ est obligatoire.
     */
    @Column(name = "ein_nombre", nullable = false)
    private Integer nombre;

    /**
     * Equipement installé.
     * Relation obligatoire ManyToOne vers l'entité Equipement.
     */
    @ManyToOne
    @JoinColumn(name = "equ_id", nullable = false)
    private Equipement equipement;

    /**
     * Salle dans laquelle l'équipement est installé.
     * Relation obligatoire ManyToOne vers l'entité Salle.
     */
    @ManyToOne
    @JoinColumn(name = "sal_id", nullable = false)
    private Salle salle;

    /**
     * Compare deux objets EquipementInstalle pour déterminer s’ils sont égaux.
     * Deux installations sont considérées comme égales si tous leurs attributs sont identiques.
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
        EquipementInstalle other = (EquipementInstalle) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (equipement == null) {
            if (other.equipement != null)
                return false;
        } else if (!equipement.equals(other.equipement))
            return false;
        if (salle == null) {
            if (other.salle != null)
                return false;
        } else if (!salle.equals(other.salle))
            return false;
        return true;
    }

    /**
     * Calcule le hashCode de l'objet EquipementInstalle.
     * Basé sur l'identifiant, l'équipement, la salle et le nombre d'unités installées.
     * @return Code de hachage de l'objet.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((equipement == null) ? 0 : equipement.hashCode());
        result = prime * result + ((salle == null) ? 0 : salle.hashCode());
        return result;
    }
}
