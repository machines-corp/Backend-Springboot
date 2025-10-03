package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.Ubicacion;
import com.mchscorp.integrajob.datapi.service.UbicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping
    public List<Ubicacion> getAll() {
        return ubicacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> getById(@PathVariable Integer id) {
        return ubicacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/region/{region}")
    public List<Ubicacion> getByRegion(@PathVariable String region) {
        return ubicacionService.findByRegion(region);
    }

    @GetMapping("/comuna/{comuna}")
    public List<Ubicacion> getByComuna(@PathVariable String comuna) {
        return ubicacionService.findByComuna(comuna);
    }

    @PostMapping
    public Ubicacion create(@RequestBody Ubicacion ubicacion) {
        return ubicacionService.save(ubicacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ubicacion> update(@PathVariable Integer id, @RequestBody Ubicacion ubicacion) {
        return ubicacionService.findById(id)
                .map(existing -> {
                    ubicacion.setIdUbicacion(id);
                    return ResponseEntity.ok(ubicacionService.save(ubicacion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return ubicacionService.findById(id)
                .map(existing -> {
                    ubicacionService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
