package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
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
public class TypeEquipementControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/typeEquipement";

    @Test
    void testGetAllTypeEquipements() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetTypeEquipementById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        TypeEquipement typeEquipement = objectMapper.readValue(json, TypeEquipement.class);
        assertThat(typeEquipement.getId()).isEqualTo(1);
    }

    @Test
    void testSaveTypeEquipement() throws Exception {
        TypeEquipement typeEquipement = new TypeEquipement(null, "Projecteur");
        String json = objectMapper.writeValueAsString(typeEquipement);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        TypeEquipement saved = objectMapper.readValue(responseJson, TypeEquipement.class);

        assertThat(saved.getNom()).isEqualTo("Projecteur");
    }

    @Test
    void testUpdateTypeEquipement() throws Exception {
        TypeEquipement typeEquipement = new TypeEquipement(null, "Temporaire");
        String json = objectMapper.writeValueAsString(typeEquipement);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        TypeEquipement created = objectMapper.readValue(postResult.getResponse().getContentAsString(), TypeEquipement.class);

        created.setNom("Écran interactif");
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        TypeEquipement updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), TypeEquipement.class);
        assertThat(updated.getNom()).isEqualTo("Écran interactif");
    }

    @Test
    void testDeleteTypeEquipementById() throws Exception {
        TypeEquipement typeEquipement = new TypeEquipement(null, "À supprimer");
        String json = objectMapper.writeValueAsString(typeEquipement);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        TypeEquipement saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), TypeEquipement.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
