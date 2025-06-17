package com.usmb.but3.sae_s6_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usmb.but3.sae_s6_api.entity.Salle;

public interface SalleRepo extends JpaRepository<Salle, Integer> {
    List<Salle> findByBatimentId(Integer batimentId);
}
