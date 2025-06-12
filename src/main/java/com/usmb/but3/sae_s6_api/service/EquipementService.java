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
    private final EquipementRepo capteurRepo;

    public List<Equipement> getAllEquipements(){
        return capteurRepo.findAll();
    }

    public Equipement getEquipementById(Integer id){
        return capteurRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public Equipement saveEquipement(Equipement capteur){
        return capteurRepo.save(capteur);
    }

    public Equipement updateEquipement(Equipement capteur){
        if (!capteurRepo.existsById(capteur.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return capteurRepo.save(capteur);
    }

    public void deleteEquipementById(Integer id){
        if (!capteurRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        capteurRepo.deleteById(id);
    }
}