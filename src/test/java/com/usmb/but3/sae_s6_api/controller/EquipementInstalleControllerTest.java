package com.usmb.but3.sae_s6_api.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;


import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.entity.Salle;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EquipementInstalleControllerTest {
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSaveEquipementInstalle() {
        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setId(22);
        equipementInstalle.setNombre(2);
        equipementInstalle.setEquipement(new Equipement());

        equipementInstalle.setSalle(new Salle());

        
        this.restTemplate.postForObject("http://localhost:" + port + "/sae/v1/equipementInstalle/", equipementInstalle, String.class);
        
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/equipementInstalle/11",
                EquipementInstalle.class)).satisfies(savedEquipementInstalle -> {
                    assertThat(savedEquipementInstalle.getId()).isEqualTo(22);
                });
    }

    @Test
    void testDeleteEquipementInstalleById() {
        this.restTemplate.delete("http://localhost:" + port + "/sae/v1/equipementInstalle/11");
        
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/equipementInstalle/11",
                EquipementInstalle.class)).isNull();
    }

    @Test
    void testGetAllEquipementInstalles() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/equipementInstalle",
                EquipementInstalle[].class)).satisfies(equipementInstalles ->{ 
                    assertThat(equipementInstalles).isNotEmpty();
                    assertThat(equipementInstalles[0].getId()).isEqualTo(1);
                });

    }

    @Test
    void testGetEquipementInstalleById() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sae/v1/equipementInstalle/11",
                EquipementInstalle.class)).satisfies(equipementInstalle -> assertThat(equipementInstalle.getId()).isEqualTo(22));
    }

    

    @Test
    void testUpdateEquipementInstalle() {

    }
}