package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.repository.SalleRepo;

import lombok.RequiredArgsConstructor;

/**
 * Service pour la gestion des salles.
 * Fournit les opérations CRUD et des méthodes personnalisées.
 */
@Service
@RequiredArgsConstructor
public class SalleService {

    // Injection du repository pour accéder aux données des salles
    private final SalleRepo salleRepo;

    /**
     * Récupère toutes les salles de la base de données.
     * @return liste de toutes les salles
     */
    public List<Salle> getAllSalles(){
        return salleRepo.findAll();
    }

    /**
     * Récupère une salle par son identifiant.
     * @param id identifiant de la salle
     * @return la salle trouvée
     * @throws ResponseStatusException si la salle n'existe pas
     */
    public Salle getSalleById(Integer id){
        return salleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    /**
     * Enregistre une nouvelle salle.
     * @param Salle objet Salle à enregistrer
     * @return la salle enregistrée
     */
    public Salle saveSalle(Salle Salle){
        return salleRepo.save(Salle);
    }

    /**
     * Met à jour une salle existante.
     * @param Salle salle contenant les nouvelles données
     * @return la salle mise à jour
     * @throws ResponseStatusException si la salle n'existe pas
     */
    public Salle updateSalle(Salle Salle){
        if (!salleRepo.existsById(Salle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return salleRepo.save(Salle);
    }

    /**
     * Supprime une salle par son identifiant.
     * @param id identifiant de la salle à supprimer
     * @throws ResponseStatusException si la salle n'existe pas
     */
    public void deleteSalleById(Integer id){
        if (!salleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        salleRepo.deleteById(id);
    }

    /**
     * Récupère toutes les salles associées à un bâtiment donné.
     * @param batimentId identifiant du bâtiment
     * @return liste des salles du bâtiment
     */
    public List<Salle> getByBatimentId(Integer batimentId) {
        return salleRepo.findByBatimentId(batimentId);
    }  
}
