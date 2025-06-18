package com.usmb.but3.sae_s6_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
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
public class CapteurInstalleControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/sae/v1/capteurInstalle";

    @Test
    void testGetAllCapteursInstalles() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testGetCapteurInstalleById() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        CapteurInstalle capteurInstalle = objectMapper.readValue(json, CapteurInstalle.class);
        assertThat(capteurInstalle.getId()).isEqualTo(1);
    }

    @Test
    void testSaveCapteurInstalle() throws Exception {
        CapteurInstalle capteurInstalle = new CapteurInstalle();
        capteurInstalle.setCapteur(new Capteur(1, null, null, null, null, null, null, null, null, null));
        capteurInstalle.setSalle(new Salle(1, null, null, null, null, null));
        capteurInstalle.setNombre(3);
        String json = objectMapper.writeValueAsString(capteurInstalle);

        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        CapteurInstalle saved = objectMapper.readValue(result.getResponse().getContentAsString(), CapteurInstalle.class);
        assertThat(saved.getCapteur().getId()).isEqualTo(1);
        assertThat(saved.getSalle().getId()).isEqualTo(1);
    }

    @Test
    void testUpdateCapteurInstalle() throws Exception {
        CapteurInstalle capteurInstalle = new CapteurInstalle();
        capteurInstalle.setCapteur(new Capteur(1, null, null, null, null, null, null, null, null, null));
        capteurInstalle.setSalle(new Salle(1, null, null, null, null, null));
        capteurInstalle.setNombre(1);

        String json = objectMapper.writeValueAsString(capteurInstalle);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        CapteurInstalle created = objectMapper.readValue(postResult.getResponse().getContentAsString(), CapteurInstalle.class);

        created.setNombre(5);
        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andReturn();

        MvcResult getResult = mockMvc.perform(get(BASE_URL + "/" + created.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        CapteurInstalle updated = objectMapper.readValue(getResult.getResponse().getContentAsString(), CapteurInstalle.class);
        assertThat(updated.getNombre()).isEqualTo(5);
    }

    @Test
    void testDeleteCapteurInstalleById() throws Exception {
        CapteurInstalle capteurInstalle = new CapteurInstalle();
        capteurInstalle.setCapteur(new Capteur(1, null, null, null, null, null, null, null, null, null));
        capteurInstalle.setSalle(new Salle(1, null, null, null, null, null));
        capteurInstalle.setNombre(3);

        String json = objectMapper.writeValueAsString(capteurInstalle);

        MvcResult postResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andReturn();

        CapteurInstalle saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), CapteurInstalle.class);
        Integer id = saved.getId();

        mockMvc.perform(delete(BASE_URL + "/" + id)).andReturn();

        MvcResult getAfterDelete = mockMvc.perform(get(BASE_URL + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn();

        int status = getAfterDelete.getResponse().getStatus();
        assertThat(status).isEqualTo(404);
    }
}
