package com.usmb.but3.sae_s6_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.entity.ParametreCapteurId;

/**
 * Repository JPA pour l’entité ParametreCapteur.
 * Fournit des opérations CRUD et une méthode de recherche par capteur.
 */
public interface ParametreCapteurRepo extends JpaRepository<ParametreCapteur, ParametreCapteurId> {

    /**
     * Recherche les paramètres associés à un capteur donné.
     * @param capteurId Identifiant du capteur.
     * @return Liste des ParametreCapteur correspondants.
     */
    List<ParametreCapteur> findByCapteurId(Integer capteurId);
}
