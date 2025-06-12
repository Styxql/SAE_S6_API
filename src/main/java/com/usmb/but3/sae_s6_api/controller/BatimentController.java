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

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.service.BatimentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/batiment")
@RequiredArgsConstructor
@Validated
public class BatimentController {

    private final BatimentService batimentService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/batiment
     * but: Recupere tous les batiments dans la table batiment
     * @return List de batiments
     */
    @GetMapping("")
    public ResponseEntity<List<Batiment>> getallBatiment() {
        return ResponseEntity.ok().body(batimentService.getAllBatiments());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/batiment/{id}
     * But : Récupère un batiment à partir de son identifiant.
     * @param id L'identifiant du batiment
     * @return Le batiment correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Batiment> getBatimentById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(batimentService.getBatimentById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/batiment
     * But : Enregistre un nouveau batiment dans la base de données.
     * @param batiment L'objet batiment à enregistrer
     * @return Le batiment enregistré avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<Batiment> saveBatiment(@RequestBody Batiment batiment) {
        return ResponseEntity.ok().body(batimentService.saveBatiment(batiment));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/batiment
     * But : Met à jour un batiment existant dans la base de données.
     * @param batiment L'objet batiment à mettre à jour
     * @return Le batiment mis à jour
     */
    @PutMapping("")
    public ResponseEntity<Batiment> updateBatiment(@RequestBody Batiment batiment) {
        return ResponseEntity.ok().body(batimentService.updateBatiment(batiment));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/batiment/{id}
     * But : Supprime un batiment à partir de son identifiant.
     * @param id L'identifiant du batiment à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBatimentById(@PathVariable("id") Integer id)
    {
       batimentService.deleteBatimentById(id);
       return ResponseEntity.ok().body("Batiment supprimé avec succès");
    }

}
