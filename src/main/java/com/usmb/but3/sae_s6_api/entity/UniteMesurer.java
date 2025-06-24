package com.usmb.but3.sae_s6_api.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_unitemesurer_uni")
public class UniteMesurer {

    @Id
    @Column(name = "uni_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uni_nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "uni_symbole", length = 10, nullable = false)
    private String symbole;

    @OneToMany(mappedBy = "uniteMesurer", cascade = CascadeType.REMOVE)
    private List<ParametreCapteur> parametreCapteurs;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniteMesurer other = (UniteMesurer) obj;
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
        if (symbole == null) {
            if (other.symbole != null)
                return false;
        } else if (!symbole.equals(other.symbole))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((symbole == null) ? 0 : symbole.hashCode());
        return result;
    }
}
