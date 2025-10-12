package com.mchscorp.integrajob.datapi.controller.bne;

import com.mchscorp.integrajob.datapi.service.bne.BNETokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bne")
public class BNETokenController {

    private final BNETokenService tokenService;

    public BNETokenController(BNETokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/token")
    public String obtenerToken() {
        return tokenService.obtenerToken();
    }
}
