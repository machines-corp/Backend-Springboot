package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.AcomodoAccesibilidad;
import com.mchscorp.integrajob.datapi.service.AcomodoAccesibilidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acomodos")
public class AcomodoAccesibilidadController {

    private final AcomodoAccesibilidadService acomodoService;

    public AcomodoAccesibilidadController(AcomodoAccesibilidadService acomodoService) {
        this.acomodoService = acomodoService;
    }

    @GetMapping
    public List<AcomodoAccesibilidad> getAll() {
        return acomodoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcomodoAccesibilidad> getById(@PathVariable Integer id) {
        return acomodoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AcomodoAccesibilidad create(@RequestBody AcomodoAccesibilidad acomodo) {
        return acomodoService.save(acomodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcomodoAccesibilidad> update(@PathVariable Integer id, @RequestBody AcomodoAccesibilidad acomodo) {
        return acomodoService.findById(id)
                .map(existing -> {
                    acomodo.setIdAcomodo(id);
                    return ResponseEntity.ok(acomodoService.save(acomodo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return acomodoService.findById(id)
                .map(existing -> {
                    acomodoService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
