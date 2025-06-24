package com.usmb.but3.sae_s6_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.service.SalleService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/salle")
@RequiredArgsConstructor
@Validated
public class SalleController {

    private final SalleService salleService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/Salle
     * but: Recupere tous les Salles dans la table Salle
     * @return List de Salles
     */
    @GetMapping("")
    public ResponseEntity<List<Salle>> getallSalle() {
        return ResponseEntity.ok().body(salleService.getAllSalles());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/salle/{id}
     * But : Récupère une Salle à partir de son identifiant.
     * @param id L'identifiant de Salle
     * @return La Salle correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(salleService.getSalleById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/salle
     * But : Enregistre une nouvelle Salle dans la base de données.
     * @param Salle L'objet Salle à enregistrer
     * @return La Salle enregistrée avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<Salle> saveSalle(@RequestBody Salle Salle) {
        return ResponseEntity.ok().body(salleService.saveSalle(Salle));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/salle
     * But : Met à jour une Salle existant dans la base de données.
     * @param Salle L'objet Salle à mettre à jour
     * @return La Salle mis à jour
     */
    @PutMapping("")
    public ResponseEntity<Salle> updateSalle(@RequestBody Salle Salle) {
        return ResponseEntity.ok().body(salleService.updateSalle(Salle));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/salle/{id}
     * But : Supprime une Salle à partir de son identifiant.
     * @param id L'identifiant de Salle à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalleById(@PathVariable("id") Integer id)
    {
       salleService.deleteSalleById(id);
       return ResponseEntity.ok().body("Salle supprimée avec succès");
    }

    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/getByBatimentId/{batimentId}
     * But : Recupere toutes les salles dans le batiment avec l'id 
     * @param batimentId L'identifiant de batiment
     * @return Message de confirmation
     */
    @GetMapping("/getByBatimentId/{batimentId}")
    public ResponseEntity<List<Salle>> getSalleByBatimentId(@PathVariable("batimentId") Integer batimentId) {
        return ResponseEntity.ok().body(salleService.getByBatimentId(batimentId));
    }
}
