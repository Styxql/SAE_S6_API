package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.repository.TypeEquipementRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeEquipementService {
    private final TypeEquipementRepo typeEquipementRepo;

    public List<TypeEquipement> getAllTypeEquipement(){
        return typeEquipementRepo.findAll();
    }

    public TypeEquipement getTypeEquipementById(Integer id){
        return typeEquipementRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public TypeEquipement saveTypeEquipement(TypeEquipement typeEquipement){
        return typeEquipementRepo.save(typeEquipement);
    }

    public TypeEquipement updateTypeEquipement(TypeEquipement typeEquipement){
        if (!typeEquipementRepo.existsById(typeEquipement.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return typeEquipementRepo.save(typeEquipement);
    }

    public void deleteTypeEquipementtById(Integer id){
        if (!typeEquipementRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        typeEquipementRepo.deleteById(id);
    }
}
