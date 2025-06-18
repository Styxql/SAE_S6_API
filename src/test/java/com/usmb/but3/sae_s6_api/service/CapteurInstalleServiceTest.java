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

import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.repository.CapteurInstalleRepo;

public class CapteurInstalleServiceTest {
    

 @Mock
    private CapteurInstalleRepo capteurInstalleRepo;

    @InjectMocks
    private CapteurInstalleService capteurInstalleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCapteurInstalles() {
        CapteurInstalle salle1 = new CapteurInstalle();
        CapteurInstalle salle2 = new CapteurInstalle();
        when(capteurInstalleRepo.findAll()).thenReturn(Arrays.asList(salle1, salle2));

        List<CapteurInstalle> salles = capteurInstalleService.getAllCapteurInstalles();

        assertEquals(2, salles.size());
        verify(capteurInstalleRepo, times(1)).findAll();
    }

    @Test
    public void testGetCapteurInstalleById_Found() {
        CapteurInstalle capteurInstalle = new CapteurInstalle();
        capteurInstalle.setId(1);
        when(capteurInstalleRepo.findById(1)).thenReturn(Optional.of(capteurInstalle));

        CapteurInstalle result = capteurInstalleService.getCapteurInstalleById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetCapteurInstalleById_NotFound() {
        when(capteurInstalleRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            capteurInstalleService.getCapteurInstalleById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveCapteurInstalle() {
        CapteurInstalle capteurInstalle = new CapteurInstalle();
        capteurInstalle.setId(1);
        when(capteurInstalleRepo.save(capteurInstalle)).thenReturn(capteurInstalle);
        CapteurInstalle result = capteurInstalleService.saveCapteurInstalle(capteurInstalle);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateCapteurInstalle() {
        CapteurInstalle capteurInstalle = new CapteurInstalle();
        capteurInstalle.setId(1);
    
        when(capteurInstalleRepo.existsById(1)).thenReturn(true);
    
        when(capteurInstalleRepo.save(capteurInstalle)).thenReturn(capteurInstalle);
    
        CapteurInstalle result = capteurInstalleService.updateCapteurInstalle(capteurInstalle);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteCapteurInstalleById() {
        CapteurInstalle capteurInstalle = new CapteurInstalle();
        capteurInstalle.setId(1);
    
        when(capteurInstalleRepo.existsById(1)).thenReturn(true); 
    
        capteurInstalleService.deleteCapteurInstalleById(1);
    
        verify(capteurInstalleRepo, times(1)).deleteById(1);
    }
}
