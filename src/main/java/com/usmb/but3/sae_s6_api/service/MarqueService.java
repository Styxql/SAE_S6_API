package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.repository.MarqueRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarqueService {
    private final MarqueRepo MarqueRepo;

    public List<Marque> getAllMarques(){
        return MarqueRepo.findAll();
    }

    public Marque getMarqueById(Integer id){
        return MarqueRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public Marque saveMarque(Marque Marque){
        return MarqueRepo.save(Marque);
    }

    public Marque updateMarque(Marque Marque){
        if (!MarqueRepo.existsById(Marque.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return MarqueRepo.save(Marque);
    }

    public void deleteMarqueById(Integer id){
        if (!MarqueRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        MarqueRepo.deleteById(id);
    }
}