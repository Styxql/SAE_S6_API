package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.usmb.but3.sae_s6_api.repository.UniteMesurerRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service pour la gestion des unités de mesure.
 * Fournit les opérations CRUD de base.
 */
@Service
@RequiredArgsConstructor
public class UniteMesurerService {

    // Repository injecté pour accéder aux données des unités de mesure
    private final UniteMesurerRepo uniteMesurerRepo;

    /**
     * Récupère toutes les unités de mesure.
     * @return liste de toutes les unités de mesure
     */
    public List<UniteMesurer> getAllUniteMesurers(){
        return uniteMesurerRepo.findAll();
    }

    /**
     * Récupère une unité de mesure par son identifiant.
     * @param id identifiant de l'unité
     * @return l'unité trouvée
     * @throws ResponseStatusException si l'identifiant n'existe pas
     */
    public UniteMesurer getUniteMesurerById(Integer id){
        return uniteMesurerRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre une nouvelle unité de mesure.
     * @param uniteMesurer entité à enregistrer
     * @return l'unité de mesure enregistrée
     */
    public UniteMesurer saveUniteMesurer(UniteMesurer uniteMesurer){
        return uniteMesurerRepo.save(uniteMesurer);
    }

    /**
     * Met à jour une unité de mesure existante.
     * @param uniteMesurer entité contenant les données à mettre à jour
     * @return l'unité mise à jour
     * @throws ResponseStatusException si l'unité n'existe pas
     */
    public UniteMesurer updateUniteMesurer(UniteMesurer uniteMesurer){
        if (!uniteMesurerRepo.existsById(uniteMesurer.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return uniteMesurerRepo.save(uniteMesurer);
    }

    /**
     * Supprime une unité de mesure par son identifiant.
     * @param id identifiant de l'unité à supprimer
     * @throws ResponseStatusException si l'unité n'existe pas
     */
    public void deleteUniteMesurerById(Integer id){
        if (!uniteMesurerRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        uniteMesurerRepo.deleteById(id);
    }
}
