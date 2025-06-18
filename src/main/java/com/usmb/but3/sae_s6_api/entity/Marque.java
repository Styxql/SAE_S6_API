package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_marque_mar")
public class Marque {

    @Id
    @Column(name = "mar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mar_nom", length = 100, nullable = false)
    private String nom;
}
