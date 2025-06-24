package com.usmb.but3.sae_s6_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.TypeSalle;

/**
 * Repository JPA pour l’entité TypeSalle.
 * Fournit des opérations CRUD.
 */
public interface TypeSalleRepo extends JpaRepository<TypeSalle, Integer> {
}
