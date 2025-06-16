package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.repository.EquipementInstalleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipementInstalleService {
    private final EquipementInstalleRepo EquipementInstalleRepo;

    public List<EquipementInstalle> getAllEquipementInstalles(){
        return EquipementInstalleRepo.findAll();
    }

    public EquipementInstalle getEquipementInstalleById(Integer id){
        return EquipementInstalleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public EquipementInstalle saveEquipementInstalle(EquipementInstalle EquipementInstalle){
        return EquipementInstalleRepo.save(EquipementInstalle);
    }

    public EquipementInstalle updateEquipementInstalle(EquipementInstalle EquipementInstalle){
        if (!EquipementInstalleRepo.existsById(EquipementInstalle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return EquipementInstalleRepo.save(EquipementInstalle);
    }

    public void deleteEquipementInstalleById(Integer id){
        if (!EquipementInstalleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        EquipementInstalleRepo.deleteById(id);
    }
}

