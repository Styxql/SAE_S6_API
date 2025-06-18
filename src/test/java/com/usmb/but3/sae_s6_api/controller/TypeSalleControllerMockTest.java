package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class TypeSalleControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/typeSalle";

    @Test
    void testGetAllTypeSalles() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetTypeSalleById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        TypeSalle typeSalle = objectMapper.readValue(json, TypeSalle.class);
        assertThat(typeSalle.getId()).isEqualTo(1);
    }

    @Test
    void testSaveTypeSalle() throws Exception {
        TypeSalle typeSalle = new TypeSalle(null, "Salle informatique");
        String json = objectMapper.writeValueAsString(typeSalle);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        TypeSalle saved = objectMapper.readValue(responseJson, TypeSalle.class);

        assertThat(saved.getNom()).isEqualTo("Salle informatique");
    }

    @Test
    void testUpdateTypeSalle() throws Exception {
        TypeSalle typeSalle = new TypeSalle(null, "Temporaire");
        String json = objectMapper.writeValueAsString(typeSalle);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        TypeSalle created = objectMapper.readValue(postResult.getResponse().getContentAsString(), TypeSalle.class);

        created.setNom("Salle de conférence");
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        TypeSalle updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), TypeSalle.class);
        assertThat(updated.getNom()).isEqualTo("Salle de conférence");
    }

    @Test
    void testDeleteTypeSalleById() throws Exception {
        TypeSalle typeSalle = new TypeSalle(null, "À supprimer");
        String json = objectMapper.writeValueAsString(typeSalle);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        TypeSalle saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), TypeSalle.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
