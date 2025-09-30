package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.RequisitoEducacion;
import com.mchscorp.integrajob.datapi.service.RequisitoEducacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requisitos-educacion")
public class RequisitoEducacionController {

    private final RequisitoEducacionService requisitoEducacionService;

    public RequisitoEducacionController(RequisitoEducacionService requisitoEducacionService) {
        this.requisitoEducacionService = requisitoEducacionService;
    }

    @GetMapping
    public List<RequisitoEducacion> getAll() {
        return requisitoEducacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequisitoEducacion> getById(@PathVariable Integer id) {
        return requisitoEducacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RequisitoEducacion create(@RequestBody RequisitoEducacion requisito) {
        return requisitoEducacionService.save(requisito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequisitoEducacion> update(@PathVariable Integer id, @RequestBody RequisitoEducacion requisito) {
        return requisitoEducacionService.findById(id)
                .map(existing -> {
                    requisito.setIdReqEdu(id);
                    return ResponseEntity.ok(requisitoEducacionService.save(requisito));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return requisitoEducacionService.findById(id)
                .map(existing -> {
                    requisitoEducacionService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
