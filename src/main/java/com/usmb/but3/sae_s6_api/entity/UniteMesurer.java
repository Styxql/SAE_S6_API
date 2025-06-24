package com.usmb.but3.sae_s6_api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entité UniteMesurer.
 * Représente une unité de mesure utilisée pour exprimer les valeurs mesurées par un capteur.
 * Par exemple : Celsius (°C), Pascal (Pa), Lux (lx), etc.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_unitemesurer_uni")
public class UniteMesurer {

    /**
     * Identifiant unique de l’unité de mesure.
     * Généré automatiquement par la base de données.
     */
    @Id
    @Column(name = "uni_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom complet de l’unité de mesure (ex : Celsius, Pascal).
     * Obligatoire, limité à 100 caractères.
     */
    @Column(name = "uni_nom", length = 100, nullable = false)
    private String nom;

    /**
     * Symbole de l’unité de mesure (ex : °C, Pa, lx).
     * Obligatoire, limité à 10 caractères.
     */
    @Column(name = "uni_symbole", length = 10, nullable = false)
    private String symbole;

    /** Liste des paramètres liés à cette unité. */
    @OneToMany(mappedBy = "uniteMesurer", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<ParametreCapteur> parametreCapteurs;

    /**
     * Compare deux objets UniteMesurer pour déterminer s’ils sont égaux.
     * L’égalité est basée sur l’identifiant, le nom et le symbole.
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

    /**
     * Calcule le hashCode de l’objet UniteMesurer.
     * Basé sur l’identifiant, le nom et le symbole.
     * @return Code de hachage de l’unité.
     */
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
