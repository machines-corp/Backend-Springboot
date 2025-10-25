package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.CategoriaOcupacional;
import com.mchscorp.integrajob.datapi.service.CategoriaOcupacionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaOcupacionalController {

    private final CategoriaOcupacionalService categoriaService;

    public CategoriaOcupacionalController(CategoriaOcupacionalService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaOcupacional> getAll() {
        return categoriaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaOcupacional> getById(@PathVariable Integer id) {
        return categoriaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CategoriaOcupacional create(@RequestBody CategoriaOcupacional categoria) {
        return categoriaService.save(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaOcupacional> update(@PathVariable Integer id, @RequestBody CategoriaOcupacional categoria) {
        return categoriaService.findById(id)
                .map(existing -> {
                    categoria.setIdCategoria(id);
                    return ResponseEntity.ok(categoriaService.save(categoria));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return categoriaService.findById(id)
                .map(existing -> {
                    categoriaService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
