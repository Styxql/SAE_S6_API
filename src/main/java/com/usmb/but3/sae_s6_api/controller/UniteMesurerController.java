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
     * Requête GET
     * URL : localhost:8080/sae/v1/uniteMesurer
     * Récupère toutes les unités de mesure.
     */
    @GetMapping("")
    public ResponseEntity<List<UniteMesurer>> getAllUniteMesurer() {
        return ResponseEntity.ok().body(uniteMesurerService.getAllUniteMesurers());
    }

    /**
     * Requête GET
     * URL : localhost:8080/sae/v1/uniteMesurer/{id}
     * Récupère une unité de mesure par son identifiant.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UniteMesurer> getUniteMesurerById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(uniteMesurerService.getUniteMesurerById(id));
    }

    /**
     * Requête POST
     * URL : localhost:8080/sae/v1/uniteMesurer
     * Enregistre une nouvelle unité de mesure.
     */
    @PostMapping("")
    public ResponseEntity<UniteMesurer> saveUniteMesurer(@RequestBody UniteMesurer uniteMesurer) {
        return ResponseEntity.ok().body(uniteMesurerService.saveUniteMesurer(uniteMesurer));
    }

    /**
     * Requête PUT
     * URL : localhost:8080/sae/v1/uniteMesurer
     * Met à jour une unité de mesure existante.
     */
    @PutMapping("")
    public ResponseEntity<UniteMesurer> updateUniteMesurer(@RequestBody UniteMesurer uniteMesurer) {
        return ResponseEntity.ok().body(uniteMesurerService.updateUniteMesurer(uniteMesurer));
    }

    /**
     * Requête DELETE
     * URL : localhost:8080/sae/v1/uniteMesurer/{id}
     * Supprime une unité de mesure par son identifiant.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUniteMesurerById(@PathVariable("id") Integer id) {
        uniteMesurerService.deleteUniteMesurerById(id);
        return ResponseEntity.ok().body("UniteMesurer supprimée avec succès");
    }
}
