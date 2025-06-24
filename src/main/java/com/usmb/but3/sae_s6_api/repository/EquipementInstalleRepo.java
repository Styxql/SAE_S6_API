package com.usmb.but3.sae_s6_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;

/**
 * Repository JPA pour l’entité EquipementInstalle.
 * Fournit des opérations CRUD et des méthodes de recherche par salle ou équipement.
 */
public interface EquipementInstalleRepo extends JpaRepository<EquipementInstalle, Integer> {

    /**
     * Recherche les équipements installés dans une salle donnée.
     * @param salleId Identifiant de la salle.
     * @return Liste des équipements installés.
     */
    List<EquipementInstalle> findBySalleId(Integer salleId);

    /**
     * Recherche les installations d’un équipement spécifique.
     * @param equipementId Identifiant de l’équipement.
     * @return Liste des enregistrements correspondants.
     */
    List<EquipementInstalle> findByEquipementId(Integer equipementId);
}
