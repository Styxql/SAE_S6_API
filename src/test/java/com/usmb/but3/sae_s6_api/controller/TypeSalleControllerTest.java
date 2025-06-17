package com.usmb.but3.sae_s6_api.controller;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeSalleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/sae/v1/typeSalle";
    }

    @Test
    void testGetAllTypeSalle() {
        TypeSalle[] typeSalles = restTemplate.getForObject(baseUrl(), TypeSalle[].class);
        assertThat(typeSalles).isNotEmpty();
    }

    @Test
    void testGetTypeSalleById() {
        TypeSalle typeSalle = restTemplate.getForObject(baseUrl() + "/1", TypeSalle.class);
        assertThat(typeSalle).isNotNull();
        assertThat(typeSalle.getNom()).isNotEmpty();
    }

    @Test
    void testSaveTypeSalle() {
        TypeSalle newTypeSalle = new TypeSalle();
        newTypeSalle.setId(5);
        newTypeSalle.setNom("Salle Conférence");
    
        TypeSalle savedTypeSalle = restTemplate.postForObject(baseUrl(), newTypeSalle, TypeSalle.class);
    
        assertThat(savedTypeSalle).isNotNull();
        assertThat(savedTypeSalle.getId()).isNotNull();
        assertThat(savedTypeSalle.getNom()).isEqualTo("Salle Conférence");
    }
    
    @Test
    void testUpdateTypeSalle() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(6);
        typeSalle.setNom("Salle Temporaire");
        TypeSalle created = restTemplate.postForObject(baseUrl(), typeSalle, TypeSalle.class);

        created.setNom("Salle Modifiée");
        restTemplate.put(baseUrl(), created);

        TypeSalle updated = restTemplate.getForObject(baseUrl() + "/" + created.getId(), TypeSalle.class);
        assertThat(updated.getNom()).isEqualTo("Salle Modifiée");
    }

    @Test
    void testDeleteTypeSalleById() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setNom("Salle à Supprimer");
        TypeSalle created = restTemplate.postForObject(baseUrl(), typeSalle, TypeSalle.class);

        restTemplate.delete(baseUrl() + "/" + created.getId());

        TypeSalle deleted = restTemplate.getForObject(baseUrl() + "/" + created.getId(), TypeSalle.class);
        assertThat(deleted).isNull(); 
    }
}
