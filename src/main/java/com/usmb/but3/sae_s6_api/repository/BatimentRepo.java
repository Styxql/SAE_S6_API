package com.usmb.but3.sae_s6_api.repository;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.usmb.but3.sae_s6_api.entity.Batiment;

public interface BatimentRepo extends JpaRepository<Batiment, Integer> {
}
