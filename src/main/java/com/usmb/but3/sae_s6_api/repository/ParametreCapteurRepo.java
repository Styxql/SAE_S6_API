package com.usmb.but3.sae_s6_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.entity.ParametreCapteurId;

public interface ParametreCapteurRepo extends JpaRepository<ParametreCapteur, ParametreCapteurId> {
    List<ParametreCapteur> findByCapteurId(Integer capteurId);

}
