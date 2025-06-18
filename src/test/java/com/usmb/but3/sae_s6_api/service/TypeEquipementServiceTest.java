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

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.repository.TypeEquipementRepo;

public class TypeEquipementServiceTest {
  @Mock
    private TypeEquipementRepo typeEquipementRepo;

    @InjectMocks
    private TypeEquipementService typeEquipementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTypeEquipements() {
        TypeEquipement typeEquipement1 = new TypeEquipement();
        TypeEquipement typeEquipement2 = new TypeEquipement();
        when(typeEquipementRepo.findAll()).thenReturn(Arrays.asList(typeEquipement1, typeEquipement2));

        List<TypeEquipement> typeEquipements = typeEquipementService.getAllTypeEquipements();

        assertEquals(2, typeEquipements.size());
        verify(typeEquipementRepo, times(1)).findAll();
    }

    @Test
    public void testGetTypeEquipementById_Found() {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setId(1);
        when(typeEquipementRepo.findById(1)).thenReturn(Optional.of(typeEquipement));

        TypeEquipement result = typeEquipementService.getTypeEquipementById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetTypeEquipementById_NotFound() {
        when(typeEquipementRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            typeEquipementService.getTypeEquipementById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveTypeEquipement() {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setId(1);
        when(typeEquipementRepo.save(typeEquipement)).thenReturn(typeEquipement);
        TypeEquipement result = typeEquipementService.saveTypeEquipement(typeEquipement);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateTypeEquipement() {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setId(1);
    
        when(typeEquipementRepo.existsById(1)).thenReturn(true);
    
        when(typeEquipementRepo.save(typeEquipement)).thenReturn(typeEquipement);
    
        TypeEquipement result = typeEquipementService.updateTypeEquipement(typeEquipement);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteTypeEquipementById() {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setId(1);
    
        when(typeEquipementRepo.existsById(1)).thenReturn(true); 
    
        typeEquipementService.deleteTypeEquipementById(1);
    
        verify(typeEquipementRepo, times(1)).deleteById(1);
    }
    
}
