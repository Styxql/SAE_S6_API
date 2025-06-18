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

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.repository.TypeSalleRepo;

public class TypeSalleServiceTest {
    @Mock
    private TypeSalleRepo typeSalleRepo;

    @InjectMocks
    private TypeSalleService typeSalleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTypeSalles() {
        TypeSalle typeSalle1 = new TypeSalle();
        TypeSalle typeSalle2 = new TypeSalle();
        when(typeSalleRepo.findAll()).thenReturn(Arrays.asList(typeSalle1, typeSalle2));

        List<TypeSalle> typeSalles = typeSalleService.getAllTypeSalles();

        assertEquals(2, typeSalles.size());
        verify(typeSalleRepo, times(1)).findAll();
    }

    @Test
    public void testGetTypeSalleById_Found() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
        when(typeSalleRepo.findById(1)).thenReturn(Optional.of(typeSalle));

        TypeSalle result = typeSalleService.getTypeSalleById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetTypeSalleById_NotFound() {
        when(typeSalleRepo.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            typeSalleService.getTypeSalleById(1);
        });

        assertThat(ex.getReason()).isEqualTo("404 : Id Not Found");
        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    public void testSaveTypeSalle() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
        when(typeSalleRepo.save(typeSalle)).thenReturn(typeSalle);
        TypeSalle result = typeSalleService.saveTypeSalle(typeSalle);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateTypeSalle() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
    
        when(typeSalleRepo.existsById(1)).thenReturn(true);
    
        when(typeSalleRepo.save(typeSalle)).thenReturn(typeSalle);
    
        TypeSalle result = typeSalleService.updateTypeSalle(typeSalle);
    
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteTypeSalleById() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
    
        when(typeSalleRepo.existsById(1)).thenReturn(true); 
    
        typeSalleService.deleteTypeSalleById(1);
    
        verify(typeSalleRepo, times(1)).deleteById(1);
    }
    
}
