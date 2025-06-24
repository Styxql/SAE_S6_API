package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.repository.EquipementInstalleRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service métier pour la gestion des équipements installés.
 * Fournit les opérations CRUD ainsi que des recherches par salle.
 */
@Service
@RequiredArgsConstructor
public class EquipementInstalleService {
    
    private final EquipementInstalleRepo equipementInstalleRepo;

    /**
     * Récupère tous les équipements installés.
     * @return Liste des équipements installés.
     */
    public List<EquipementInstalle> getAllEquipementInstalles() {
        return equipementInstalleRepo.findAll();
    }

    /**
     * Récupère un équipement installé par son ID.
     * @param id ID de l’équipement installé.
     * @return L’équipement installé correspondant.
     * @throws ResponseStatusException si l’équipement n’est pas trouvé.
     */

     public EquipementInstalle getEquipementInstalleById(Integer id){
        return equipementInstalleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre un nouvel équipement installé.
     * @param equipementInstalle L’équipement à enregistrer.
     * @return L’équipement installé sauvegardé.
     */
    public EquipementInstalle saveEquipementInstalle(EquipementInstalle EquipementInstalle){
        return equipementInstalleRepo.save(EquipementInstalle);
    }

    /**
     * Met à jour un équipement installé existant.
     * @param equipementInstalle L’équipement modifié.
     * @return L’équipement sauvegardé.
     * @throws ResponseStatusException si l’ID n’existe pas.
     */
    public EquipementInstalle updateEquipementInstalle(EquipementInstalle EquipementInstalle){
        if (!equipementInstalleRepo.existsById(EquipementInstalle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return equipementInstalleRepo.save(EquipementInstalle);
    }

    /**
     * Supprime un équipement installé par son ID.
     * @param id ID de l’équipement à supprimer.
     * @throws ResponseStatusException si l’ID est introuvable.
     */
    public void deleteEquipementInstalleById(Integer id){
        if (!equipementInstalleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        equipementInstalleRepo.deleteById(id);
    }

    /**
     * Récupère les équipements installés dans une salle donnée.
     * @param salleId ID de la salle.
     * @return Liste des équipements installés dans la salle.
     */
    public List<EquipementInstalle> getBySalleId(Integer salleId) {
        return equipementInstalleRepo.findBySalleId(salleId);
    }
}