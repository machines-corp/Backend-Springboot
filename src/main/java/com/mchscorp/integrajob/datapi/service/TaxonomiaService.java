package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.Taxonomia;
import com.mchscorp.integrajob.datapi.repository.TaxonomiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxonomiaService {

    private final TaxonomiaRepository taxonomiaRepository;

    public TaxonomiaService(TaxonomiaRepository taxonomiaRepository) {
        this.taxonomiaRepository = taxonomiaRepository;
    }

    public List<Taxonomia> findAll() {
        return taxonomiaRepository.findAll();
    }

    public Optional<Taxonomia> findById(Integer id) {
        return taxonomiaRepository.findById(id);
    }

    public List<Taxonomia> findByCategoria(String categoria) {
        return taxonomiaRepository.findByCategoria(categoria);
    }

    public Taxonomia save(Taxonomia taxonomia) {
        return taxonomiaRepository.save(taxonomia);
    }

    public void delete(Integer id) {
        taxonomiaRepository.deleteById(id);
    }
}
