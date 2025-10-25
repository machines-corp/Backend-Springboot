package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.Beneficio;
import com.mchscorp.integrajob.datapi.repository.BeneficioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficioService {

    private final BeneficioRepository beneficioRepository;

    public BeneficioService(BeneficioRepository beneficioRepository) {
        this.beneficioRepository = beneficioRepository;
    }

    public List<Beneficio> findAll() {
        return beneficioRepository.findAll();
    }

    public Optional<Beneficio> findById(Integer id) {
        return beneficioRepository.findById(id);
    }

    public Beneficio save(Beneficio beneficio) {
        return beneficioRepository.save(beneficio);
    }

    public void delete(Integer id) {
        beneficioRepository.deleteById(id);
    }
}
