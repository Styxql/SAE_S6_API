package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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

    @OneToMany(mappedBy = "capteur")
    @JsonManagedReference
    private List<ParametreCapteur> parametreCapteur;

}
