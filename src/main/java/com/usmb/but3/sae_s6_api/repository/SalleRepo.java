package com.usmb.but3.sae_s6_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.Salle;

/**
 * Repository JPA pour l’entité Salle.
 * Fournit des opérations CRUD et une méthode de recherche par bâtiment.
 */
public interface SalleRepo extends JpaRepository<Salle, Integer> {

    /**
     * Recherche les salles associées à un bâtiment donné.
     * @param batimentId Identifiant du bâtiment.
     * @return Liste des salles correspondantes.
     */
    List<Salle> findByBatimentId(Integer batimentId);
}
