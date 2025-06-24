package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.but3.sae_s6_api.entity.Batiment;
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
public class BatimentControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/batiment";

    @Test
    void testGetAllBatiments() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetBatimentById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        Batiment batiment = objectMapper.readValue(json, Batiment.class);
        assertThat(batiment.getId()).isEqualTo(1);
    }

    @Test
    void testSaveBatiment() throws Exception {
        Batiment batiment = new Batiment(null, "Bâtiment B", "https://example.com/image.jpg", null);
        String json = objectMapper.writeValueAsString(batiment);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Batiment saved = objectMapper.readValue(responseJson, Batiment.class);

        assertThat(saved.getNom()).isEqualTo("Bâtiment B");
        assertThat(saved.getUrlImg()).isEqualTo("https://example.com/image.jpg");
    }

    @Test
    void testUpdateBatiment() throws Exception {
        Batiment batiment = new Batiment(null, "Temporaire", null, null);
        String json = objectMapper.writeValueAsString(batiment);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Batiment created = objectMapper.readValue(postResult.getResponse().getContentAsString(), Batiment.class);

        created.setNom("Bâtiment Modifié");
        created.setUrlImg("https://example.com/updated.jpg");
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        Batiment updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), Batiment.class);
        assertThat(updated.getNom()).isEqualTo("Bâtiment Modifié");
        assertThat(updated.getUrlImg()).isEqualTo("https://example.com/updated.jpg");
    }

    @Test
    void testDeleteBatimentById() throws Exception {
        Batiment batiment = new Batiment(null, "À supprimer", null, null);
        String json = objectMapper.writeValueAsString(batiment);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Batiment saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), Batiment.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
