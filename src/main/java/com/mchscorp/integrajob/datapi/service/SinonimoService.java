package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.Sinonimo;
import com.mchscorp.integrajob.datapi.repository.SinonimoRepository;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class SinonimoService {

    private final SinonimoRepository sinonimoRepository;

    public SinonimoService(SinonimoRepository sinonimoRepository) {
        this.sinonimoRepository = sinonimoRepository;
    }

    public Map<String, List<String>> analizarPrompt(String prompt) {
        Map<String, List<String>> resultado = new LinkedHashMap<>();
        String texto = normalizar(prompt);

        List<Sinonimo> sinonimos = sinonimoRepository.findAll();
        for (Sinonimo s : sinonimos) {
            if (texto.contains(s.getPalabra().toLowerCase())) {
                String categoria = s.getCaracteristica().getAgrupacion().getNombre();
                String valor = s.getCaracteristica().getNombre();
                resultado.computeIfAbsent(categoria, k -> new ArrayList<>()).add(valor);
            }
        }
        return resultado;
    }

    private String normalizar(String texto) {
        return texto.toLowerCase()
            .replace("á", "a")
            .replace("é", "e")
            .replace("í", "i")
            .replace("ó", "o")
            .replace("ú", "u");
    }
}
