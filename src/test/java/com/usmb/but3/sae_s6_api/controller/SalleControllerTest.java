package com.usmb.but3.sae_s6_api.controller;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.entity.TypeSalle;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SalleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/sae/v1/salle";
    }

    @Test
    void testGetAllSalle() {
        Salle[] salles = restTemplate.getForObject(baseUrl(), Salle[].class);
        assertThat(salles).isNotEmpty();
    }

    @Test
    void testGetSalleById() {
        Salle salle = restTemplate.getForObject(baseUrl() + "/5", Salle.class);
        assertThat(salle).isNotNull();
        assertThat(salle.getId()).isEqualTo(5);
    }

    @Test
    void testSaveSalle() {
        Salle salle = new Salle();
        salle.setNom("Salle Test");
        salle.setCapacite(50);
        salle.setUrlImg(null);
        salle.setBatiment(new Batiment(1, null, null));  
        salle.setTypeSalle(new TypeSalle(1, null));     

        Salle created = restTemplate.postForObject(baseUrl(), salle, Salle.class);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getNom()).isEqualTo("Salle Test");
        assertThat(created.getCapacite()).isEqualTo(50);
    }

    @Test
    void testUpdateSalle() {
        Salle salle = new Salle();
        salle.setNom("Salle Initiale");
        salle.setCapacite(20);
        salle.setBatiment(new Batiment(1, null, null));
        salle.setTypeSalle(new TypeSalle(1, null));

        Salle created = restTemplate.postForObject(baseUrl(), salle, Salle.class);
        assertThat(created).isNotNull();

        created.setNom("Salle Modifiée");
        created.setCapacite(60);
        restTemplate.put(baseUrl(), created);

        Salle updated = restTemplate.getForObject(baseUrl() + "/" + created.getId(), Salle.class);
        assertThat(updated).isNotNull();
        assertThat(updated.getNom()).isEqualTo("Salle Modifiée");
        assertThat(updated.getCapacite()).isEqualTo(60);
    }

    @Test
    void testDeleteSalleById() {
        Salle salle = new Salle();
        salle.setNom("Salle À Supprimer");
        salle.setCapacite(30);
        salle.setBatiment(new Batiment(1, null, null));
        salle.setTypeSalle(new TypeSalle(1, null));

        Salle created = restTemplate.postForObject(baseUrl(), salle, Salle.class);
        assertThat(created).isNotNull();

        restTemplate.delete(baseUrl() + "/" + created.getId());

        ResponseEntity<Salle> response = restTemplate.getForEntity(baseUrl() + "/" + created.getId(), Salle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
