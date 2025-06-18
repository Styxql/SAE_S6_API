package com.usmb.but3.sae_s6_api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.usmb.but3.sae_s6_api.entity.Marque;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MarqueControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/sae/v1/marque";
    }

    @Test
    void testGetallMarque() {
        Marque[] marques = restTemplate.getForObject(baseUrl(), Marque[].class);
        assertThat(marques).isNotEmpty();
    }

    @Test
    void testGetMarqueById() {
        assertThat(this.restTemplate.getForObject(baseUrl() + "/5",
                Marque.class)).satisfies(marque -> assertThat(marque.getId()).isEqualTo(5));
    }

    @Test
    void testUpdateMarque() {
        Marque marque = new Marque();
        marque.setNom("Marque Before update");
        Marque created = restTemplate.postForObject(baseUrl(), marque, Marque.class);

        created.setNom("Marque After update"); 
        restTemplate.put(baseUrl(), created);

        Marque updated = restTemplate.getForObject(baseUrl() + "/" + created.getId(), Marque.class);
        assertThat(updated.getNom()).isEqualTo("Marque After update");
    }

    @Test
    void testSaveMarque() {
        Marque marque = new Marque();
        marque.setNom("New Marque");

        this.restTemplate.postForObject(baseUrl(), marque, Marque.class);

        assertThat(this.restTemplate.getForObject(baseUrl(), String.class)).contains("New Marque");

    }

    @Test
    void testDeleteMarqueById() {
        Marque marque = new Marque();
        marque.setNom("ToBeDeleted");
        this.restTemplate.postForObject(baseUrl(), marque, Marque.class);


        Marque saved = this.restTemplate.postForObject(baseUrl(), marque, Marque.class);
        Integer id = saved.getId();

        assertThat(saved).isNotNull();
        assertThat(id).isNotNull();

        this.restTemplate.delete(baseUrl() + "/" + id);

        ResponseEntity<Marque> response = this.restTemplate.getForEntity(baseUrl() + "/" + id, Marque.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

}
