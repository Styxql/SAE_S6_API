package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
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
public class UniteMesurerControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/uniteMesurer";

    @Test
    void testGetAllUnitesMesurer() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetUniteMesurerById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        UniteMesurer unite = objectMapper.readValue(json, UniteMesurer.class);
        assertThat(unite.getId()).isEqualTo(1);
    }

    @Test
    void testSaveUniteMesurer() throws Exception {
        UniteMesurer unite = new UniteMesurer();
        unite.setNom("unite mesurer post");
        unite.setSymbole("m");

        String json = objectMapper.writeValueAsString(unite);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        UniteMesurer saved = objectMapper.readValue(responseJson, UniteMesurer.class);

        assertThat(saved.getNom()).isEqualTo("unite mesurer post");
        assertThat(saved.getSymbole()).isEqualTo("m");
    }

    @Test
    void testUpdateUniteMesurer() throws Exception {
        UniteMesurer unite = new UniteMesurer(null, "Temporaire", "tmp", null);
        String json = objectMapper.writeValueAsString(unite);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        UniteMesurer created = objectMapper.readValue(postResult.getResponse().getContentAsString(), UniteMesurer.class);

        created.setNom("Température");
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        UniteMesurer updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), UniteMesurer.class);
        assertThat(updated.getNom()).isEqualTo("Température");
    }

    @Test
    void testDeleteUniteMesurerById() throws Exception {
        UniteMesurer unite = new UniteMesurer(null, "À supprimer", "del", null);
        String json = objectMapper.writeValueAsString(unite);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        UniteMesurer saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), UniteMesurer.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
