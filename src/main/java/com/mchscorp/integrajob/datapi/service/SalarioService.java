package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.Salario;
import com.mchscorp.integrajob.datapi.repository.SalarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalarioService {

    private final SalarioRepository salarioRepository;

    public SalarioService(SalarioRepository salarioRepository) {
        this.salarioRepository = salarioRepository;
    }

    public List<Salario> findAll() {
        return salarioRepository.findAll();
    }

    public Optional<Salario> findById(Integer id) {
        return salarioRepository.findById(id);
    }

    public List<Salario> findByOferta(Integer idOferta) {
        return salarioRepository.findByOfertaIdOferta(idOferta);
    }

    public Salario save(Salario salario) {
        return salarioRepository.save(salario);
    }

    public void delete(Integer id) {
        salarioRepository.deleteById(id);
    }
}
