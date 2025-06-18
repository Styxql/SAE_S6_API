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
  private final UniteMesurerRepo uniteMesurerRepo;

    public List<UniteMesurer> getAllUniteMesurers(){
        return uniteMesurerRepo.findAll();
    }

    public UniteMesurer getUniteMesurerById(Integer id){
        return uniteMesurerRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public UniteMesurer saveUniteMesurer(UniteMesurer uniteMesurer){
        return uniteMesurerRepo.save(uniteMesurer);
    }

    public UniteMesurer updateUniteMesurer(UniteMesurer uniteMesurer){
        if (!uniteMesurerRepo.existsById(uniteMesurer.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return uniteMesurerRepo.save(uniteMesurer);
    }

    public void deleteUniteMesurerById(Integer id){
        if (!uniteMesurerRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        uniteMesurerRepo.deleteById(id);
    }
}
