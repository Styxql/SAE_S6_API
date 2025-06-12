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

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.service.EquipementService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/equipement")
@RequiredArgsConstructor
@Validated
public class EquipementController {

    private final EquipementService equipementService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/equipement
     * but: Recupere tous les equipements dans la table equipement
     * @return List de equipements
     */
    @GetMapping("")
    public ResponseEntity<List<Equipement>> getallEquipement() {
        return ResponseEntity.ok().body(equipementService.getAllEquipements());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/equipement/{id}
     * But : Récupère un equipement à partir de son identifiant.
     * @param id L'identifiant du equipement
     * @return Le equipement correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getEquipementById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(equipementService.getEquipementById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/equipement
     * But : Enregistre un nouveau equipement dans la base de données.
     * @param equipement L'objet equipement à enregistrer
     * @return Le equipement enregistré avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<Equipement> saveEquipement(@RequestBody Equipement equipement) {
        return ResponseEntity.ok().body(equipementService.saveEquipement(equipement));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/equipement
     * But : Met à jour un equipement existant dans la base de données.
     * @param equipement L'objet equipement à mettre à jour
     * @return Le equipement mis à jour
     */
    @PutMapping("")
    public ResponseEntity<Equipement> updateEquipement(@RequestBody Equipement equipement) {
        return ResponseEntity.ok().body(equipementService.updateEquipement(equipement));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/equipement/{id}
     * But : Supprime un equipement à partir de son identifiant.
     * @param id L'identifiant du equipement à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipementById(@PathVariable("id") Integer id)
    {
       equipementService.deleteEquipementById(id);
       return ResponseEntity.ok().body("Equipement supprimé avec succès");
    }

}
