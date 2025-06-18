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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_equipementinstalle_ein")
public class EquipementInstalle {
    
    @Id
    @Column(name = "ein_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ein_nombre")
    private Integer nombre;

    @ManyToOne
    @JoinColumn(name = "equ_id", nullable = false)
    private Equipement equipement;

    @ManyToOne
    @JoinColumn(name = "sal_id", nullable = false)
    private Salle salle;

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
