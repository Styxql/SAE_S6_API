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

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.service.TypeEquipementService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/typeEquipement")
@RequiredArgsConstructor
@Validated
public class TypeEquipementController {

    private final TypeEquipementService typeEquipementService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/typeEquipement
     * but: Recupere tous les typeEquipement dans la table typeEquipement
     * @return List de typeEquipements
     */
    @GetMapping("")
    public ResponseEntity<List<TypeEquipement>> getAllTypeEquipement() {
        return ResponseEntity.ok().body(typeEquipementService.getAllTypeEquipement());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/typeEquipement/{id}
     * But : Récupère un typeEquipement à partir de son identifiant.
     * @param id L'identifiant du typeEquipement
     * @return Le typeEquipement correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeEquipement> getTypeEquipementById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(typeEquipementService.getTypeEquipementById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/typeEquipement
     * But : Enregistre un nouveau typeEquipement dans la base de données.
     * @param typeEquipement L'objet typeEquipement à enregistrer
     * @return Le typeEquipement enregistré avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<TypeEquipement> saveTypeEquipement(@RequestBody TypeEquipement typeEquipement) {
        return ResponseEntity.ok().body(typeEquipementService.saveTypeEquipement(typeEquipement));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/typeEquipement
     * But : Met à jour un typeEquipement existant dans la base de données.
     * @param typeEquipement L'objet typeEquipement à mettre à jour
     * @return Le typeEquipement mis à jour
     */
    @PutMapping("")
    public ResponseEntity<TypeEquipement> updateTypeEquipement(@RequestBody TypeEquipement typeEquipement) {
        return ResponseEntity.ok().body(typeEquipementService.updateTypeEquipement(typeEquipement));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/typeEquipement/{id}
     * But : Supprime un typeEquipement à partir de son identifiant.
     * @param id L'identifiant du typeEquipement à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeEquipementtById(@PathVariable("id") Integer id)
    {
        typeEquipementService.deleteTypeEquipementtById(id);
       return ResponseEntity.ok().body("TypeEquipement supprimé avec succès");
    }

}
