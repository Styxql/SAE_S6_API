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
    private final SalleRepo SalleRepo;

    public List<Salle> getAllSalles(){
        return SalleRepo.findAll();
    }

    public Salle getSalleById(Integer id){
        return SalleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public Salle saveSalle(Salle Salle){
        return SalleRepo.save(Salle);
    }

    public Salle updateSalle(Salle Salle){
        if (!SalleRepo.existsById(Salle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return SalleRepo.save(Salle);
    }

    public void deleteSalleById(Integer id){
        if (!SalleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        SalleRepo.deleteById(id);
    }
}