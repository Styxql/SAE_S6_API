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
    private Integer acuPlagemin;

    @Column(name = "acu_plagemax")
    private Integer acuPlagemax;

    @Column(name = "acu_precision")
    private Integer acuPrecision;

    @ManyToOne
    @JoinColumn(name = "cap_id", insertable = false, updatable = false)
    @JsonBackReference
    private Capteur capteur;

    @ManyToOne
    @JoinColumn(name = "uni_id", insertable = false, updatable = false)
    private UniteMesurer uniteMesurer;
}
