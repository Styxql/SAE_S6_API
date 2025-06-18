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

}
