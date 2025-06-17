package com.usmb.but3.sae_s6_api.service;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.repository.SalleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalleService {
    private final SalleRepo salleRepo;

    public List<Salle> getAllSalles(){
        return salleRepo.findAll();
    }

    public Salle getSalleById(Integer id){
        return salleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public Salle saveSalle(Salle Salle){
        return salleRepo.save(Salle);
    }

    public Salle updateSalle(Salle Salle){
        if (!salleRepo.existsById(Salle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return salleRepo.save(Salle);
    }

    public void deleteSalleById(Integer id){
        if (!salleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        salleRepo.deleteById(id);
    }

    public List<Salle> getByBatimentId(Integer batimentId) {
        return salleRepo.findByBatimentId(batimentId);
    }  
}