package com.usmb.but3.sae_s6_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParametreCapteurId implements Serializable {
    private Integer uniteMesurerId;
    private Integer capteurId;
}
