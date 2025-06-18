package com.usmb.but3.sae_s6_api.controller;

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UniteMesurerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getURL() {
        return "http://localhost:" + port + "/sae/v1/uniteMesurer";
    }

    @Test
    void testSaveUniteMesurer() {
        UniteMesurer unite = new UniteMesurer();
        unite.setNom("UniteMesurer Post");
        unite.setSymbole("m");

        UniteMesurer saved = this.restTemplate.postForObject(getURL(), unite, UniteMesurer.class);

        assertThat(saved.getNom()).isEqualTo("UniteMesurer Post");
        assertThat(saved.getSymbole()).isEqualTo("m");
    }

    @Test
    void testGetAllUniteMesurer() {
        UniteMesurer[] unites = this.restTemplate.getForObject(getURL(), UniteMesurer[].class);
        assertThat(unites).isNotEmpty();
    }

    @Test
    void testGetUniteMesurerById() {
    assertThat(
            this.restTemplate.getForObject(getURL() + "/1", UniteMesurer.class)
            ).satisfies(
                uniteMesurer -> assertThat(uniteMesurer.getId()).isEqualTo(1)
                );
    }

    @Test
    void testUpdateUniteMesurer() {
        UniteMesurer unite = new UniteMesurer();
        unite.setNom("UniteMesurer Put");
        unite.setSymbole("m");

        UniteMesurer saved = this.restTemplate.postForObject(getURL(), unite, UniteMesurer.class);
        saved.setNom("UniteMesurer Put update");

        this.restTemplate.put(getURL(), saved);

        UniteMesurer updated = this.restTemplate.getForObject(getURL() + "/" + saved.getId(), UniteMesurer.class);
        assertThat(updated.getNom()).isEqualTo("UniteMesurer Put update");
        assertThat(updated.getSymbole()).isEqualTo("m");
    }

    @Test
    void testDeleteUniteMesurerById() {
        UniteMesurer unite = new UniteMesurer();
        unite.setNom("UniteMesurer delete");
        unite.setSymbole("tmp");

        UniteMesurer saved = this.restTemplate.postForObject(getURL(), unite, UniteMesurer.class);
        Integer id = saved.getId();

        this.restTemplate.delete(getURL() + "/" + id);

        ResponseEntity<UniteMesurer> response = this.restTemplate.getForEntity(getURL() + "/" + id, UniteMesurer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
