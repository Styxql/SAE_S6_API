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

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.repository.EquipementRepo;

public class EquipementServiceTest {
    @Mock
    private EquipementRepo equipementRepo;

    @InjectMocks
    private EquipementService equipementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEquipements() {
        Equipement equipement1 = new Equipement();
        Equipement equipement2 = new Equipement();
        when(equipementRepo.findAll()).thenReturn(Arrays.asList(equipement1, equipement2));

        List<Equipement> equipements = equipementService.getAllEquipements();

        assertEquals(2, equipements.size());
        verify(equipementRepo, times(1)).findAll();
    }

    @Test
    public void testGetEquipementById_Found() {
        Equipement equipement = new Equipement();
        equipement.setId(1);
        when(equipementRepo.findById(1)).thenReturn(Optional.of(equipement));

        Equipement result = equipementService.getEquipementById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetEquipementById_NotFound() {
        when(equipementRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            equipementService.getEquipementById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveEquipement() {
        Equipement equipement = new Equipement();
        equipement.setId(1);
        when(equipementRepo.save(equipement)).thenReturn(equipement);
        Equipement result = equipementService.saveEquipement(equipement);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateEquipement() {
        Equipement equipement = new Equipement();
        equipement.setId(1);
    
        when(equipementRepo.existsById(1)).thenReturn(true);
    
        when(equipementRepo.save(equipement)).thenReturn(equipement);
    
        Equipement result = equipementService.updateEquipement(equipement);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteEquipementById() {
        Equipement equipement = new Equipement();
        equipement.setId(1);
    
        when(equipementRepo.existsById(1)).thenReturn(true); 
    
        equipementService.deleteEquipementById(1);
    
        verify(equipementRepo, times(1)).deleteById(1);
    }
}
