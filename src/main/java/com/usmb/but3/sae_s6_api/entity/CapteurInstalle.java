package com.usmb.but3.sae_s6_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_e_capteurinstalle_cin")
public class CapteurInstalle {

    @Id
    @Column(name = "cin_id")
    private Integer capteurInstalleId;

    @Column(name = "cin_posx", precision = 100, scale = 2, nullable = false)
    private BigDecimal positionX;

    @Column(name = "cin_posy", precision = 100, scale = 2, nullable = false)
    private BigDecimal positionY;

    @ManyToOne
    @JoinColumn(name = "cap_id", nullable = false)
    private Capteur capteur;

}
