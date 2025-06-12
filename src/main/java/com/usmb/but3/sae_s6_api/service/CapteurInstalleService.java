package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.repository.CapteurInstalleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CapteurInstalleService {
    private final CapteurInstalleRepo capteurInstalleRepo;

    public List<CapteurInstalle> getAllCapteurInstalles(){
        return capteurInstalleRepo.findAll();
    }

    public CapteurInstalle getCapteurInstalleById(Integer id){
        return capteurInstalleRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public CapteurInstalle saveCapteurInstalle(CapteurInstalle capteurInstalle){
        return capteurInstalleRepo.save(capteurInstalle);
    }

    public CapteurInstalle updateCapteurInstalle(CapteurInstalle capteurInstalle){
        if (!capteurInstalleRepo.existsById(capteurInstalle.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return capteurInstalleRepo.save(capteurInstalle);
    }

    public void deleteCapteurInstalleById(Integer id){
        if (!capteurInstalleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        capteurInstalleRepo.deleteById(id);
    }
}
