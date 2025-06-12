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

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.service.CapteurService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/capteur")
@RequiredArgsConstructor
@Validated
public class CapteurController {

    private final CapteurService capteurService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/capteur
     * but: Recupere tous les capteurs dans la table capteur
     * @return List de capteurs
     */
    @GetMapping("")
    public ResponseEntity<List<Capteur>> getallCapteur() {
        return ResponseEntity.ok().body(capteurService.getAllCapteurs());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/capteur/{id}
     * But : Récupère un capteur à partir de son identifiant.
     * @param id L'identifiant du capteur
     * @return Le capteur correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Capteur> getCapteurById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(capteurService.getCapteurById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/capteur
     * But : Enregistre un nouveau capteur dans la base de données.
     * @param capteur L'objet capteur à enregistrer
     * @return Le capteur enregistré avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<Capteur> saveCapteur(@RequestBody Capteur capteur) {
        return ResponseEntity.ok().body(capteurService.saveCapteur(capteur));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/capteur
     * But : Met à jour un capteur existant dans la base de données.
     * @param capteur L'objet capteur à mettre à jour
     * @return Le capteur mis à jour
     */
    @PutMapping("")
    public ResponseEntity<Capteur> updateCapteur(@RequestBody Capteur capteur) {
        return ResponseEntity.ok().body(capteurService.updateCapteur(capteur));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/capteur/{id}
     * But : Supprime un capteur à partir de son identifiant.
     * @param id L'identifiant du capteur à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapteurById(@PathVariable("id") Integer id)
    {
       capteurService.deleteCapteurById(id);
       return ResponseEntity.ok().body("Capteur supprimé avec succès");
    }

}
