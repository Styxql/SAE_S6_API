package com.usmb.but3.sae_s6_api.controller;


import java.util.List;

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

import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.service.EquipementInstalleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sae/v1/equipementInstalle")
@RequiredArgsConstructor
@Validated
public class EquipementInstalleController {

    private final EquipementInstalleService equipementInstalleService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/EquipementInstalle
     * but: Recupere tout les EquipementInstalles dans la table EquipementInstalle
     * @return List d'EquipementInstalles
     */
    @GetMapping("")
    public ResponseEntity<List<EquipementInstalle>> getallEquipementInstalle() {
        return ResponseEntity.ok().body(equipementInstalleService.getAllEquipementInstalles());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/equipementInstalle/{id}
     * But : Récupère un EquipementInstalle à partir de son identifiant.
     * @param id L'identifiant de EquipementInstalle
     * @return L'EquipementInstalle correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipementInstalle> getEquipementInstalleById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(equipementInstalleService.getEquipementInstalleById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/equipementInstalle
     * But : Enregistre un nouvel EquipementInstalle dans la base de données.
     * @param EquipementInstalle L'objet EquipementInstalle à enregistrer
     * @return La EquipementInstalle enregistrée avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<EquipementInstalle> saveEquipementInstalle(@RequestBody EquipementInstalle EquipementInstalle) {
        return ResponseEntity.ok().body(equipementInstalleService.saveEquipementInstalle(EquipementInstalle));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/equipementInstalle
     * But : Met à jour un EquipementInstalle existant dans la base de données.
     * @param EquipementInstalle L'objet EquipementInstalle à mettre à jour
     * @return L'EquipementInstalle mis à jour
     */
    @PutMapping("")
    public ResponseEntity<EquipementInstalle> updateEquipementInstalle(@RequestBody EquipementInstalle EquipementInstalle) {
        return ResponseEntity.ok().body(equipementInstalleService.updateEquipementInstalle(EquipementInstalle));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/equipementInstalle/{id}
     * But : Supprime un EquipementInstalle à partir de son identifiant.
     * @param id L'identifiant de EquipementInstalle à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipementInstalleById(@PathVariable("id") Integer id)
    {
       equipementInstalleService.deleteEquipementInstalleById(id);
       return ResponseEntity.ok().body("EquipementInstalle supprimé avec succès");
    }

    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/equipementInstalle/getBySalleId/{salleId}
     * But : Recupere toutes les salles dans le batiment avec l'id 
     * @param id L'identifiant de batiment
     * @return Message de confirmation
     */
    @GetMapping("/getBySalleId/{salleId}")
    public ResponseEntity<List<EquipementInstalle>> getEquipementInstalleBySalleId(@PathVariable("salleId") Integer salleId) {
        return ResponseEntity.ok().body(equipementInstalleService.getBySalleId(salleId));
    }
}
