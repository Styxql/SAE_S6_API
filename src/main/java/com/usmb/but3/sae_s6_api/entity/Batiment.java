package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_batiment_bat")
public class Batiment {

    @Id
    @Column(name = "bat_id")
    private Integer id;

    @Column(name = "bat_nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "bat_urlimg", length = 1024, nullable = false)
    private String urlImg;
}
