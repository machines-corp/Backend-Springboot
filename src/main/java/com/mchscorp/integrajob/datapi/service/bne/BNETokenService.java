package com.mchscorp.integrajob.datapi.service.bne;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class BNETokenService {

    @Value("${bne.api.token-url}")
    private String tokenUrl;

    @Value("${bne.api.client-id}")
    private String clientId;

    @Value("${bne.api.client-secret}")
    private String clientSecret;

    private final ObjectMapper mapper = new ObjectMapper();
    private final File tokenFile = new File("src/main/resources/tokens/bne_token.json");

    public String obtenerToken() {
        try {
            if (tokenFile.exists()) {
                JsonNode json = mapper.readTree(tokenFile);
                if (json.hasNonNull("access_token") && json.hasNonNull("expires_at")) {
                    Instant expira = Instant.parse(json.get("expires_at").asText());
                    if (Instant.now().isBefore(expira)) {
                        return json.get("access_token").asText();
                    }
                }
            }
            return renovarToken();
        } catch (IOException e) {
            throw new RuntimeException("💥 Error al leer el archivo del token BNE", e);
        }
    }

    /**
     * Solicita un nuevo token a la API y lo guarda localmente.
     */
    private String renovarToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, Map.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("No se pudo obtener token desde la BNE. Status: " + response.getStatusCode());
        }

        Map<String, Object> json = response.getBody();
        String token = (String) json.get("access_token");
        int expiresIn = ((Number) json.getOrDefault("expires_in", 3600)).intValue();

        Map<String, Object> actualizado = new HashMap<>();
        actualizado.put("access_token", token);
        actualizado.put("expires_at", Instant.now().plus(expiresIn - 60, ChronoUnit.SECONDS).toString());

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(tokenFile, actualizado);
        } catch (IOException e) {
            System.err.println("No se pudo guardar el token en archivo: " + e.getMessage());
        }

        System.out.println("Token BNE renovado y guardado con éxito.");
        return token;
    }
}
