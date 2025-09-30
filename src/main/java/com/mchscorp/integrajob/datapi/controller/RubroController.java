package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.Rubro;
import com.mchscorp.integrajob.datapi.service.RubroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rubros")
public class RubroController {

    private final RubroService rubroService;

    public RubroController(RubroService rubroService) {
        this.rubroService = rubroService;
    }

    @GetMapping
    public List<Rubro> getAll() {
        return rubroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rubro> getById(@PathVariable Integer id) {
        return rubroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rubro create(@RequestBody Rubro rubro) {
        return rubroService.save(rubro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rubro> update(@PathVariable Integer id, @RequestBody Rubro rubro) {
        return rubroService.findById(id)
                .map(existing -> {
                    rubro.setIdRubro(id);
                    return ResponseEntity.ok(rubroService.save(rubro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return rubroService.findById(id)
                .map(existing -> {
                    rubroService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
