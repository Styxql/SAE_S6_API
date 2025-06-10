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
@Table(name = "t_e_marquecapteur_mar")
public class MarqueCapteur {

    @Id
    @Column(name = "mar_id")
    private Integer marqueId;

    @Column(name = "mar_nom", length = 100, nullable = false)
    private String nom;

    @OneToMany(mappedBy = "marque")
    private List<Capteur> capteurs;
}
