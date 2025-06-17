package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.repository.EquipementRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipementService {
    private final EquipementRepo equipementRepo;

    public List<Equipement> getAllEquipements(){
        return equipementRepo.findAll();
    }

    public Equipement getEquipementById(Integer id){
        return equipementRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public Equipement saveEquipement(Equipement capteur){
        return equipementRepo.save(capteur);
    }

    public Equipement updateEquipement(Equipement capteur){
        if (!equipementRepo.existsById(capteur.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return equipementRepo.save(capteur);
    }

    public void deleteEquipementById(Integer id){
        if (!equipementRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        equipementRepo.deleteById(id);
    }
}