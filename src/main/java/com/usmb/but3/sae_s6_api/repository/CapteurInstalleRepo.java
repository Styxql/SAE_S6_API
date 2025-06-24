package com.usmb.but3.sae_s6_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;

/**
 * Repository JPA pour l’entité CapteurInstalle.
 * Fournit des opérations CRUD et des méthodes de recherche par salle ou capteur.
 */
public interface CapteurInstalleRepo extends JpaRepository<CapteurInstalle, Integer> {

    /**
     * Recherche les capteurs installés dans une salle donnée.
     * @param salleId Identifiant de la salle.
     * @return Liste des capteurs installés.
     */
    List<CapteurInstalle> findBySalleId(Integer salleId);

    /**
     * Recherche les installations d’un capteur spécifique.
     * @param capteurId Identifiant du capteur.
     * @return Liste des enregistrements correspondants.
     */
    List<CapteurInstalle> findByCapteurId(Integer capteurId);
}
