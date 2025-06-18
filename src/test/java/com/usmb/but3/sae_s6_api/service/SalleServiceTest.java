package com.usmb.but3.sae_s6_api.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.repository.SalleRepo;

public class SalleServiceTest {
    @Mock
    private SalleRepo salleRepo;

    @InjectMocks
    private SalleService salleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSalles() {
        Salle salle1 = new Salle();
        Salle salle2 = new Salle();
        when(salleRepo.findAll()).thenReturn(Arrays.asList(salle1, salle2));

        List<Salle> salles = salleService.getAllSalles();

        assertEquals(2, salles.size());
        verify(salleRepo, times(1)).findAll();
    }

    @Test
    public void testGetSalleById_Found() {
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.findById(1)).thenReturn(Optional.of(salle));

        Salle result = salleService.getSalleById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetSalleById_NotFound() {
        when(salleRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            salleService.getSalleById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveSalle() {
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.save(salle)).thenReturn(salle);
        Salle result = salleService.saveSalle(salle);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateSalle() {
        Salle salle = new Salle();
        salle.setId(1);
    
        when(salleRepo.existsById(1)).thenReturn(true);
    
        when(salleRepo.save(salle)).thenReturn(salle);
    
        Salle result = salleService.updateSalle(salle);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteSalleById() {
        Salle salle = new Salle();
        salle.setId(1);
    
        when(salleRepo.existsById(1)).thenReturn(true); 
    
        salleService.deleteSalleById(1);
    
        verify(salleRepo, times(1)).deleteById(1);
    }
}
