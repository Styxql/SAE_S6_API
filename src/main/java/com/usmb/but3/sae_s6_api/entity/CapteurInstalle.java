package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_capteurinstalle_cin")
public class CapteurInstalle {

    @Id
    @Column(name = "cin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cin_nombre")
    private Integer nombre;

    @ManyToOne
    @JoinColumn(name = "cap_id", nullable = false)
    private Capteur capteur;

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
