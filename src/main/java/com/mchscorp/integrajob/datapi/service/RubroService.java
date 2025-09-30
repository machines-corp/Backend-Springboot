package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.Rubro;
import com.mchscorp.integrajob.datapi.repository.RubroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RubroService {

    private final RubroRepository rubroRepository;

    public RubroService(RubroRepository rubroRepository) {
        this.rubroRepository = rubroRepository;
    }

    public List<Rubro> findAll() {
        return rubroRepository.findAll();
    }

    public Optional<Rubro> findById(Integer id) {
        return rubroRepository.findById(id);
    }

    public Rubro save(Rubro rubro) {
        return rubroRepository.save(rubro);
    }

    public void delete(Integer id) {
        rubroRepository.deleteById(id);
    }
}
