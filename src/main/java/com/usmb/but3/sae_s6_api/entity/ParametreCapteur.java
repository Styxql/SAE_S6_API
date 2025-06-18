package com.usmb.but3.sae_s6_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ParametreCapteurId.class)
@Table(name = "t_a_parametrecapteur_acu")
public class ParametreCapteur {

    @Id
    @Column(name = "uni_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uniteMesurerId;

    @Id
    @Column(name = "cap_id")
    private Integer capteurId;

    @Column(name = "acu_plagemin")
    private Integer plageMin;

    @Column(name = "acu_plagemax")
    private Integer plageMax;

    @Column(name = "acu_precision")
    private Integer precision;

    @ManyToOne
    @JoinColumn(name = "cap_id", insertable = false, updatable = false)
    @JsonBackReference
    private Capteur capteur;

    @ManyToOne
    @JoinColumn(name = "uni_id", insertable = false, updatable = false)
    private UniteMesurer uniteMesurer;

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
