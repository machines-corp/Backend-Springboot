package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.RequisitoEducacion;
import com.mchscorp.integrajob.datapi.repository.RequisitoEducacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequisitoEducacionService {

    private final RequisitoEducacionRepository requisitoEducacionRepository;

    public RequisitoEducacionService(RequisitoEducacionRepository requisitoEducacionRepository) {
        this.requisitoEducacionRepository = requisitoEducacionRepository;
    }

    public List<RequisitoEducacion> findAll() {
        return requisitoEducacionRepository.findAll();
    }

    public Optional<RequisitoEducacion> findById(Integer id) {
        return requisitoEducacionRepository.findById(id);
    }

    public RequisitoEducacion save(RequisitoEducacion requisito) {
        return requisitoEducacionRepository.save(requisito);
    }

    public void delete(Integer id) {
        requisitoEducacionRepository.deleteById(id);
    }
}
