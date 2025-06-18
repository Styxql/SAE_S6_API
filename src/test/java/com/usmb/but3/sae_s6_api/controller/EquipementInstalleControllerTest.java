package com.usmb.but3.sae_s6_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.entity.Salle;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EquipementInstalleControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/sae/v1/equipementInstalle";
    }

    @Test
    void testSaveEquipementInstalle() {
        Equipement equipement = new Equipement();
        equipement.setId(1);

        Salle salle = new Salle();
        salle.setId(1);

        EquipementInstalle ei = new EquipementInstalle();
        ei.setNombre(15);
        ei.setEquipement(equipement);
        ei.setSalle(salle);

        EquipementInstalle postEquipement = restTemplate.postForObject(baseUrl(), ei, EquipementInstalle.class);

        assertThat(postEquipement).isNotNull();
        assertThat(postEquipement.getId()).isNotNull();
        assertThat(postEquipement.getNombre()).isEqualTo(15);

        
    }

    @Test
    void testDeleteEquipementInstalleById() {
        Equipement equipement = new Equipement();
        equipement.setId(1); 

        Salle salle = new Salle();
        salle.setId(1); 

        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setNombre(23);
        equipementInstalle.setEquipement(equipement);
        equipementInstalle.setSalle(salle);

        EquipementInstalle created = restTemplate.postForObject(baseUrl(), equipementInstalle,
                EquipementInstalle.class);


        restTemplate.delete(baseUrl() + "/" + created.getId());

        ResponseEntity<EquipementInstalle> response = restTemplate.getForEntity(baseUrl() + "/" + created.getId(),
                EquipementInstalle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetAllEquipementInstalles() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/equipementInstalle",
                EquipementInstalle[].class)).satisfies(equipementInstalles -> {
                    assertThat(equipementInstalles).isNotEmpty();
                    assertThat(equipementInstalles[0].getId()).isEqualTo(1);
                });

    }

    @Test
    void testGetEquipementInstalleById() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/equipementInstalle/5",
                EquipementInstalle.class))
                .satisfies(equipementInstalle -> assertThat(equipementInstalle.getId()).isEqualTo(5));
    }

    @Test
    void testUpdateEquipementInstalle() {
    Equipement equipement = new Equipement();
    equipement.setId(1);

    Salle salle = new Salle();
    salle.setId(1);

    EquipementInstalle ei = new EquipementInstalle();
    ei.setNombre(10);
    ei.setEquipement(equipement);
    ei.setSalle(salle);

    EquipementInstalle created = restTemplate.postForObject(baseUrl(), ei, EquipementInstalle.class);

   

    created.setNombre(99);

    restTemplate.put(baseUrl(), created);

    EquipementInstalle updated = restTemplate.getForObject(baseUrl() + "/" + created.getId(),
            EquipementInstalle.class);
    assertThat(updated).isNotNull();
    assertThat(updated.getNombre()).isEqualTo(99);
    assertThat(updated.getId()).isEqualTo(created.getId());

    }
}