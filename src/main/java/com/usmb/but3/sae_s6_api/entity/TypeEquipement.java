package com.usmb.but3.sae_s6_api.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class TypeEquipement {
    
    @Id
    @Column(name = "teq_id")
    private Integer id;

    @Column(name = "teq_nom", length = 100, nullable = false)
    private String nom;

    @OneToMany(mappedBy = "typeEquipement")
    private List<Equipement> equipements;
}
