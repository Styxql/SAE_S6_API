package com.usmb.but3.sae_s6_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import com.usmb.but3.sae_s6_api.entity.Capteur;

public interface CapteurRepo extends JpaRepository<Capteur, Integer>{
    @Query("SELECT c FROM Capteur c LEFT JOIN FETCH c.parametreCapteur WHERE c.id = :id")
    Optional<Capteur> findByIdWithParametres(@Param("id") Integer id);

}
