package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.Marque;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CapteurControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/capteur";

    @Test
    void testGetAllCapteurs() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetCapteurById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        Capteur capteur = objectMapper.readValue(json, Capteur.class);
        assertThat(capteur.getId()).isEqualTo(1);
    }

    @Test
    void testSaveCapteur() throws Exception {
        Capteur capteur = new Capteur();
        capteur.setNom("Capteur minimal");
        capteur.setHauteur(BigDecimal.valueOf(10.0));
        capteur.setLongueur(BigDecimal.valueOf(5.0));
        capteur.setLargeur(BigDecimal.valueOf(2.0));
        capteur.setMarque(new Marque(1, null)); 
        
        String json = objectMapper.writeValueAsString(capteur);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Capteur saved = objectMapper.readValue(result.getResponse().getContentAsString(), Capteur.class);
        assertThat(saved.getNom()).isEqualTo("Capteur minimal");
    }

    @Test
    void testUpdateCapteur() throws Exception {
        Capteur capteur = new Capteur();
        capteur.setNom("Capteur temporaire");
        capteur.setHauteur(BigDecimal.valueOf(12.0));
        capteur.setLongueur(BigDecimal.valueOf(6.0));
        capteur.setLargeur(BigDecimal.valueOf(3.0));
        capteur.setMarque(new Marque(1, null));

        String json = objectMapper.writeValueAsString(capteur);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Capteur created = objectMapper.readValue(postResult.getResponse().getContentAsString(), Capteur.class);

        created.setNom("Capteur modifié");
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        Capteur updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), Capteur.class);
        assertThat(updated.getNom()).isEqualTo("Capteur modifié");
    }

    @Test
    void testDeleteCapteurById() throws Exception {
        Capteur capteur = new Capteur();
        capteur.setNom("À supprimer");
        capteur.setHauteur(BigDecimal.valueOf(10));
        capteur.setLongueur(BigDecimal.valueOf(5));
        capteur.setLargeur(BigDecimal.valueOf(2));
        capteur.setMarque(new Marque(1, null));

        String json = objectMapper.writeValueAsString(capteur);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Capteur saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), Capteur.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
