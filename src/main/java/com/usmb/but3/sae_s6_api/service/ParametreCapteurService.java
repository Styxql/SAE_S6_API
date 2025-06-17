package com.usmb.but3.sae_s6_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.entity.ParametreCapteurId;
import com.usmb.but3.sae_s6_api.repository.ParametreCapteurRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParametreCapteurService {

    private final ParametreCapteurRepo parametreCapteurRepo;

    public List<ParametreCapteur> getAllParametreCapteurs() {
        return parametreCapteurRepo.findAll();
    }

    public ParametreCapteur getParametreCapteurById(Integer capteurId, Integer uniteMesurerId) {
        ParametreCapteurId id = new ParametreCapteurId(uniteMesurerId, capteurId);
        return parametreCapteurRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found"));
    }


    public ParametreCapteur saveParametreCapteur(ParametreCapteur parametreCapteur) {
        return parametreCapteurRepo.save(parametreCapteur);
    }

    public ParametreCapteur updateParametreCapteur(ParametreCapteur parametreCapteur) {
        ParametreCapteurId id = new ParametreCapteurId(
            parametreCapteur.getUniteMesurerId(), 
            parametreCapteur.getCapteurId()
        );
        if (!parametreCapteurRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"404 : Id Not Found");
        }
        return parametreCapteurRepo.save(parametreCapteur);
    }

    public void deleteParametreCapteurById(Integer capteurId, Integer uniteMesurerId) {
        ParametreCapteurId id = new ParametreCapteurId(uniteMesurerId, capteurId);
        if (!parametreCapteurRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 : Id Not Found");
        }
        parametreCapteurRepo.deleteById(id);
    }
}
