package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.but3.sae_s6_api.entity.Marque;
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
public class MarqueControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/marque";

    @Test
    void testGetAllMarques() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetMarqueById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        Marque marque = objectMapper.readValue(json, Marque.class);
        assertThat(marque.getId()).isEqualTo(1);
    }

    @Test
    void testSaveMarque() throws Exception {
        Marque marque = new Marque(null, "Philips");

        String json = objectMapper.writeValueAsString(marque);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Marque saved = objectMapper.readValue(result.getResponse().getContentAsString(), Marque.class);
        assertThat(saved.getNom()).isEqualTo("Philips");
    }

    @Test
    void testUpdateMarque() throws Exception {
        Marque marque = new Marque(null, "Temporaire");

        String json = objectMapper.writeValueAsString(marque);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Marque created = objectMapper.readValue(postResult.getResponse().getContentAsString(), Marque.class);

        created.setNom("Sony");
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        Marque updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), Marque.class);
        assertThat(updated.getNom()).isEqualTo("Sony");
    }

    @Test
    void testDeleteMarqueById() throws Exception {
        Marque marque = new Marque(null, "À supprimer");

        String json = objectMapper.writeValueAsString(marque);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        Marque saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), Marque.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
