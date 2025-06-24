package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.repository.CapteurInstalleRepo;
import com.usmb.but3.sae_s6_api.repository.CapteurRepo;
import com.usmb.but3.sae_s6_api.repository.ParametreCapteurRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service métier pour la gestion des capteurs.
 * Fournit les opérations CRUD ainsi que des recherches spécifiques.
 */
@Service
@RequiredArgsConstructor
public class CapteurService {

    private final CapteurRepo capteurRepo;
    private final CapteurInstalleRepo capteurInstalleRepo;
    private final ParametreCapteurRepo parametreCapteurRepo;

    /**
     * Récupère tous les capteurs enregistrés.
     * @return Liste de tous les capteurs.
     */
    public List<Capteur> getAllCapteurs() {
        return capteurRepo.findAll();
    }

    /**
     * Récupère un capteur par son identifiant.
     * @param id ID du capteur.
     * @return Capteur correspondant.
     * @throws ResponseStatusException si l’ID n’est pas trouvé.
     */
    public Capteur getCapteurById(Integer id) {
        return capteurRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre un nouveau capteur.
     * @param capteur Capteur à enregistrer.
     * @return Le capteur enregistré.
     */
    public Capteur saveCapteur(Capteur capteur) {
        return capteurRepo.save(capteur);
    }

    /**
     * Met à jour un capteur existant.
     * @param capteur Capteur mis à jour.
     * @return Le capteur sauvegardé.
     * @throws ResponseStatusException si l’ID n’existe pas.
     */
    public Capteur updateCapteur(Capteur capteur) {
        if (!capteurRepo.existsById(capteur.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        return capteurRepo.save(capteur);
    }

    /**
     * Supprime un capteur et ses dépendances associées (installations et paramètres).
     * @param id ID du capteur à supprimer.
     * @throws ResponseStatusException si l’ID est introuvable.
     */
    public void deleteCapteurById(Integer id) {
        List<CapteurInstalle> installs = capteurInstalleRepo.findByCapteurId(id);
        capteurInstalleRepo.deleteAll(installs);

        List<ParametreCapteur> param = parametreCapteurRepo.findByCapteurId(id);
        parametreCapteurRepo.deleteAll(param);

        if (!capteurRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Equipement non trouvé");
        }

        capteurRepo.deleteById(id);
    }

    /**
     * Récupère un capteur avec ses paramètres associés (fetch join).
     * @param id ID du capteur.
     * @return Capteur avec la liste de ses ParametreCapteur.
     * @throws EntityNotFoundException si l’ID est introuvable.
     */
    public Capteur getCapteurByIdWithParam(Integer id) {
        return capteurRepo.findByIdWithParametres(id)
                .orElseThrow(() -> new EntityNotFoundException("Capteur non trouvé avec id " + id));
    }
}
