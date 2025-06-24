package com.usmb.but3.sae_s6_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.Marque;

/**
 * Repository JPA pour l’entité Marque.
 * Fournit des opérations CRUD.
 */
public interface MarqueRepo extends JpaRepository<Marque, Integer> {
}
