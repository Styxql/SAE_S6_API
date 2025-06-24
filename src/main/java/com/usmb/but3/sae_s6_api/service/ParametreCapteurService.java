package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.entity.ParametreCapteurId;
import com.usmb.but3.sae_s6_api.repository.ParametreCapteurRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service pour la gestion des paramètres de capteurs.
 * Fournit les opérations CRUD sur les entités ParametreCapteur.
 */
@Service
@RequiredArgsConstructor
public class ParametreCapteurService {

    // Injection du repository pour les paramètres de capteurs
    private final ParametreCapteurRepo parametreCapteurRepo;

    /**
     * Récupère tous les paramètres de capteurs.
     * @return liste de tous les ParametreCapteur
     */
    public List<ParametreCapteur> getAllParametreCapteurs() {
        return parametreCapteurRepo.findAll();
    }

    /**
     * Récupère un paramètre de capteur à partir de son identifiant composite.
     * @param capteurId identifiant du capteur
     * @param uniteMesurerId identifiant de l'unité mesurée
     * @return le ParametreCapteur correspondant
     * @throws ResponseStatusException si aucun paramètre n'est trouvé
     */
    public ParametreCapteur getParametreCapteurById(Integer capteurId, Integer uniteMesurerId) {
        ParametreCapteurId id = new ParametreCapteurId(uniteMesurerId, capteurId);
        return parametreCapteurRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found"));
    }

    /**
     * Enregistre un nouveau paramètre de capteur.
     * @param parametreCapteur l'entité à enregistrer
     * @return l'entité enregistrée
     */
    public ParametreCapteur saveParametreCapteur(ParametreCapteur parametreCapteur) {
        return parametreCapteurRepo.save(parametreCapteur);
    }

    /**
     * Met à jour un paramètre de capteur existant.
     * @param parametreCapteur l'entité avec les nouvelles données
     * @return l'entité mise à jour
     * @throws ResponseStatusException si le paramètre n'existe pas
     */
    public ParametreCapteur updateParametreCapteur(ParametreCapteur parametreCapteur) {
        ParametreCapteurId id = new ParametreCapteurId(
            parametreCapteur.getUniteMesurerId(), 
            parametreCapteur.getCapteurId()
        );
        if (!parametreCapteurRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return parametreCapteurRepo.save(parametreCapteur);
    }

    /**
     * Supprime un paramètre de capteur à partir de son identifiant composite.
     * @param capteurId identifiant du capteur
     * @param uniteMesurerId identifiant de l'unité mesurée
     * @throws ResponseStatusException si l'entité à supprimer n'existe pas
     */
    public void deleteParametreCapteurById(Integer capteurId, Integer uniteMesurerId) {
        ParametreCapteurId id = new ParametreCapteurId(uniteMesurerId, capteurId);
        if (!parametreCapteurRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        parametreCapteurRepo.deleteById(id);
    }
}
