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

import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.repository.EquipementInstalleRepo;

public class EquipementInstalleServiceTest {
    @Mock
    private EquipementInstalleRepo equipementInstalleRepo;

    @InjectMocks
    private EquipementInstalleService equipementInstalleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEquipementInstalles() {
        EquipementInstalle equipementInstalle1 = new EquipementInstalle();
        EquipementInstalle equipementInstalle2 = new EquipementInstalle();
        when(equipementInstalleRepo.findAll()).thenReturn(Arrays.asList(equipementInstalle1, equipementInstalle2));

        List<EquipementInstalle> equipementInstalles = equipementInstalleService.getAllEquipementInstalles();

        assertEquals(2, equipementInstalles.size());
        verify(equipementInstalleRepo, times(1)).findAll();
    }

    @Test
    public void testGetEquipementInstalleById_Found() {
        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setId(1);
        when(equipementInstalleRepo.findById(1)).thenReturn(Optional.of(equipementInstalle));

        EquipementInstalle result = equipementInstalleService.getEquipementInstalleById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetEquipementInstalleById_NotFound() {
        when(equipementInstalleRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            equipementInstalleService.getEquipementInstalleById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveEquipementInstalle() {
        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setId(1);
        when(equipementInstalleRepo.save(equipementInstalle)).thenReturn(equipementInstalle);
        EquipementInstalle result = equipementInstalleService.saveEquipementInstalle(equipementInstalle);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateEquipementInstalle() {
        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setId(1);
    
        when(equipementInstalleRepo.existsById(1)).thenReturn(true);
    
        when(equipementInstalleRepo.save(equipementInstalle)).thenReturn(equipementInstalle);
    
        EquipementInstalle result = equipementInstalleService.updateEquipementInstalle(equipementInstalle);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteEquipementInstalleById() {
        EquipementInstalle equipementInstalle = new EquipementInstalle();
        equipementInstalle.setId(1);
    
        when(equipementInstalleRepo.existsById(1)).thenReturn(true); 
    
        equipementInstalleService.deleteEquipementInstalleById(1);
    
        verify(equipementInstalleRepo, times(1)).deleteById(1);
    }
}
