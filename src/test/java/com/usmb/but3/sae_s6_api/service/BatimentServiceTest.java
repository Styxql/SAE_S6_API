package com.usmb.but3.sae_s6_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.repository.BatimentRepo;

public class BatimentServiceTest {
  

    @Mock
    private BatimentRepo batimentRepo;

    @InjectMocks
    private BatimentService batimentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBatiments() {
        Batiment bat1 = new Batiment();
        Batiment bat2 = new Batiment();
        when(batimentRepo.findAll()).thenReturn(Arrays.asList(bat1, bat2));

        List<Batiment> batiments = batimentService.getAllBatiments();

        assertEquals(2, batiments.size());
        verify(batimentRepo, times(1)).findAll();
    }

    @Test
    public void testGetBatimentById_Found() {
        Batiment salle = new Batiment();
        salle.setId(1);
        when(batimentRepo.findById(1)).thenReturn(Optional.of(salle));

        Batiment result = batimentService.getBatimentById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetBatimentById_NotFound() {
        when(batimentRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            batimentService.getBatimentById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveBatiment() {
        Batiment salle = new Batiment();
        salle.setId(1);
        when(batimentRepo.save(salle)).thenReturn(salle);
        Batiment result = batimentService.saveBatiment(salle);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateBatiment() {
        Batiment salle = new Batiment();
        salle.setId(1);
    
        when(batimentRepo.existsById(1)).thenReturn(true);
    
        when(batimentRepo.save(salle)).thenReturn(salle);
    
        Batiment result = batimentService.updateBatiment(salle);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteBatimentById() {
        Batiment salle = new Batiment();
        salle.setId(1);
    
        when(batimentRepo.existsById(1)).thenReturn(true); 
    
        batimentService.deleteBatimentById(1);
    
        verify(batimentRepo, times(1)).deleteById(1);
    }
}
