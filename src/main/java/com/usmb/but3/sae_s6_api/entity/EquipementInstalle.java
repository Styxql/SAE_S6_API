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
}
