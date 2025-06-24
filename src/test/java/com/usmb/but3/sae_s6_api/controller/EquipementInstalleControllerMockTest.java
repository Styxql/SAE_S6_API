package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.entity.Salle;
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
public class EquipementInstalleControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/equipementInstalle";

    @Test
    void testGetAllEquipementsInstalles() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetEquipementInstalleById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        EquipementInstalle equipementInstalle = objectMapper.readValue(json, EquipementInstalle.class);
        assertThat(equipementInstalle.getId()).isEqualTo(1);
    }

    @Test
    void testSaveEquipementInstalle() throws Exception {
        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setEquipement(new Equipement(1, null, null, null, null, null, null, null, null));
        equipementInstalle.setSalle(new Salle(1, null, null, null, null, null, null, null));
        equipementInstalle.setNombre(2);

        String json = objectMapper.writeValueAsString(equipementInstalle);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        EquipementInstalle saved = objectMapper.readValue(result.getResponse().getContentAsString(), EquipementInstalle.class);
        assertThat(saved.getEquipement().getId()).isEqualTo(1);
        assertThat(saved.getSalle().getId()).isEqualTo(1);
    }

    @Test
    void testUpdateEquipementInstalle() throws Exception {
        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setEquipement(new Equipement(1, null, null, null, null, null, null, null, null));
        equipementInstalle.setSalle(new Salle(1, null, null, null, null, null, null, null));
        equipementInstalle.setNombre(2);

        String json = objectMapper.writeValueAsString(equipementInstalle);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        EquipementInstalle created = objectMapper.readValue(postResult.getResponse().getContentAsString(), EquipementInstalle.class);

        created.setNombre(5);
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        EquipementInstalle updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), EquipementInstalle.class);
        assertThat(updated.getNombre()).isEqualTo(5);
    }

    @Test
    void testDeleteEquipementInstalleById() throws Exception {
        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setEquipement(new Equipement(1, null, null, null, null, null, null, null, null));
        equipementInstalle.setSalle(new Salle(1, null, null, null, null, null, null, null));
        equipementInstalle.setNombre(2);

        String json = objectMapper.writeValueAsString(equipementInstalle);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        EquipementInstalle saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), EquipementInstalle.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
