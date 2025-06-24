package com.usmb.but3.sae_s6_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.Equipement;

/**
 * Repository JPA pour l’entité Equipement.
 * Fournit des opérations CRUD.
 */
public interface EquipementRepo extends JpaRepository<Equipement, Integer> {
}
