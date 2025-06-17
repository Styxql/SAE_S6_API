package com.usmb.but3.sae_s6_api.entity;

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
    private Integer id;

    @Column(name = "uni_nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "uni_symbole", length = 10, nullable = false)
    private String symbole;
}
