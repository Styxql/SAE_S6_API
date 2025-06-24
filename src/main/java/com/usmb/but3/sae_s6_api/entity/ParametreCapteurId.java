package com.usmb.but3.sae_s6_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Classe ParametreCapteurId.
 * Représente la clé primaire composite de l'entité ParametreCapteur.
 * Contient les identifiants du capteur et de l’unité de mesure.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParametreCapteurId implements Serializable {

    /**
     * Identifiant de l’unité de mesure.
     * Fait partie de la clé primaire composite.
     */
    private Integer uniteMesurerId;

    /**
     * Identifiant du capteur.
     * Fait également partie de la clé primaire composite.
     */
    private Integer capteurId;
}