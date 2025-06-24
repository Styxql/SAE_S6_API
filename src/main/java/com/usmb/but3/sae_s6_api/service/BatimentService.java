package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.repository.BatimentRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service métier pour la gestion des entités {@link Batiment}.
 * Fournit les opérations CRUD de base.
 */
@Service
@RequiredArgsConstructor
public class BatimentService {

    private final BatimentRepo batimentRepo;

    /**
     * Récupère la liste complète des bâtiments.
     * @return Liste de tous les bâtiments présents en base.
     */
    public List<Batiment> getAllBatiments() {
        return batimentRepo.findAll();
    }

    /**
     * Récupère un bâtiment par son identifiant.
     * @param id Identifiant du bâtiment.
     * @return Le bâtiment correspondant.
     * @throws ResponseStatusException si l'identifiant est introuvable.
     */
    public Batiment getBatimentById(Integer id) {
        return batimentRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre un nouveau bâtiment en base.
     * @param batiment Le bâtiment à sauvegarder.
     * @return Le bâtiment sauvegardé avec son ID généré.
     */
    public Batiment saveBatiment(Batiment batiment) {
        return batimentRepo.save(batiment);
    }

    /**
     * Met à jour un bâtiment existant.
     * @param batiment Le bâtiment à mettre à jour.
     * @return Le bâtiment mis à jour.
     * @throws ResponseStatusException si l'identifiant est introuvable.
     */
    public Batiment updateBatiment(Batiment batiment) {
        if (!batimentRepo.existsById(batiment.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        return batimentRepo.save(batiment);
    }

    /**
     * Supprime un bâtiment par son identifiant.
     * @param id Identifiant du bâtiment à supprimer.
     * @throws ResponseStatusException si l'identifiant est introuvable.
     */
    public void deleteBatimentById(Integer id) {
        if (!batimentRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        batimentRepo.deleteById(id);
    }
}
