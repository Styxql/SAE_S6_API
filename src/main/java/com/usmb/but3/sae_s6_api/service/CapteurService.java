package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.repository.CapteurInstalleRepo;
import com.usmb.but3.sae_s6_api.repository.CapteurRepo;
import com.usmb.but3.sae_s6_api.repository.ParametreCapteurRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CapteurService {
    private final CapteurRepo capteurRepo;
    private final CapteurInstalleRepo capteurInstalleRepo;
    private final ParametreCapteurRepo parametreCapteurRepo;

    public List<Capteur> getAllCapteurs() {
        return capteurRepo.findAll();
    }

    public Capteur getCapteurById(Integer id) {
        return capteurRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found"));
    }

    public Capteur saveCapteur(Capteur capteur) {
        return capteurRepo.save(capteur);
    }

    public Capteur updateCapteur(Capteur capteur) {
        if (!capteurRepo.existsById(capteur.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        return capteurRepo.save(capteur);
    }

    public void deleteCapteurById(Integer id) {
        List<CapteurInstalle> installs = capteurInstalleRepo.findByCapteurId(id);
        capteurInstalleRepo.deleteAll(installs);
        List<ParametreCapteur> param = parametreCapteurRepo.findByCapteurId(id);
        parametreCapteurRepo.deleteAll(param);

        if (!capteurRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Equipement non trouvé");
        }
        capteurRepo.deleteById(id);
    }

    public Capteur getCapteurByIdWithParam(Integer id) {
        return capteurRepo.findByIdWithParametres(id)
                .orElseThrow(() -> new EntityNotFoundException("Capteur non trouvé avec id " + id));
    }
}