package com.usmb.but3.sae_s6_api.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité TypeSalle.
 * Représente le type d’une salle (ex : salle de cours, laboratoire, amphithéâtre...).
 * Permet de catégoriser les salles dans le système.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_typesalle_typ")
public class TypeSalle {

    /**
     * Identifiant unique du type de salle.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "typ_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom du type de salle.
     * Champ obligatoire, limité à 100 caractères.
     */
    @Column(name = "typ_nom", length = 100, nullable = false)
    private String nom;

    /**
     * Compare deux objets TypeSalle pour déterminer s’ils sont égaux.
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
        TypeSalle other = (TypeSalle) obj;
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
     * Calcule le hashCode de l’objet TypeSalle.
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
