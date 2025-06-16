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

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.service.TypeSalleService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/sae/v1/typeSalle")
@RequiredArgsConstructor
@Validated
public class TypeSalleController {

    private final TypeSalleService typeSalleService;

    /**
     * Cette methode est appelle lors d'une requete GET
     * URL: localhost:8080/sae/v1/typeSalle
     * but: Recupere tous les typeSalle dans la table typeSalle
     * @return List de typeSalle
     */
    @GetMapping("")
    public ResponseEntity<List<TypeSalle>> getallTypeSalle() {
        return ResponseEntity.ok().body(typeSalleService.getAllTypeSalles());
    }
    
    /**
     * Cette méthode est appelée lors d'une requête GET.
     * URL : localhost:8080/sae/v1/typeSalle/{id}
     * But : Récupère un typeSalle à partir de son identifiant.
     * @param id L'identifiant du typeSalle
     * @return Le typeSalle correspondant à l'id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeSalle> getTypeSalleById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(typeSalleService.getTypeSalleById(id));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête POST.
     * URL : localhost:8080/sae/v1/typeSalle
     * But : Enregistre un nouveau typeSalle dans la base de données.
     * @param typeSalle L'objet typeSalle à enregistrer
     * @return Le typeSalle enregistré avec son identifiant
     */
    @PostMapping("")
    public ResponseEntity<TypeSalle> saveTypeSalle(@RequestBody TypeSalle typeSalle) {
        return ResponseEntity.ok().body(typeSalleService.saveTypeSalle(typeSalle));
    }
    
    /**
     * Cette méthode est appelée lors d'une requête PUT.
     * URL : localhost:8080/sae/v1/typeSalle
     * But : Met à jour un typeSalle existant dans la base de données.
     * @param typeSalle L'objet typeSalle à mettre à jour
     * @return Le typeSalle mis à jour
     */
    @PutMapping("")
    public ResponseEntity<TypeSalle> updateTypeSalle(@RequestBody TypeSalle typeSalle) {
        return ResponseEntity.ok().body(typeSalleService.updateTypeSalle(typeSalle));
    }

    /**
     * Cette méthode est appelée lors d'une requête DELETE.
     * URL : localhost:8080/sae/v1/typeSalle/{id}
     * But : Supprime un typeSalle à partir de son identifiant.
     * @param id L'identifiant du typeSalle à supprimer
     * @return Message de confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeSalleById(@PathVariable("id") Integer id)
    {
       typeSalleService.deleteTypeSalleById(id);
       return ResponseEntity.ok().body("TypeSalle supprimé avec succès");
    }

}
