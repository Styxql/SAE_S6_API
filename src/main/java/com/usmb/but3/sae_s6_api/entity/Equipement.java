package com.usmb.but3.sae_s6_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Equipement {

    @Id
    @Column(name = "equ_id")
    private Integer id;

    @Column(name = "equ_nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "equ_hauteur", precision = 100, scale = 2, nullable = false)
    private BigDecimal hauteur;

    @Column(name = "equ_longueur", precision = 100, scale = 2, nullable = false)
    private BigDecimal longueur;

    @Column(name = "equ_largeur", precision = 100, scale = 2, nullable = false)
    private BigDecimal largeur;

    @Column(name = "equ_urlimg", length = 1024, nullable = false)
    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "mar_id", nullable = false)
    private Marque marque;

    @ManyToOne
    @JoinColumn(name = "teq_id", nullable = false)
    private TypeEquipement typeEquipement;
}
