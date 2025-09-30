package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.OfertaJob;
import com.mchscorp.integrajob.datapi.repository.OfertaJobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfertaJobService {

    private final OfertaJobRepository ofertaJobRepository;

    public OfertaJobService(OfertaJobRepository ofertaJobRepository) {
        this.ofertaJobRepository = ofertaJobRepository;
    }

    public List<OfertaJob> findAll() {
        return ofertaJobRepository.findAll();
    }

    public Optional<OfertaJob> findById(Integer id) {
        return ofertaJobRepository.findById(id);
    }

    public OfertaJob save(OfertaJob ofertaJob) {
        return ofertaJobRepository.save(ofertaJob);
    }

    public void delete(Integer id) {
        ofertaJobRepository.deleteById(id);
    }
}