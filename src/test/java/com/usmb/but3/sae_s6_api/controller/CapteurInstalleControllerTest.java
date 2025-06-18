package com.usmb.but3.sae_s6_api.controller;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.entity.Salle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CapteurInstalleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/sae/v1/capteurInstalle";
    }

    @Test
    void testSaveCapteurInstalle() {
        Capteur capteur = new Capteur();
        capteur.setId(1); // ID existant

        Salle salle = new Salle();
        salle.setId(1); // ID existant

        CapteurInstalle cin = new CapteurInstalle();
        cin.setNombre(10);
        cin.setCapteur(capteur);
        cin.setSalle(salle);

        CapteurInstalle postCapteur = restTemplate.postForObject(baseUrl(), cin, CapteurInstalle.class);

        assertThat(postCapteur).isNotNull();
        assertThat(postCapteur.getId()).isNotNull();
        assertThat(postCapteur.getNombre()).isEqualTo(10);
    }

    @Test
    void testDeleteCapteurInstalleById() {
        Capteur capteur = new Capteur();
        capteur.setId(1);

        Salle salle = new Salle();
        salle.setId(1);

        CapteurInstalle cin = new CapteurInstalle();
        cin.setNombre(7);
        cin.setCapteur(capteur);
        cin.setSalle(salle);

        CapteurInstalle created = restTemplate.postForObject(baseUrl(), cin, CapteurInstalle.class);

        restTemplate.delete(baseUrl() + "/" + created.getId());

        ResponseEntity<CapteurInstalle> response = restTemplate.getForEntity(baseUrl() + "/" + created.getId(), CapteurInstalle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetAllCapteursInstalles() {
        CapteurInstalle[] list = restTemplate.getForObject(baseUrl(), CapteurInstalle[].class);
        assertThat(list).isNotEmpty();
        assertThat(list[0].getId()).isNotNull();
    }

    @Test
    void testGetCapteurInstalleById() {
        CapteurInstalle capteurInstalle = restTemplate.getForObject(baseUrl() + "/1", CapteurInstalle.class);
        assertThat(capteurInstalle).isNotNull();
        assertThat(capteurInstalle.getId()).isEqualTo(1);
    }

    @Test
    void testUpdateCapteurInstalle() {
        Capteur capteur = new Capteur();
        capteur.setId(1);

        Salle salle = new Salle();
        salle.setId(1);

        CapteurInstalle cin = new CapteurInstalle();
        cin.setNombre(3);
        cin.setCapteur(capteur);
        cin.setSalle(salle);

        CapteurInstalle created = restTemplate.postForObject(baseUrl(), cin, CapteurInstalle.class);

        created.setNombre(99);
        restTemplate.put(baseUrl(), created);

        CapteurInstalle updated = restTemplate.getForObject(baseUrl() + "/" + created.getId(), CapteurInstalle.class);
        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(created.getId());
        assertThat(updated.getNombre()).isEqualTo(99);
    }
}
