package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.OfertaJob;
import com.mchscorp.integrajob.datapi.dto.OfertaJobDTO;
import com.mchscorp.integrajob.datapi.service.OfertaJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaJobController {

    private final OfertaJobService ofertaJobService;

    public OfertaJobController(OfertaJobService ofertaJobService) {
        this.ofertaJobService = ofertaJobService;
    }

    @GetMapping
    public List<OfertaJobDTO> getAllDTO() {return ofertaJobService.findAllDTO();}

    @GetMapping("/{id}")
    public ResponseEntity<OfertaJob> getById(@PathVariable Integer id) {
        return ofertaJobService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OfertaJob create(@RequestBody OfertaJob ofertaJob) {
        return ofertaJobService.save(ofertaJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfertaJob> update(@PathVariable Integer id, @RequestBody OfertaJob ofertaJob) {
        return ofertaJobService.findById(id)
                .map(existing -> {
                    ofertaJob.setIdOferta(id);
                    return ResponseEntity.ok(ofertaJobService.save(ofertaJob));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return ofertaJobService.findById(id)
                .map(existing -> {
                    ofertaJobService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
//    @GetMapping("/oferta/{idOferta}")
//    public List<RequisitoEducacion> getByOferta(@PathVariable Integer idOferta) {
//        return requisitoEducacionService.findByOferta(idOferta);
//    }

}
