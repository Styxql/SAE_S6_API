package com.usmb.but3.sae_s6_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.UniteMesurer;

/**
 * Repository JPA pour l’entité UniteMesurer.
 * Fournit des opérations CRUD.
 */
public interface UniteMesurerRepo extends JpaRepository<UniteMesurer, Integer> {
}
