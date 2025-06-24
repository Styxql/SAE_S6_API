package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SalleControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/salle";

    @Test
    void testGetAllSalles() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetSalleById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        Salle salle = objectMapper.readValue(json, Salle.class);
        assertThat(salle.getId()).isEqualTo(1);
    }

    @Test
    void testSaveSalle() throws Exception {
        // Assumes Batiment with ID 1 and TypeSalle with ID 1 exist
        Salle salle = new Salle();
        salle.setNom("Salle Test");
        salle.setUrlImg("https://example.com/image.jpg");
        salle.setCapacite(40);
        salle.setBatiment(new Batiment(1, "BAT-A", null, null));  // or use a simple constructor if available
        salle.setTypeSalle(new TypeSalle(1, "Amphi"));

        String json = objectMapper.writeValueAsString(salle);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Salle saved = objectMapper.readValue(responseJson, Salle.class);

        assertThat(saved.getNom()).isEqualTo("Salle Test");
        assertThat(saved.getCapacite()).isEqualTo(40);
    }

    @Test
    void testUpdateSalle() throws Exception {
        Salle salle = new Salle();
        salle.setNom("Temporaire");
        salle.setUrlImg(null);
        salle.setCapacite(20);
        salle.setBatiment(new Batiment(1, null, null, null));
        salle.setTypeSalle(new TypeSalle(1, null));

        String json = objectMapper.writeValueAsString(salle);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Salle created = objectMapper.readValue(postResult.getResponse().getContentAsString(), Salle.class);

        created.setNom("Salle Modifiée");
        created.setCapacite(50);
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        Salle updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), Salle.class);
        assertThat(updated.getNom()).isEqualTo("Salle Modifiée");
        assertThat(updated.getCapacite()).isEqualTo(50);
    }

    @Test
    void testDeleteSalleById() throws Exception {
        Salle salle = new Salle();
        salle.setNom("À supprimer");
        salle.setCapacite(10);
        salle.setUrlImg(null);
        salle.setBatiment(new Batiment(1, null, null, null));
        salle.setTypeSalle(new TypeSalle(1, null));

        String json = objectMapper.writeValueAsString(salle);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Salle saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), Salle.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
