package com.usmb.but3.sae_s6_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BatimentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getURL()
    {
        return "http://localhost:" + port + "/sae/v1/batiment";
    }

    @Test
    void testDeleteBatimentById() {
        Batiment batiment = new Batiment();
        batiment.setNom("ToDelete");
        batiment.setUrlImg("https://img.todelete.fr");

        Batiment savedBatiment = this.restTemplate.postForObject(getURL(), batiment, Batiment.class);
        Integer id = savedBatiment.getId();

        assertThat(savedBatiment).isNotNull();
        assertThat(id).isNotNull();

        this.restTemplate.delete(getURL() + "/" + id);

        ResponseEntity<Batiment> response = this.restTemplate.getForEntity(getURL() + "/" + id, Batiment.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    

    @Test
    void testGetBatimentById() {
    assertThat(
            this.restTemplate.getForObject(getURL() + "/1", Batiment.class)
            ).satisfies(
                batiment -> assertThat(batiment.getId()).isEqualTo(1)
                );
    }

    @Test
    void testGetallBatiment() {
        Batiment[] batiments = restTemplate.getForObject(getURL(), Batiment[].class);
        assertThat(batiments).isNotEmpty();
    }

    @Test
    void testSaveBatiment() {
        Batiment batiment = new Batiment();
        batiment.setNom("H");
        batiment.setUrlImg("https://img.example.fr");

        Batiment postBatiment = this.restTemplate.postForObject(getURL(), batiment, Batiment.class);

        assertThat(postBatiment.getNom()).isEqualTo(batiment.getNom());
        assertThat(postBatiment.getUrlImg()).isEqualTo(batiment.getUrlImg());
    }

    @Test
    void testUpdateBatiment() {
        Batiment batiment = new Batiment();
        batiment.setNom("A");
        batiment.setUrlImg("https://img.example.fr");
    
        Batiment batimentSave = this.restTemplate.postForObject(getURL(), batiment, Batiment.class);

        batimentSave.setNom("A update");
        this.restTemplate.put(getURL(), batimentSave);
    
        Batiment updated = restTemplate.getForObject(getURL() + "/" + batimentSave.getId(), Batiment.class);
        assertThat(updated.getNom()).isEqualTo("A update");
        assertThat(updated.getUrlImg()).isEqualTo("https://img.example.fr");
    }
    
}
