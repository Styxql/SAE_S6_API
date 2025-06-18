package com.usmb.but3.sae_s6_api.controller;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
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
public class TypeSalleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getURL() {
        return "http://localhost:" + port + "/sae/v1/typeSalle";
    }

    @Test
    void testSaveTypeSalle() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setNom("typeSalle post");

        TypeSalle savedTypeSalle = this.restTemplate.postForObject(getURL(), typeSalle, TypeSalle.class);

        assertThat(savedTypeSalle).isNotNull();
        assertThat(savedTypeSalle.getNom()).isEqualTo("typeSalle post");
        assertThat(savedTypeSalle.getId()).isNotNull();
    }

    @Test
    void testGetAllTypeSalles() {
        TypeSalle[] typeSalles = this.restTemplate.getForObject(getURL(), TypeSalle[].class);
        assertThat(typeSalles).isNotEmpty();
    }

    @Test
    void testGetTypeSalleById() {
    assertThat(
            this.restTemplate.getForObject(getURL() + "/1", TypeSalle.class)
            ).satisfies(
                typeSalle -> assertThat(typeSalle.getId()).isEqualTo(1)
                );
    }

    @Test
    void testUpdateTypeSalle() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setNom("typeSalle put");

        TypeSalle saved = this.restTemplate.postForObject(getURL(), typeSalle, TypeSalle.class);

        saved.setNom("typeSalle put update");

        this.restTemplate.put(getURL(), saved);

        TypeSalle updated = this.restTemplate.getForObject(getURL() + "/" + saved.getId(), TypeSalle.class);

        assertThat(updated.getNom()).isEqualTo("typeSalle put update");
    }

    @Test
    void testDeleteTypeSalleById() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setNom("typeSalle delete");

        TypeSalle saved = this.restTemplate.postForObject(getURL(), typeSalle, TypeSalle.class);
        Integer id = saved.getId();

        assertThat(saved).isNotNull();
        assertThat(id).isNotNull();

        this.restTemplate.delete(getURL() + "/" + id);

        ResponseEntity<TypeSalle> response = this.restTemplate.getForEntity(getURL() + "/" + id, TypeSalle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
