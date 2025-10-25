package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.AcomodoAccesibilidad;
import com.mchscorp.integrajob.datapi.repository.AcomodoAccesibilidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcomodoAccesibilidadService {

    private final AcomodoAccesibilidadRepository acomodoRepo;

    public AcomodoAccesibilidadService(AcomodoAccesibilidadRepository acomodoRepo) {
        this.acomodoRepo = acomodoRepo;
    }

    public List<AcomodoAccesibilidad> findAll() {
        return acomodoRepo.findAll();
    }

    public Optional<AcomodoAccesibilidad> findById(Integer id) {
        return acomodoRepo.findById(id);
    }

    public AcomodoAccesibilidad save(AcomodoAccesibilidad acomodo) {
        return acomodoRepo.save(acomodo);
    }

    public void delete(Integer id) {
        acomodoRepo.deleteById(id);
    }
}
