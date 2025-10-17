package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.service.SinonimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class SinonimoController {

    private final SinonimoService sinonimoService;

    public SinonimoController(SinonimoService sinonimoService) {
        this.sinonimoService = sinonimoService;
    }

    @PostMapping("/parse")
    public ResponseEntity<Map<String, Object>> parsePrompt(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        Map<String, ?> resultado = sinonimoService.analizarPrompt(prompt);
        return ResponseEntity.ok(Map.of("resultado", resultado));
    }
}
