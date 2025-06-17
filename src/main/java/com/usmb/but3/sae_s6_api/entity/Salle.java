package com.usmb.but3.sae_s6_api.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_salle_sal")
public class Salle {

    @Id
    @Column(name = "sal_id")
    private Integer id;

    @Column(name = "sal_nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "sal_urlimg", length = 1024, nullable = true)
    private String urlImg;

    @Column(name = "sal_capacite")
    private Integer capacite;

    @ManyToOne
    @JoinColumn(name = "bat_id", nullable = false)
    private Batiment batiment;

    @ManyToOne
    @JoinColumn(name = "typ_id", nullable = false)
    private TypeSalle typeSalle;

    @OneToMany(mappedBy = "salle")
    private List<CapteurInstalle> CapteurInstalles;

    @OneToMany(mappedBy = "salle")
    private List<EquipementInstalle> EquipementInstalles;
}
