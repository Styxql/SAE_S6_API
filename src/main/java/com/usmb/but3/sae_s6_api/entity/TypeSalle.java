package com.usmb.but3.sae_s6_api.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_typesalle_typ")
public class TypeSalle {

    @Id
    @Column(name = "typ_id")
    private Integer typeSalleId;

    @Column(name = "typ_nom", length = 100, nullable = false)
    private String nom;

    @OneToMany(mappedBy = "typeSalle")
    private List<Salle> salles;
}
