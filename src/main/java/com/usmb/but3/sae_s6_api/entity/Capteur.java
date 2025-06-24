package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_capteur_cap")
public class Capteur {

    @Id
    @Column(name = "cap_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cap_nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "cap_description", length = 500)
    private String description;

    @Column(name = "cap_reference", length = 200)
    private String reference;

    @Column(name = "cap_hauteur", precision = 6, scale = 2, nullable = false)
    private BigDecimal hauteur;

    @Column(name = "cap_longueur", precision = 6, scale = 2, nullable = false)
    private BigDecimal longueur;

    @Column(name = "cap_largeur", precision = 6, scale = 2, nullable = false)
    private BigDecimal largeur;

    @Column(name = "cap_urlimg", length = 1024, nullable = true)
    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "mar_id", nullable = false)
    private Marque marque;

    @OneToMany(mappedBy = "capteur", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<ParametreCapteur> parametreCapteur;

    @OneToMany(mappedBy = "capteur", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<CapteurInstalle> capteurInstalles;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Capteur other = (Capteur) obj;
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
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (reference == null) {
            if (other.reference != null)
                return false;
        } else if (!reference.equals(other.reference))
            return false;
        if (hauteur == null) {
            if (other.hauteur != null)
                return false;
        } else if (!hauteur.equals(other.hauteur))
            return false;
        if (longueur == null) {
            if (other.longueur != null)
                return false;
        } else if (!longueur.equals(other.longueur))
            return false;
        if (largeur == null) {
            if (other.largeur != null)
                return false;
        } else if (!largeur.equals(other.largeur))
            return false;
        if (urlImg == null) {
            if (other.urlImg != null)
                return false;
        } else if (!urlImg.equals(other.urlImg))
            return false;
        if (marque == null) {
            if (other.marque != null)
                return false;
        } else if (!marque.equals(other.marque))
            return false;
        if (parametreCapteur == null) {
            if (other.parametreCapteur != null)
                return false;
        } else if (!parametreCapteur.equals(other.parametreCapteur))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((reference == null) ? 0 : reference.hashCode());
        result = prime * result + ((hauteur == null) ? 0 : hauteur.hashCode());
        result = prime * result + ((longueur == null) ? 0 : longueur.hashCode());
        result = prime * result + ((largeur == null) ? 0 : largeur.hashCode());
        result = prime * result + ((urlImg == null) ? 0 : urlImg.hashCode());
        result = prime * result + ((marque == null) ? 0 : marque.hashCode());
        result = prime * result + ((parametreCapteur == null) ? 0 : parametreCapteur.hashCode());
        return result;
    }

}
