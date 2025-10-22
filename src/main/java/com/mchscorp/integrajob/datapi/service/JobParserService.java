package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.dto.ParsedPromptDTO;
import com.mchscorp.integrajob.datapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class JobParserService {

    private final SinonimoRepository sinonimoRepo;
    private final CaracteristicaRepository caracteristicaRepo;
    private final AgrupacionRepository agrupacionRepo;

    public ParsedPromptDTO parsePrompt(String prompt) {
        String raw = normalize(prompt);

        // --- Detectar moneda y salario ---
        String currency = raw.contains("usd") || raw.contains("$") ? "USD" :
                raw.contains("clp") || raw.contains("pesos") ? "CLP" : null;
        Integer salaryMin = extractSalary(raw);

        Map<String, List<String>> include = new HashMap<>();
        Map<String, List<String>> exclude = new HashMap<>();

        Map<String, String> invSynonyms = loadSynonyms(); // palabra -> característica canonical

        // Detectar negaciones ("no X", "sin X")
        List<String> negTerms = extractNegations(raw);

        // --- Clasificación por agrupación ---
        agrupacionRepo.findAll().forEach(agr -> {
            String agrName = agr.getNombre().toLowerCase();
            caracteristicaRepo.findByAgrupacion(agr).forEach(car -> {
                String canon = car.getNombre().toLowerCase();

                // incluir
                if (raw.contains(canon)) {
                    include.computeIfAbsent(agrName, k -> new ArrayList<>()).add(car.getNombre());
                }

                // sinónimos de esa característica
                sinonimoRepo.findByCaracteristica(car).forEach(s -> {
                    if (raw.contains(s.getPalabra().toLowerCase())) {
                        include.computeIfAbsent(agrName, k -> new ArrayList<>()).add(car.getNombre());
                    }
                });

                // exclusiones
                for (String neg : negTerms) {
                    if (neg.contains(canon)) {
                        exclude.computeIfAbsent(agrName, k -> new ArrayList<>()).add(car.getNombre());
                    }
                }
            });
        });

        // Eliminar duplicados
        include.replaceAll((k,v) -> v.stream().distinct().toList());
        exclude.replaceAll((k,v) -> v.stream().distinct().toList());

        return ParsedPromptDTO.builder()
                .include(include)
                .exclude(exclude)
                .salaryMin(salaryMin)
                .currency(currency != null ? currency : "USD")
                .build();
    }

    // --- Utilidades ---
    private String normalize(String s) {
        return s.toLowerCase()
                .replace("á","a").replace("é","e").replace("í","i")
                .replace("ó","o").replace("ú","u")
                .replaceAll("[^a-z0-9\\s\\-\\+\\$\\.]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private Integer extractSalary(String text) {
        Matcher m = Pattern.compile("\\d[\\d\\.]*").matcher(text);
        if (m.find()) {
            try { return Integer.parseInt(m.group().replace(".", "")); }
            catch (NumberFormatException ignored) {}
        }
        return null;
    }

    private List<String> extractNegations(String text) {
        List<String> neg = new ArrayList<>();
        Pattern p1 = Pattern.compile("no\\s+([a-z0-9\\-/\\s]+)");
        Pattern p2 = Pattern.compile("sin\\s+([a-z0-9\\-/\\s]+)");
        for (Pattern p : List.of(p1, p2)) {
            Matcher m = p.matcher(text);
            while (m.find()) neg.add(m.group(1).trim());
        }
        return neg;
    }

    private Map<String, String> loadSynonyms() {
        Map<String, String> map = new HashMap<>();
        sinonimoRepo.findAll().forEach(s ->
                map.put(s.getPalabra().toLowerCase(), s.getCaracteristica().getNombre().toLowerCase())
        );
        return map;
    }
}
