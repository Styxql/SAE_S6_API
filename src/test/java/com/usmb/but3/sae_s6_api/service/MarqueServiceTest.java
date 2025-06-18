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

import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.repository.MarqueRepo;

public class MarqueServiceTest {
    @Mock
    private MarqueRepo marqueRepo;

    @InjectMocks
    private MarqueService marqueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMarques() {
        Marque marque1 = new Marque();
        Marque marque2 = new Marque();
        when(marqueRepo.findAll()).thenReturn(Arrays.asList(marque1, marque2));

        List<Marque> marques = marqueService.getAllMarques();

        assertEquals(2, marques.size());
        verify(marqueRepo, times(1)).findAll();
    }

    @Test
    public void testGetMarqueById_Found() {
        Marque marque = new Marque();
        marque.setId(1);
        when(marqueRepo.findById(1)).thenReturn(Optional.of(marque));

        Marque result = marqueService.getMarqueById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetMarqueById_NotFound() {
        when(marqueRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            marqueService.getMarqueById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveMarque() {
        Marque marque = new Marque();
        marque.setId(1);
        when(marqueRepo.save(marque)).thenReturn(marque);
        Marque result = marqueService.saveMarque(marque);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateMarque() {
        Marque marque = new Marque();
        marque.setId(1);
    
        when(marqueRepo.existsById(1)).thenReturn(true);
    
        when(marqueRepo.save(marque)).thenReturn(marque);
    
        Marque result = marqueService.updateMarque(marque);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteMarqueById() {
        Marque marque = new Marque();
        marque.setId(1);
    
        when(marqueRepo.existsById(1)).thenReturn(true); 
    
        marqueService.deleteMarqueById(1);
    
        verify(marqueRepo, times(1)).deleteById(1);
    }
}
