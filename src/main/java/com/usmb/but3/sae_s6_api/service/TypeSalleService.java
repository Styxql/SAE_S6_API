package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.repository.TypeSalleRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service pour la gestion des types de salles.
 * Fournit les opérations CRUD de base.
 */
@Service
@RequiredArgsConstructor
public class TypeSalleService {

    // Repository injecté pour accéder aux données des types de salles
    private final TypeSalleRepo typeSalleRepo;

    /**
     * Récupère tous les types de salles.
     * @return liste de tous les types de salles
     */
    public List<TypeSalle> getAllTypeSalles(){
        return typeSalleRepo.findAll();
    }

    /**
     * Récupère un type de salle par son identifiant.
     * @param id identifiant du type de salle
     * @return le type de salle correspondant
     * @throws ResponseStatusException si l'identifiant n'existe pas
     */
    public TypeSalle getTypeSalleById(Integer id){
        return typeSalleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre un nouveau type de salle.
     * @param typeSalle entité à enregistrer
     * @return le type de salle enregistré
     */
    public TypeSalle saveTypeSalle(TypeSalle typeSalle){
        return typeSalleRepo.save(typeSalle);
    }

    /**
     * Met à jour un type de salle existant.
     * @param typeSalle entité contenant les données mises à jour
     * @return le type de salle mis à jour
     * @throws ResponseStatusException si le type de salle n'existe pas
     */
    public TypeSalle updateTypeSalle(TypeSalle typeSalle){
        if (!typeSalleRepo.existsById(typeSalle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return typeSalleRepo.save(typeSalle);
    }

    /**
     * Supprime un type de salle par son identifiant.
     * @param id identifiant du type de salle à supprimer
     * @throws ResponseStatusException si le type de salle n'existe pas
     */
    public void deleteTypeSalleById(Integer id){
        if (!typeSalleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        typeSalleRepo.deleteById(id);
    }
}
