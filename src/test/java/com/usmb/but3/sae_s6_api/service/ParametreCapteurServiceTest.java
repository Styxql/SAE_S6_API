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

import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.repository.ParametreCapteurRepo;

public class ParametreCapteurServiceTest {
    // @Mock
    // private ParametreCapteurRepo parametreCapteurRepo;

    // @InjectMocks
    // private ParametreCapteurService parametreCapteurService;

    // @BeforeEach
    // public void setUp() {
    //     MockitoAnnotations.openMocks(this);
    // }

    // @Test
    // public void testGetAllParametreCapteurs() {
    //     ParametreCapteur parametreCapteur1 = new ParametreCapteur();
    //     ParametreCapteur parametreCapteur2 = new ParametreCapteur();
    //     when(parametreCapteurRepo.findAll()).thenReturn(Arrays.asList(parametreCapteur1, parametreCapteur2));

    //     List<ParametreCapteur> parametreCapteurs = parametreCapteurService.getAllParametreCapteurs();

    //     assertEquals(2, parametreCapteurs.size());
    //     verify(parametreCapteurRepo, times(1)).findAll();
    // }

    // @Test
    // public void testGetParametreCapteurById_Found() {
    //     ParametreCapteur parametreCapteur = new ParametreCapteur();
    //     parametreCapteur.setId(1);
    //     when(parametreCapteurRepo.findById(1)).thenReturn(Optional.of(parametreCapteur));

    //     ParametreCapteur result = parametreCapteurService.getParametreCapteurById(1);

    //     assertNotNull(result);
    //     assertEquals(1, result.getId());
    // }

    // @Test
    // public void testGetParametreCapteurById_NotFound() {
    //     when(parametreCapteurRepo.findById(1)).thenReturn(Optional.empty());

    //     ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
    //         parametreCapteurService.getParametreCapteurById(1);
    //     });

    //     assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
    //     assertThat(ex.getStatusCode().value()).isEqualTo(404);
    // }

    // @Test
    // public void testSaveParametreCapteur() {
    //     ParametreCapteur parametreCapteur = new ParametreCapteur();
    //     parametreCapteur.setId(1);
    //     when(parametreCapteurRepo.save(parametreCapteur)).thenReturn(parametreCapteur);
    //     ParametreCapteur result = parametreCapteurService.saveParametreCapteur(parametreCapteur);
    //     assertNotNull(result);
    //     assertEquals(1, result.getId());
    // }

    // @Test
    // public void testUpdateParametreCapteur() {
    //     ParametreCapteur parametreCapteur = new ParametreCapteur();
    //     parametreCapteur.setId(1);
    
    //     when(parametreCapteurRepo.existsById(1)).thenReturn(true);
    
    //     when(parametreCapteurRepo.save(parametreCapteur)).thenReturn(parametreCapteur);
    
    //     ParametreCapteur result = parametreCapteurService.updateParametreCapteur(parametreCapteur);
    
    //     assertEquals(1, result.getId());
    // }

    // @Test
    // public void testDeleteParametreCapteurById() {
    //     ParametreCapteur parametreCapteur = new ParametreCapteur();
    //     parametreCapteur.setId(1);
    
    //     when(parametreCapteurRepo.existsById(1)).thenReturn(true); 
    
    //     parametreCapteurService.deleteParametreCapteurById(1);
    
    //     verify(parametreCapteurRepo, times(1)).deleteById(1);
    // }
}
