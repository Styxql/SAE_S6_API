package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.repository.BatimentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatimentService {
    private final BatimentRepo batimentRepo;

    public List<Batiment> getAllBatiments(){
        return batimentRepo.findAll();
    }

    public Batiment getBatimentById(Integer id){
        return batimentRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public Batiment saveBatiment(Batiment batiment){
        return batimentRepo.save(batiment);
    }

    public Batiment updateBatiment(Batiment batiment){
        if (!batimentRepo.existsById(batiment.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return batimentRepo.save(batiment);
    }

    public void deleteBatimentById(Integer id){
        if (!batimentRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        batimentRepo.deleteById(id);
    }
}
