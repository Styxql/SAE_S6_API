package com.usmb.but3.sae_s6_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité ParametreCapteur.
 * Représente les caractéristiques de mesure d’un capteur pour une unité donnée.
 * Cette entité correspond à une table d'association (relation many-to-many enrichie)
 * entre les entités Capteur et UniteMesurer.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ParametreCapteurId.class)
@Table(name = "t_a_parametrecapteur_acu")
public class ParametreCapteur {

    /**
     * Identifiant de l’unité de mesure (clé primaire composite).
     */
    @Id
    @Column(name = "uni_id")
    private Integer uniteMesurerId;

    /**
     * Identifiant du capteur (clé primaire composite).
     */
    @Id
    @Column(name = "cap_id")
    private Integer capteurId;

    /**
     * Plage minimale que le capteur peut mesurer pour cette unité.
     * Optionnel.
     */
    @Column(name = "acu_plagemin")
    private Integer plageMin;

    /**
     * Plage maximale que le capteur peut mesurer pour cette unité.
     * Optionnel.
     */
    @Column(name = "acu_plagemax")
    private Integer plageMax;

    /**
     * Précision de la mesure pour cette unité.
     * Optionnel. Valeur entière représentant la précision (ex: nombre de décimales).
     */
    @Column(name = "acu_precision")
    private Integer precision;

    /**
     * Capteur concerné.
     * Relation ManyToOne, join sur capteurId.
     * Champ non modifiable directement via cette entité (readonly).
     */
    @ManyToOne
    @JoinColumn(name = "cap_id", insertable = false, updatable = false)
    @JsonBackReference
    private Capteur capteur;

    /**
     * Unité de mesure concernée.
     * Relation ManyToOne, join sur uniteMesurerId.
     * Champ non modifiable directement via cette entité (readonly).
     */
    @ManyToOne
    @JoinColumn(name = "uni_id", insertable = false, updatable = false)
    private UniteMesurer uniteMesurer;

    /**
     * Compare deux objets ParametreCapteur pour déterminer s’ils sont égaux.
     * L’égalité est basée sur les clés composites, les plages, la précision et les relations.
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
        ParametreCapteur other = (ParametreCapteur) obj;
        if (uniteMesurerId == null) {
            if (other.uniteMesurerId != null)
                return false;
        } else if (!uniteMesurerId.equals(other.uniteMesurerId))
            return false;
        if (capteurId == null) {
            if (other.capteurId != null)
                return false;
        } else if (!capteurId.equals(other.capteurId))
            return false;
        if (plageMin == null) {
            if (other.plageMin != null)
                return false;
        } else if (!plageMin.equals(other.plageMin))
            return false;
        if (plageMax == null) {
            if (other.plageMax != null)
                return false;
        } else if (!plageMax.equals(other.plageMax))
            return false;
        if (precision == null) {
            if (other.precision != null)
                return false;
        } else if (!precision.equals(other.precision))
            return false;
        if (capteur == null) {
            if (other.capteur != null)
                return false;
        } else if (!capteur.equals(other.capteur))
            return false;
        if (uniteMesurer == null) {
            if (other.uniteMesurer != null)
                return false;
        } else if (!uniteMesurer.equals(other.uniteMesurer))
            return false;
        return true;
    }

    /**
     * Calcule le hashCode de l'objet ParametreCapteur.
     * Basé sur les clés composites et les champs associés.
     * @return Code de hachage.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uniteMesurerId == null) ? 0 : uniteMesurerId.hashCode());
        result = prime * result + ((capteurId == null) ? 0 : capteurId.hashCode());
        result = prime * result + ((plageMin == null) ? 0 : plageMin.hashCode());
        result = prime * result + ((plageMax == null) ? 0 : plageMax.hashCode());
        result = prime * result + ((precision == null) ? 0 : precision.hashCode());
        result = prime * result + ((capteur == null) ? 0 : capteur.hashCode());
        result = prime * result + ((uniteMesurer == null) ? 0 : uniteMesurer.hashCode());
        return result;
    }
}
