package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.Taxonomia;
import com.mchscorp.integrajob.datapi.service.TaxonomiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxonomias")
public class TaxonomiaController {

    private final TaxonomiaService taxonomiaService;

    public TaxonomiaController(TaxonomiaService taxonomiaService) {
        this.taxonomiaService = taxonomiaService;
    }

    @GetMapping
    public List<Taxonomia> getAll() {
        return taxonomiaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taxonomia> getById(@PathVariable Integer id) {
        return taxonomiaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public List<Taxonomia> getByCategoria(@PathVariable String categoria) {
        return taxonomiaService.findByCategoria(categoria);
    }

    @PostMapping
    public Taxonomia create(@RequestBody Taxonomia taxonomia) {
        return taxonomiaService.save(taxonomia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Taxonomia> update(@PathVariable Integer id, @RequestBody Taxonomia taxonomia) {
        return taxonomiaService.findById(id)
                .map(existing -> {
                    taxonomia.setIdTaxonomia(id);
                    return ResponseEntity.ok(taxonomiaService.save(taxonomia));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return taxonomiaService.findById(id)
                .map(existing -> {
                    taxonomiaService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
