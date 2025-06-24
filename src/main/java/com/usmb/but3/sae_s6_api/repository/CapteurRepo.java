package com.usmb.but3.sae_s6_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import com.usmb.but3.sae_s6_api.entity.Capteur;

/**
 * Repository JPA pour l’entité Capteur.
 * Fournit des opérations CRUD et une méthode pour récupérer un capteur avec ses paramètres.
 */
public interface CapteurRepo extends JpaRepository<Capteur, Integer> {

    /**
     * Recherche un capteur par son identifiant en chargeant ses paramètres associés.
     * @param id Identifiant du capteur.
     * @return Un capteur avec sa liste de paramètres, si trouvé.
     */
    @Query("SELECT c FROM Capteur c LEFT JOIN FETCH c.parametreCapteur WHERE c.id = :id")
    Optional<Capteur> findByIdWithParametres(@Param("id") Integer id);
}
