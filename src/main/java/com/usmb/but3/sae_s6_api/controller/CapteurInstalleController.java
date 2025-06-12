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

import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.service.CapteurInstalleService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/capteurInstalle")
@RequiredArgsConstructor
@Validated
public class CapteurInstalleController {

    private final CapteurInstalleService capteurInstalleService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/capteurInstalle
     * but: Recupere tous les capteurInstalles dans la table capteurInstalle
     * @return List de capteurInstalles
     */
    @GetMapping("")
    public ResponseEntity<List<CapteurInstalle>> getallCapteurInstalle() {
        return ResponseEntity.ok().body(capteurInstalleService.getAllCapteurInstalles());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/capteurInstalle/{id}
     * But : Récupère un capteurInstalle à partir de son identifiant.
     * @param id L'identifiant du capteurInstalle
     * @return Le capteurInstalle correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<CapteurInstalle> getCapteurInstalleById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(capteurInstalleService.getCapteurInstalleById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/capteurInstalle
     * But : Enregistre un nouveau capteurInstalle dans la base de données.
     * @param capteurInstalle L'objet capteurInstalle à enregistrer
     * @return Le capteurInstalle enregistré avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<CapteurInstalle> saveCapteurInstalle(@RequestBody CapteurInstalle capteurInstalle) {
        return ResponseEntity.ok().body(capteurInstalleService.saveCapteurInstalle(capteurInstalle));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/capteurInstalle
     * But : Met à jour un capteurInstalle existant dans la base de données.
     * @param capteurInstalle L'objet capteurInstalle à mettre à jour
     * @return Le capteurInstalle mis à jour
     */
    @PutMapping("")
    public ResponseEntity<CapteurInstalle> updateCapteurInstalle(@RequestBody CapteurInstalle capteurInstalle) {
        return ResponseEntity.ok().body(capteurInstalleService.updateCapteurInstalle(capteurInstalle));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/capteurInstalle/{id}
     * But : Supprime un capteurInstalle à partir de son identifiant.
     * @param id L'identifiant du capteurInstalle à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapteurInstalleById(@PathVariable("id") Integer id)
    {
       capteurInstalleService.deleteCapteurInstalleById(id);
       return ResponseEntity.ok().body("CapteurInstalle supprimé avec succès");
    }

}
