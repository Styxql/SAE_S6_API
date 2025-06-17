package com.usmb.but3.sae_s6_api.controller;

import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.service.ParametreCapteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/parametrecapteur")
@RequiredArgsConstructor
@Validated
public class ParametreCapteurController {

    private final ParametreCapteurService parametreCapteurService;

    /**
     * GET : Récupère tous les paramètres capteurs
     * URL : /sae/v1/parametre-capteur
     */
    @GetMapping("")
    public ResponseEntity<List<ParametreCapteur>> getAllParametreCapteurs() {
        return ResponseEntity.ok().body(parametreCapteurService.getAllParametreCapteurs());
    }

    /**
     * GET : Récupère un paramètre capteur par ses deux identifiants
     * URL : /sae/v1/parametre-capteur/{capteurId}/{uniteMesurerId}
     */
    @GetMapping("/{capteurId}/{uniteMesurerId}")
    public ResponseEntity<ParametreCapteur> getParametreCapteurById(
            @PathVariable("capteurId") Integer capteurId,
            @PathVariable("uniteMesurerId") Integer uniteMesurerId) {
        return ResponseEntity.ok().body(parametreCapteurService.getParametreCapteurById(capteurId, uniteMesurerId));
    }

    /**
     * POST : Enregistre un nouveau paramètre capteur
     * URL : /sae/v1/parametre-capteur
     */
    @PostMapping("")
    public ResponseEntity<ParametreCapteur> saveParametreCapteur(@RequestBody ParametreCapteur parametreCapteur) {
        return ResponseEntity.ok().body(parametreCapteurService.saveParametreCapteur(parametreCapteur));
    }

    /**
     * PUT : Met à jour un paramètre capteur existant
     * URL : /sae/v1/parametre-capteur
     */
    @PutMapping("")
    public ResponseEntity<ParametreCapteur> updateParametreCapteur(@RequestBody ParametreCapteur parametreCapteur) {
        return ResponseEntity.ok().body(parametreCapteurService.updateParametreCapteur(parametreCapteur));
    }

    /**
     * DELETE : Supprime un paramètre capteur avec ses deux identifiants
     * URL : /sae/v1/parametre-capteur/{capteurId}/{uniteMesurerId}
     */
    @DeleteMapping("/{capteurId}/{uniteMesurerId}")
    public ResponseEntity<String> deleteParametreCapteurById(
            @PathVariable("capteurId") Integer capteurId,
            @PathVariable("uniteMesurerId") Integer uniteMesurerId) {
        parametreCapteurService.deleteParametreCapteurById(capteurId, uniteMesurerId);
        return ResponseEntity.ok().body("Paramètre Capteur supprimé avec succès");
    }
}
