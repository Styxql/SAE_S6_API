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
    private final EquipementInstalleRepo equipementInstalleRepo;

    public List<EquipementInstalle> getAllEquipementInstalles(){
        return equipementInstalleRepo.findAll();
    }

    public EquipementInstalle getEquipementInstalleById(Integer id){
        return equipementInstalleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public EquipementInstalle saveEquipementInstalle(EquipementInstalle EquipementInstalle){
        return equipementInstalleRepo.save(EquipementInstalle);
    }

    public EquipementInstalle updateEquipementInstalle(EquipementInstalle EquipementInstalle){
        if (!equipementInstalleRepo.existsById(EquipementInstalle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return equipementInstalleRepo.save(EquipementInstalle);
    }

    public void deleteEquipementInstalleById(Integer id){
        if (!equipementInstalleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        equipementInstalleRepo.deleteById(id);
    }

    public List<EquipementInstalle> getBySalleId(Integer salleId) {
        return equipementInstalleRepo.findBySalleId(salleId);
    }  
}

