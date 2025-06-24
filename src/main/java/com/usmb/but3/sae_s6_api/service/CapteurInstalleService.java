package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.repository.CapteurInstalleRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service métier pour la gestion des capteurs installés.
 * Fournit les opérations CRUD et quelques recherches spécifiques.
 */
@Service
@RequiredArgsConstructor
public class CapteurInstalleService {

    private final CapteurInstalleRepo capteurInstalleRepo;

    /**
     * Récupère tous les capteurs installés.
     * @return Liste de tous les capteurs installés.
     */
    public List<CapteurInstalle> getAllCapteurInstalles() {
        return capteurInstalleRepo.findAll();
    }

    /**
     * Récupère un capteur installé par son ID.
     * @param id Identifiant du capteur installé.
     * @return Capteur installé correspondant.
     * @throws ResponseStatusException si l’ID est introuvable.
     */
    public CapteurInstalle getCapteurInstalleById(Integer id) {
        return capteurInstalleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre un nouveau capteur installé.
     * @param capteurInstalle L’objet à enregistrer.
     * @return L’objet sauvegardé avec son ID généré.
     */
    public CapteurInstalle saveCapteurInstalle(CapteurInstalle capteurInstalle) {
        return capteurInstalleRepo.save(capteurInstalle);
    }

    /**
     * Met à jour un capteur installé existant.
     * @param capteurInstalle L’objet mis à jour.
     * @return L’objet sauvegardé.
     * @throws ResponseStatusException si l’ID est introuvable.
     */
    public CapteurInstalle updateCapteurInstalle(CapteurInstalle capteurInstalle) {
        if (!capteurInstalleRepo.existsById(capteurInstalle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        return capteurInstalleRepo.save(capteurInstalle);
    }

    /**
     * Supprime un capteur installé par son ID.
     * @param id Identifiant de l’élément à supprimer.
     * @throws ResponseStatusException si l’ID est introuvable.
     */
    public void deleteCapteurInstalleById(Integer id) {
        if (!capteurInstalleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        capteurInstalleRepo.deleteById(id);
    }

    /**
     * Récupère tous les capteurs installés dans une salle donnée.
     * @param salleId ID de la salle.
     * @return Liste des capteurs installés dans cette salle.
     */
    public List<CapteurInstalle> getBySalleId(Integer salleId) {
        return capteurInstalleRepo.findBySalleId(salleId);
    }
}
