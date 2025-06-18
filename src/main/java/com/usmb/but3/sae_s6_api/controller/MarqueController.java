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

import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.service.MarqueService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/marque")
@RequiredArgsConstructor
@Validated
public class MarqueController {

    private final MarqueService marqueService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/Marque
     * but: Recupere toutes les Marques dans la table Marque
     * @return List de Marques
     */
    @GetMapping("")
    public ResponseEntity<List<Marque>> getallMarque() {
        return ResponseEntity.ok().body(marqueService.getAllMarques());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/marque/{id}
     * But : Récupère une Marque à partir de son identifiant.
     * @param id L'identifiant de Marque
     * @return La Marque correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Marque> getMarqueById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(marqueService.getMarqueById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/marque
     * But : Enregistre une nouvelle Marque dans la base de données.
     * @param Marque L'objet Marque à enregistrer
     * @return La Marque enregistrée avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<Marque> saveMarque(@RequestBody Marque Marque) {
        return ResponseEntity.ok().body(marqueService.saveMarque(Marque));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/marque
     * But : Met à jour une Marque existant dans la base de données.
     * @param Marque L'objet Marque à mettre à jour
     * @return La Marque mis à jour
     */
    @PutMapping("")
    public ResponseEntity<Marque> updateMarque(@RequestBody Marque Marque) {
        return ResponseEntity.ok().body(marqueService.updateMarque(Marque));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/marque/{id}
     * But : Supprime une Marque à partir de son identifiant.
     * @param id L'identifiant de Marque à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMarqueById(@PathVariable("id") Integer id)
    {
       marqueService.deleteMarqueById(id);
       return ResponseEntity.ok().body("Marque supprimée avec succès");
    }

}
