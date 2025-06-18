package com.usmb.but3.sae_s6_api.controller;

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TypeEquipementControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getURL() {
        return "http://localhost:" + port + "/sae/v1/typeEquipement";
    }

    @Test
    void testSaveTypeEquipement() {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setNom("typeEquipement post");

        TypeEquipement savedTypeEquipement = this.restTemplate.postForObject(getURL(), typeEquipement, TypeEquipement.class);

        assertThat(savedTypeEquipement).isNotNull();
        assertThat(savedTypeEquipement.getNom()).isEqualTo("typeEquipement post");
        assertThat(savedTypeEquipement.getId()).isNotNull();
    }

    @Test
    void testGetAllTypeEquipements() {
        TypeEquipement[] typeEquipements = this.restTemplate.getForObject(getURL(), TypeEquipement[].class);
        assertThat(typeEquipements).isNotEmpty();
    }

    @Test
    void testGetTypeEquipementById() {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setNom("typeEquipement getById");

        TypeEquipement saved = this.restTemplate.postForObject(getURL(), typeEquipement, TypeEquipement.class);

        assertThat(this.restTemplate.getForObject(getURL() + "/" + saved.getId(), TypeEquipement.class))
                .satisfies(te -> assertThat(te.getId()).isEqualTo(saved.getId()));
    }

    @Test
    void testUpdateTypeEquipement() {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setNom("typeEquipement put");

        TypeEquipement saved = this.restTemplate.postForObject(getURL(), typeEquipement, TypeEquipement.class);

        saved.setNom("typeEquipement put update");

        this.restTemplate.put(getURL(), saved);

        TypeEquipement updated = this.restTemplate.getForObject(getURL() + "/" + saved.getId(), TypeEquipement.class);

        assertThat(updated.getNom()).isEqualTo("typeEquipement put update");
    }

    @Test
    void testDeleteTypeEquipementById() {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setNom("typeEquipement delete");

        TypeEquipement saved = this.restTemplate.postForObject(getURL(), typeEquipement, TypeEquipement.class);
        Integer id = saved.getId();

        assertThat(saved).isNotNull();
        assertThat(id).isNotNull();

        this.restTemplate.delete(getURL() + "/" + id);

        ResponseEntity<TypeEquipement> response = this.restTemplate.getForEntity(getURL() + "/" + id, TypeEquipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
