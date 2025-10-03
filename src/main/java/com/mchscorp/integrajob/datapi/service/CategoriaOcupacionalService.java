package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.CategoriaOcupacional;
import com.mchscorp.integrajob.datapi.repository.CategoriaOcupacionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaOcupacionalService {

    private final CategoriaOcupacionalRepository categoriaRepo;

    public CategoriaOcupacionalService(CategoriaOcupacionalRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    public List<CategoriaOcupacional> findAll() {
        return categoriaRepo.findAll();
    }

    public Optional<CategoriaOcupacional> findById(Integer id) {
        return categoriaRepo.findById(id);
    }

    public CategoriaOcupacional save(CategoriaOcupacional categoria) {
        return categoriaRepo.save(categoria);
    }

    public void delete(Integer id) {
        categoriaRepo.deleteById(id);
    }
}
