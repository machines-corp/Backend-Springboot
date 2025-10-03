package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.Salario;
import com.mchscorp.integrajob.datapi.service.SalarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salarios")
public class SalarioController {

    private final SalarioService salarioService;

    public SalarioController(SalarioService salarioService) {
        this.salarioService = salarioService;
    }

    @GetMapping
    public List<Salario> getAll() {
        return salarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salario> getById(@PathVariable Integer id) {
        return salarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/oferta/{idOferta}")
    public List<Salario> getByOferta(@PathVariable Integer idOferta) {
        return salarioService.findByOferta(idOferta);
    }

    @PostMapping
    public Salario create(@RequestBody Salario salario) {
        return salarioService.save(salario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Salario> update(@PathVariable Integer id, @RequestBody Salario salario) {
        return salarioService.findById(id)
                .map(existing -> {
                    salario.setIdSalario(id);
                    return ResponseEntity.ok(salarioService.save(salario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return salarioService.findById(id)
                .map(existing -> {
                    salarioService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
