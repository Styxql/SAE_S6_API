package com.usmb.but3.sae_s6_api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.Marque;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CapteurControllerTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getURL()
    {
        return "http://localhost:" + port + "/sae/v1/capteur";
    }

    @Test
    void testDeleteCapteurById() {

    }

    @Test
    void testGetCapteurById() {
    assertThat(
            this.restTemplate.getForObject(getURL() + "/1", Capteur.class)
            ).satisfies(
                capteur -> assertThat(capteur.getId()).isEqualTo(1)
                );
    }

    @Test
    void testGetallCapteur() {
        Capteur[] capteurs = restTemplate.getForObject(getURL(), Capteur[].class);
        assertThat(capteurs).isNotEmpty();
    }

    @Test
    void testSaveCapteur() {
        Capteur capteur = new Capteur();
        capteur.setNom("CapteurTestPost");
        capteur.setHauteur(new BigDecimal(12.34));
        capteur.setLargeur(new BigDecimal(12.34));
        capteur.setLongueur(new BigDecimal(12.34));

        Marque m = new Marque();
        m.setId(10);
        m.setNom("MarqueExemple");
        capteur.setMarque(m);

        Capteur postCapteur = this.restTemplate.postForObject(getURL(), capteur, Capteur.class);

        assertThat(postCapteur.getId()).isNotNull();
        assertThat(postCapteur.getNom()).isEqualTo(postCapteur.getNom());
        assertThat(postCapteur.getHauteur()).isEqualTo(postCapteur.getHauteur());
        assertThat(postCapteur.getLongueur()).isEqualTo(postCapteur.getLongueur());
        assertThat(postCapteur.getLargeur()).isEqualTo(postCapteur.getLargeur());
    }

    @Test
    void testUpdateCapteur() {
        Capteur capteur = new Capteur();
        capteur.setNom("CapteurTestPut");
        capteur.setHauteur(new BigDecimal(12.34));
        capteur.setLargeur(new BigDecimal(12.34));
        capteur.setLongueur(new BigDecimal(12.34));

        Marque m = new Marque();
        m.setId(10);
        m.setNom("MarqueExemple");
        capteur.setMarque(m);

        Capteur postCapteur = this.restTemplate.postForObject(getURL(), capteur, Capteur.class);

        postCapteur.setNom("CapteurTestPut update");
        this.restTemplate.put(getURL(), postCapteur);
    
        Capteur updated = restTemplate.getForObject(getURL() + "/" + postCapteur.getId(), Capteur.class);
        assertThat(updated.getNom()).isEqualTo("CapteurTestPut update");
        assertThat(updated.getHauteur()).isEqualTo(new BigDecimal(12.34));
    }
}
