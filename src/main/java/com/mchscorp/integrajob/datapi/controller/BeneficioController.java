package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.Beneficio;
import com.mchscorp.integrajob.datapi.service.BeneficioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficios")
public class BeneficioController {

    private final BeneficioService beneficioService;

    public BeneficioController(BeneficioService beneficioService) {
        this.beneficioService = beneficioService;
    }

    @GetMapping
    public List<Beneficio> getAll() {
        return beneficioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> getById(@PathVariable Integer id) {
        return beneficioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Beneficio create(@RequestBody Beneficio beneficio) {
        return beneficioService.save(beneficio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficio> update(@PathVariable Integer id, @RequestBody Beneficio beneficio) {
        return beneficioService.findById(id)
                .map(existing -> {
                    beneficio.setIdBeneficio(id);
                    return ResponseEntity.ok(beneficioService.save(beneficio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return beneficioService.findById(id)
                .map(existing -> {
                    beneficioService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
