package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.repository.MarqueRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service pour la gestion des marques.
 * Permet les opérations CRUD et gère la suppression en cascade manuelle
 * des capteurs et équipements liés à une marque.
 */
@Service
@RequiredArgsConstructor
public class MarqueService {
    // Injection du repository pour accéder aux données des marques
    private final MarqueRepo marqueRepo;

    /**
     * Récupère toutes les marques depuis la base de données.
     * @return liste de toutes les marques
     */
    public List<Marque> getAllMarques(){
        return marqueRepo.findAll();
    }

    /**
     * Récupère une marque à partir de son identifiant.
     * @param id identifiant de la marque
     * @return la marque trouvée
     * @throws ResponseStatusException si la marque n'existe pas
     */
    public Marque getMarqueById(Integer id){
        return marqueRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre une nouvelle marque dans la base de données.
     * @param Marque objet marque à enregistrer
     * @return la marque enregistrée
     */
    public Marque saveMarque(Marque Marque){
        return marqueRepo.save(Marque);
    }

    /**
     * Met à jour une marque existante.
     * @param Marque objet marque avec les nouvelles données
     * @return la marque mise à jour
     * @throws ResponseStatusException si la marque à mettre à jour n'existe pas
     */
    public Marque updateMarque(Marque Marque){
        if (!marqueRepo.existsById(Marque.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return marqueRepo.save(Marque);
    }

    /**
     * Supprime une marque par son identifiant.
     * @param id identifiant de la marque à supprimer
     * @throws ResponseStatusException si la marque à supprimer n'existe pas
     */
    public void deleteMarqueById(Integer id){
        if (!marqueRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        marqueRepo.deleteById(id);
    }
}
