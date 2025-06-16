package com.usmb.but3.sae_s6_api.controller;

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UniteMesurerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllUniteMesurer() {
        assertThat(
            this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/uniteMesurer", UniteMesurer[].class)
        ).satisfies(unites -> {
            assertThat(unites).isNotEmpty();
            assertThat(unites[0].getNom()).isNotBlank();
        });
    }

    @Test
    void testGetUniteMesurerById() {
        UniteMesurer unite = this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/uniteMesurer/1", UniteMesurer.class);
        assertThat(unite).isNotNull();
        assertThat(unite.getNom()).isEqualTo("Luminosité"); 
    }

    @Test
    void testSaveUniteMesurer() {
        UniteMesurer unite = new UniteMesurer();
        unite.setId(40);
        unite.setNom("TestUnite");

        this.restTemplate.postForObject("http://localhost:" + port + "/sae/v1/uniteMesurer", unite, UniteMesurer.class);

        assertThat(this.restTemplate.getForObject("http://localhost:"+port+ "/sae/v1/uniteMesurer", String.class)).contains("TestUnite");

    }

    @Test
    void testUpdateUniteMesurer() {
        // Créer l'entité initiale
        UniteMesurer unite = new UniteMesurer();
        unite.setId(100);
        unite.setNom("AvantUpdate");
        this.restTemplate.postForObject("http://localhost:" + port + "/sae/v1/uniteMesurer", unite, UniteMesurer.class);

        // Modifier et faire PUT
        unite.setNom("ApresUpdate");
        this.restTemplate.put("http://localhost:" + port + "/sae/v1/uniteMesurer", unite);

        UniteMesurer updated = this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/uniteMesurer/100", UniteMesurer.class);
        assertThat(updated.getNom()).isEqualTo("ApresUpdate");
    }

    @Test
    void testDeleteUniteMesurerById() {
        // Créer une unité à supprimer
        UniteMesurer unite = new UniteMesurer();
        unite.setId(101);
        unite.setNom("ToBeDeleted");
        this.restTemplate.postForObject("http://localhost:" + port + "/sae/v1/uniteMesurer", unite, UniteMesurer.class);

        // Supprimer
        this.restTemplate.delete("http://localhost:" + port + "/sae/v1/uniteMesurer/101");

        // Vérifier que l'entité est absente
        UniteMesurer[] all = this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/uniteMesurer", UniteMesurer[].class);
        assertThat(all).noneMatch(u -> "ToBeDeleted".equals(u.getNom()));
    }
}
