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

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.usmb.but3.sae_s6_api.service.UniteMesurerService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/uniteMesurer")
@RequiredArgsConstructor
@Validated
public class UniteMesurerController {

    private final UniteMesurerService uniteMesurerService;

    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/uniteMesurer
     * But : Récupère toutes les unités de mesure dans la table unite_mesurer.
     * @return Liste des unités de mesure.
     */
    @GetMapping("")
    public ResponseEntity<List<UniteMesurer>> getAllUniteMesurer() {
        return ResponseEntity.ok().body(uniteMesurerService.getAllUniteMesurers());
    }

    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/uniteMesurer/{id}
     * But : Récupère une unité de mesure à partir de son identifiant.
     * @param id L'identifiant de l'unité de mesure.
     * @return L'unité de mesure correspondant à l'id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UniteMesurer> getUniteMesurerById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(uniteMesurerService.getUniteMesurerById(id));
    }

    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/uniteMesurer
     * But : Enregistre une nouvelle unité de mesure dans la base de données.
     * @param uniteMesurer L'objet unité de mesure à enregistrer.
     * @return L'unité de mesure enregistrée avec son identifiant.
     */
    @PostMapping("")
    public ResponseEntity<UniteMesurer> saveUniteMesurer(@RequestBody UniteMesurer uniteMesurer) {
        return ResponseEntity.ok().body(uniteMesurerService.saveUniteMesurer(uniteMesurer));
    }

    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/uniteMesurer
     * But : Met à jour une unité de mesure existante dans la base de données.
     * @param uniteMesurer L'objet unité de mesure à mettre à jour.
     * @return L'unité de mesure mise à jour.
     */
    @PutMapping("")
    public ResponseEntity<UniteMesurer> updateUniteMesurer(@RequestBody UniteMesurer uniteMesurer) {
        return ResponseEntity.ok().body(uniteMesurerService.updateUniteMesurer(uniteMesurer));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/uniteMesurer/{id}
     * But : Supprime une unité de mesure à partir de son identifiant.
     * @param id L'identifiant de l'unité de mesure à supprimer.
     * @return Message de confirmation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUniteMesurerById(@PathVariable("id") Integer id) {
        uniteMesurerService.deleteUniteMesurerById(id);
        return ResponseEntity.ok().body("UniteMesurer supprimée avec succès");
    }
}
