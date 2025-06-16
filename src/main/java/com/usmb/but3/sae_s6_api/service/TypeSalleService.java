package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.repository.TypeSalleRepo;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class TypeSalleService {
    private final TypeSalleRepo typeSalleRepo;

    public List<TypeSalle> getAllTypeSalles(){
        return typeSalleRepo.findAll();
    }

    public TypeSalle getTypeSalleById(Integer id){
        return typeSalleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public TypeSalle saveTypeSalle(TypeSalle typeSalle){
        return typeSalleRepo.save(typeSalle);
    }

    public TypeSalle updateTypeSalle(TypeSalle typeSalle){
        if (!typeSalleRepo.existsById(typeSalle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return typeSalleRepo.save(typeSalle);
    }   

    public void deleteTypeSalleById(Integer id){
        if (!typeSalleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        typeSalleRepo.deleteById(id);
    }
}
