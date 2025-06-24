package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Entité capteurinstalle
 * Représente un capteur installé dans une salle.
 * Contient le nombre d'unités d'un capteur donné installées dans une salle spécifique.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_capteurinstalle_cin")
public class CapteurInstalle {

    /**
     * Identifiant unique de l'installation (clé primaire).
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "cin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre d'exemplaires du capteur installés dans la salle.
     * Ce champ est obligatoire.
     */
    @Column(name = "cin_nombre", nullable = false)
    private Integer nombre;

    /**
     * Capteur installé.
     * Relation ManyToOne vers l'entité Capteur.
     * Ce champ est obligatoire.
     */
    @ManyToOne
    @JoinColumn(name = "cap_id", nullable = false)
    private Capteur capteur;

    /**
     * Salle dans laquelle le capteur est installé.
     * Relation ManyToOne vers l'entité Salle.
     * Ce champ est obligatoire.
     */
    @ManyToOne
    @JoinColumn(name = "sal_id", nullable = false)
    private Salle salle;

    /**
     * Compare deux objets CapteurInstalle pour vérifier leur égalité.
     * Deux installations sont égales si leurs attributs id, nombre, capteur et salle sont égaux.
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
        CapteurInstalle other = (CapteurInstalle) obj;
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
        if (capteur == null) {
            if (other.capteur != null)
                return false;
        } else if (!capteur.equals(other.capteur))
            return false;
        if (salle == null) {
            if (other.salle != null)
                return false;
        } else if (!salle.equals(other.salle))
            return false;
        return true;
    }

    /**
     * Calcule le hashCode de l'objet CapteurInstalle.
     * @return Un entier représentant le hash de l'objet.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((capteur == null) ? 0 : capteur.hashCode());
        result = prime * result + ((salle == null) ? 0 : salle.hashCode());
        return result;
    }

}
