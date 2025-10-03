package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.Ubicacion;
import com.mchscorp.integrajob.datapi.repository.UbicacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Ubicacion> findAll() {
        return ubicacionRepository.findAll();
    }

    public Optional<Ubicacion> findById(Integer id) {
        return ubicacionRepository.findById(id);
    }

    public List<Ubicacion> findByRegion(String region) {
        return ubicacionRepository.findByRegion(region);
    }

    public List<Ubicacion> findByComuna(String comuna) {
        return ubicacionRepository.findByComuna(comuna);
    }

    public Ubicacion save(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    public void delete(Integer id) {
        ubicacionRepository.deleteById(id);
    }
}
