package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.usmb.but3.sae_s6_api.repository.UniteMesurerRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniteMesurerService {
  private final UniteMesurerRepo UniteMesurerRepo;

    public List<UniteMesurer> getAllUniteMesurers(){
        return UniteMesurerRepo.findAll();
    }

    public UniteMesurer getUniteMesurerById(Integer id){
        return UniteMesurerRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public UniteMesurer saveUniteMesurer(UniteMesurer uniteMesurer){
        return UniteMesurerRepo.save(uniteMesurer);
    }

    public UniteMesurer updateUniteMesurer(UniteMesurer uniteMesurer){
        if (!UniteMesurerRepo.existsById(uniteMesurer.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return UniteMesurerRepo.save(uniteMesurer);
    }

    public void deleteUniteMesurerById(Integer id){
        if (!UniteMesurerRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        UniteMesurerRepo.deleteById(id);
    }
}
