package com.usmb.but3.sae_s6_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;

public interface EquipementInstalleRepo extends JpaRepository<EquipementInstalle, Integer> {
    List<EquipementInstalle> findBySalleId(Integer salleId);
}
