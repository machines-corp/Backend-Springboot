package com.mchscorp.integrajob.datapi.controller.bne;

import com.mchscorp.integrajob.datapi.service.bne.BneJobService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bne")
public class BneController {

    private final BneJobService bneJobService;

    public BneController(BneJobService bneJobService) {
        this.bneJobService = bneJobService;
    }

    @PostMapping("/import")
    public String importar() {
        try {
            bneJobService.importarOfertasDesdeBNE();
            return "Ofertas importadas automáticamente usando el token del sistema.";
        } catch (Exception e) {
            return "Error al importar: " + e.getMessage();
        }
    }
}
