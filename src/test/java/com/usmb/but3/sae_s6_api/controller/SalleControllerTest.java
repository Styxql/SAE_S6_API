package com.usmb.but3.sae_s6_api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;


import org.junit.jupiter.api.Test;

import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.entity.Salle;
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
    void testGetallSalle() {
        Salle[] typeSalles = restTemplate.getForObject(baseUrl(), Salle[].class);
        assertThat(typeSalles).isNotEmpty();
    }

    @Test
    void testGetSalleById() {
        assertThat(this.restTemplate.getForObject(baseUrl()+"/5",
                Salle.class)).satisfies(salle -> assertThat(salle.getId()).isEqualTo(5));
    }

   
    @Test
    void testSaveSalle() {

    }

    @Test
    void testUpdateSalle() {

    }
    @Test
    void testDeleteSalleById() {

    }
}
