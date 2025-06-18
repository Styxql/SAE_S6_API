package com.usmb.but3.sae_s6_api.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_salle_sal")
public class Salle {

    @Id
    @Column(name = "sal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sal_nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "sal_urlimg", length = 1024, nullable = true)
    private String urlImg;

    @Column(name = "sal_capacite")
    private Integer capacite;

    @ManyToOne
    @JoinColumn(name = "bat_id", nullable = false)
    private Batiment batiment;

    @ManyToOne
    @JoinColumn(name = "typ_id", nullable = false)
    private TypeSalle typeSalle;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Salle other = (Salle) obj;
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
        if (capacite == null) {
            if (other.capacite != null)
                return false;
        } else if (!capacite.equals(other.capacite))
            return false;
        if (batiment == null) {
            if (other.batiment != null)
                return false;
        } else if (!batiment.equals(other.batiment))
            return false;
        if (typeSalle == null) {
            if (other.typeSalle != null)
                return false;
        } else if (!typeSalle.equals(other.typeSalle))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((urlImg == null) ? 0 : urlImg.hashCode());
        result = prime * result + ((capacite == null) ? 0 : capacite.hashCode());
        result = prime * result + ((batiment == null) ? 0 : batiment.hashCode());
        result = prime * result + ((typeSalle == null) ? 0 : typeSalle.hashCode());
        return result;
    }

}
