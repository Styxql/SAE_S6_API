package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.repository.EquipementRepo;

import lombok.RequiredArgsConstructor;


/**
 * Service métier pour la gestion des équipements.
 * Fournit les opérations CRUD ainsi que la suppression en cascade sur EquipementInstalle.
 */
@Service
@RequiredArgsConstructor
public class EquipementService {
    private final EquipementRepo equipementRepo;

    /**
     * Récupère tous les équipements.
     */
    public List<Equipement> getAllEquipements(){
        return equipementRepo.findAll();
    }

    /**
     * Récupère un équipement par son ID.
     */
    public Equipement getEquipementById(Integer id){
        return equipementRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Crée un nouvel équipement.
     */
    public Equipement saveEquipement(Equipement capteur){
        return equipementRepo.save(capteur);
    }

    /**
     * Met à jour un équipement existant.
     */
    public Equipement updateEquipement(Equipement capteur){
        if (!equipementRepo.existsById(capteur.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return equipementRepo.save(capteur);
    }

    /**
     * Supprime un équipement par ID, ainsi que ses installations associées.
     */
     public void deleteEquipementById(Integer id) {
        if (!equipementRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Equipement non trouvé");
        }
        equipementRepo.deleteById(id);
    }
}