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

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.repository.CapteurRepo;

public class CapteurServiceTest {
@Mock
    private CapteurRepo capteurRepo;

    @InjectMocks
    private CapteurService capteurService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCapteurs() {
        Capteur capteur1 = new Capteur();
        Capteur capteur2 = new Capteur();
        when(capteurRepo.findAll()).thenReturn(Arrays.asList(capteur1, capteur2));

        List<Capteur> capteurs = capteurService.getAllCapteurs();

        assertEquals(2, capteurs.size());
        verify(capteurRepo, times(1)).findAll();
    }

    @Test
    public void testGetCapteurById_Found() {
        Capteur capteur = new Capteur();
        capteur.setId(1);
        when(capteurRepo.findById(1)).thenReturn(Optional.of(capteur));

        Capteur result = capteurService.getCapteurById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetCapteurById_NotFound() {
        when(capteurRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            capteurService.getCapteurById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveCapteur() {
        Capteur capteur = new Capteur();
        capteur.setId(1);
        when(capteurRepo.save(capteur)).thenReturn(capteur);
        Capteur result = capteurService.saveCapteur(capteur);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateCapteur() {
        Capteur capteur = new Capteur();
        capteur.setId(1);
    
        when(capteurRepo.existsById(1)).thenReturn(true);
    
        when(capteurRepo.save(capteur)).thenReturn(capteur);
    
        Capteur result = capteurService.updateCapteur(capteur);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteCapteurById() {
        Capteur capteur = new Capteur();
        capteur.setId(1);
    
        when(capteurRepo.existsById(1)).thenReturn(true); 
    
        capteurService.deleteCapteurById(1);
    
        verify(capteurRepo, times(1)).deleteById(1);
    }
}
