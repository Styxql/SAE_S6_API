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

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.usmb.but3.sae_s6_api.repository.UniteMesurerRepo;

public class UniteMesurerServiceTest {

    @Mock
    private UniteMesurerRepo uniteMesurerRepo;

    @InjectMocks
    private UniteMesurerService uniteMesurerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUniteMesurers() {
        UniteMesurer UniteMesurer1 = new UniteMesurer();
        UniteMesurer UniteMesurer2 = new UniteMesurer();
        when(uniteMesurerRepo.findAll()).thenReturn(Arrays.asList(UniteMesurer1, UniteMesurer2));

        List<UniteMesurer> typeUniteMesurers = uniteMesurerService.getAllUniteMesurers();

        assertEquals(2, typeUniteMesurers.size());
        verify(uniteMesurerRepo, times(1)).findAll();
    }

    @Test
    public void testGetUniteMesurerById_Found() {
        UniteMesurer typeSalle = new UniteMesurer();
        typeSalle.setId(1);
        when(uniteMesurerRepo.findById(1)).thenReturn(Optional.of(typeSalle));

        UniteMesurer result = uniteMesurerService.getUniteMesurerById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetUniteMesurerById_NotFound() {
        when(uniteMesurerRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            uniteMesurerService.getUniteMesurerById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveUniteMesurer() {
        UniteMesurer typeSalle = new UniteMesurer();
        typeSalle.setId(1);
        when(uniteMesurerRepo.save(typeSalle)).thenReturn(typeSalle);
        UniteMesurer result = uniteMesurerService.saveUniteMesurer(typeSalle);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateUniteMesurer() {
        UniteMesurer typeSalle = new UniteMesurer();
        typeSalle.setId(1);
    
        when(uniteMesurerRepo.existsById(1)).thenReturn(true);
    
        when(uniteMesurerRepo.save(typeSalle)).thenReturn(typeSalle);
    
        UniteMesurer result = uniteMesurerService.updateUniteMesurer(typeSalle);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteUniteMesurerById() {
        UniteMesurer typeSalle = new UniteMesurer();
        typeSalle.setId(1);
    
        when(uniteMesurerRepo.existsById(1)).thenReturn(true); 
    
        uniteMesurerService.deleteUniteMesurerById(1);
    
        verify(uniteMesurerRepo, times(1)).deleteById(1);
    }
}
