package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.repository.CapteurInstalleRepo;
import com.usmb.but3.sae_s6_api.repository.CapteurRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CapteurService {
    private final CapteurRepo capteurRepo;
    private final CapteurInstalleRepo capteurInstalleRepo;

    public List<Capteur> getAllCapteurs(){
        return capteurRepo.findAll();
    }

    public Capteur getCapteurById(Integer id){
        return capteurRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public Capteur saveCapteur(Capteur capteur){
        return capteurRepo.save(capteur);
    }

    public Capteur updateCapteur(Capteur capteur){
        if (!capteurRepo.existsById(capteur.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return capteurRepo.save(capteur);
    }

    public void deleteCapteurById(Integer id){
    List<CapteurInstalle> installs = capteurInstalleRepo.findByCapteurInstalles(id);
        capteurInstalleRepo.deleteAll(installs);

        if (!capteurRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Equipement non trouvé");
        }
        capteurRepo.deleteById(id);
    }
}