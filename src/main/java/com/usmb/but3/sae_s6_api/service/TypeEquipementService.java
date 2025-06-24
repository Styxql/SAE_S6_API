package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.repository.TypeEquipementRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service pour la gestion des types d'équipements.
 * Fournit les opérations CRUD de base.
 */
@Service
@RequiredArgsConstructor
public class TypeEquipementService {

    // Repository injecté pour accéder aux données de type d'équipement
    private final TypeEquipementRepo typeEquipementRepo;

    /**
     * Récupère tous les types d'équipements.
     * @return liste de tous les types d'équipements
     */
    public List<TypeEquipement> getAllTypeEquipements(){
        return typeEquipementRepo.findAll();
    }

    /**
     * Récupère un type d'équipement par son identifiant.
     * @param id identifiant du type d'équipement
     * @return le type d'équipement trouvé
     * @throws ResponseStatusException si l'identifiant n'existe pas
     */
    public TypeEquipement getTypeEquipementById(Integer id){
        return typeEquipementRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre un nouveau type d'équipement.
     * @param typeEquipement objet à enregistrer
     * @return le type d'équipement enregistré
     */
    public TypeEquipement saveTypeEquipement(TypeEquipement typeEquipement){
        return typeEquipementRepo.save(typeEquipement);
    }

    /**
     * Met à jour un type d'équipement existant.
     * @param typeEquipement objet contenant les nouvelles données
     * @return le type d'équipement mis à jour
     * @throws ResponseStatusException si le type n'existe pas
     */
    public TypeEquipement updateTypeEquipement(TypeEquipement typeEquipement){
        if (!typeEquipementRepo.existsById(typeEquipement.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return typeEquipementRepo.save(typeEquipement);
    }

    /**
     * Supprime un type d'équipement par son identifiant.
     * @param id identifiant du type d'équipement à supprimer
     * @throws ResponseStatusException si l'identifiant n'existe pas
     */
    public void deleteTypeEquipementById(Integer id){
        if (!typeEquipementRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        typeEquipementRepo.deleteById(id);
    }
}
