package com.usmb.but3.sae_s6_api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.entity.Salle;

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
        marque.setId(998);
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
        marque.setId(4);
        marque.setNom("New Marque");

        this.restTemplate.postForObject("http://localhost:" + port + "/sae/v1/marque", marque, Marque.class);

        assertThat(this.restTemplate.getForObject("http://localhost:"+port+ "/sae/v1/marque", String.class)).contains("New Marque");

    }

    @Test
    void testDeleteMarqueById() {
        Marque marque = new Marque();
        marque.setId(101);
        marque.setNom("ToBeDeleted");
        this.restTemplate.postForObject("http://localhost:" + port + "/sae/v1/marque", marque, Marque.class);

        this.restTemplate.delete("http://localhost:" + port + "/sae/v1/marque/101");

        Marque[] all = this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/marque",
        Marque[].class);
        assertThat(all).noneMatch(u -> "ToBeDeleted".equals(u.getNom()));
    }

}
