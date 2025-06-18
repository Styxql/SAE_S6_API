package com.usmb.but3.sae_s6_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;

public interface CapteurInstalleRepo extends JpaRepository<CapteurInstalle, Integer> {
    List<CapteurInstalle> findBySalleId(Integer salleId);
}
