package com.usmb.but3.sae_s6_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.TypeEquipement;

/**
 * Repository JPA pour l’entité TypeEquipement.
 * Fournit des opérations CRUD.
 */
public interface TypeEquipementRepo extends JpaRepository<TypeEquipement, Integer> {
}
