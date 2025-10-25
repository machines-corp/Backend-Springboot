package com.mchscorp.integrajob.datapi.service.bne;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchscorp.integrajob.datapi.entity.*;
import com.mchscorp.integrajob.datapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class BneJobService {

    // === Dependencias inyectadas ===
    private final WebClient webClient;                  // Debe existir un @Bean WebClient (ver Paso 2)
    private final ObjectMapper mapper;                  // Usa el ObjectMapper de Spring (ya existe)
    private final OfertaJobRepository ofertaRepo;
    private final EmpresaRepository empresaRepo;
    private final UbicacionRepository ubicacionRepo;
    private final SalarioRepository salarioRepo;
    private final BNETokenService tokenService;

    // === Configuración desde application.yml ===
    @Value("${bne.page-limit:100}")
    private int pageLimit;           // por defecto 100
    @Value("${bne.total-deseado:1000}")
    private int totalDeseado;        // por defecto 1000

    // -------------------------------------------------------------------------------------

    public int importarOfertasDesdeBNE() throws Exception {
        String token = tokenService.obtenerToken();

        int totalOfertas = 0;
        int offset = 0;
        int limit = pageLimit;

        System.out.println("Iniciando importación desde la BNE...");

        while (totalOfertas < totalDeseado) {
            System.out.println("Solicitando ofertas desde offset=" + offset);

            int finalOffset = offset;
            String json = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/JobOfferingsService/v1/1.0.0/jobofferings/active")
                            .queryParam("limit", limit)
                            .queryParam("offset", finalOffset)
                            .build())
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(e -> {
                        System.err.println("Error al consultar BNE: " + e.getMessage());
                        return Mono.empty();
                    })
                    .block();

            if (json == null || json.isBlank()) {
                System.out.println("Sin respuesta válida desde la BNE (offset=" + offset + ")");
                break;
            }

            JsonNode root = mapper.readTree(json);
            JsonNode arr = root.path("searchResult");
            int total = root.path("total").asInt(0);

            System.out.println("DEBUG: Resultados recibidos en esta página: " + arr.size());
            System.out.println("DEBUG: 'Total' reportado por la API: " + total);

            if (!arr.isArray() || arr.isEmpty()) {
                System.out.println("Sin más resultados (offset=" + offset + ")");
                break;
            }

            int nuevas = procesarOfertas(arr);
            totalOfertas += nuevas;
            System.out.println("Página procesada (offset=" + offset + ", nuevas=" + nuevas + ", total acumulado=" + totalOfertas + ")");

            if (offset + limit >= total) {
                System.out.println("Límite total alcanzado (" + total + " ofertas).");
                break;
            }

            offset += limit;
            Thread.sleep(800); // respira un poquito la API
        }

        System.out.println("Importación finalizada. Total nuevas ofertas: " + totalOfertas);
        return totalOfertas;
    }

    private int procesarOfertas(JsonNode arr) {
        int nuevas = 0;

        for (JsonNode job : arr) {
            try {
                // ---------------- EMPRESA ----------------
                JsonNode empresaNode = job.path("hiringOrganization");
                String idXFuente = empresaNode.path("identifier").asText(null);
                String nombreEmp = empresaNode.path("name").asText(null);
                String descripEmp = empresaNode.path("description").asText(null);
                String direccionEmp = empresaNode.path("address").asText(null);

                Empresa empresa = empresaRepo.findByIdXFuente(idXFuente)
                        .orElseGet(() -> empresaRepo.save(
                                Empresa.builder()
                                        .idXFuente(idXFuente)
                                        .nombreEmp(nombreEmp)
                                        .descripEmp(descripEmp)
                                        .direccion(direccionEmp)
                                        .build()
                        ));

                // ---------------- UBICACIÓN ----------------
                JsonNode locNode = job.path("jobLocation");
                String fullAddr = locNode.path("address").asText("");
                String region, comuna;
                if (!fullAddr.isBlank()) {
                    String[] partes = fullAddr.split(",");
                    region = partes[0].trim();
                    if (partes.length > 1) comuna = partes[1].trim();
                    else {
                        comuna = null;
                    }
                } else {
                    comuna = null;
                    region = null;
                }

                Ubicacion ubic = ubicacionRepo.findByRegionAndComuna(region, comuna)
                        .orElseGet(() -> ubicacionRepo.save(
                                Ubicacion.builder()
                                        .region(region)
                                        .comuna(comuna)
                                        .build()
                        ));

                // ---------------- OFERTA ----------------
                String url = job.path("url").asText(null);
                if (url != null && ofertaRepo.existsByUrl(url)) {
                    continue; // duplicada por URL
                }

                String titulo = job.hasNonNull("title") ? job.get("title").asText()
                        : job.path("name").asText(null);
                String contrato = job.path("employmentType").asText(null);
                String expReq = job.path("experienceRequirements").asText(null);
                String hrsLab = job.path("workHours").asText(null);
                int vacantes = job.path("totalJobOpenings").asInt(1);

                LocalDate fechaPost = parseFecha(job.path("datePosted").asText(null));
                LocalDate validThrough = parseFecha(job.path("validThrough").asText(null));

                OfertaJob oferta = ofertaRepo.save(
                        OfertaJob.builder()
                                .empresa(empresa)
                                .ubicacion(ubic)
                                .fuente("BNE")
                                .puesto(titulo)
                                .descripcion(job.path("description").asText(null))
                                .url(url)
                                .contrato(contrato)
                                .expReq(expReq)
                                .fechaPost(fechaPost)
                                .validThrough(validThrough)
                                .hrsLaborales(hrsLab)
                                .vacantes(vacantes)
                                .build()
                );

                // ---------------- SALARIO ----------------
                JsonNode salary = job.path("baseSalary");
                if (salary != null && (salary.has("minValue") || salary.has("maxValue"))) {
                    String moneda = salary.path("currency").asText("CLP");
                    BigDecimal min = BigDecimal.valueOf(salary.path("minValue").asDouble(0));
                    BigDecimal max = BigDecimal.valueOf(salary.path("maxValue").asDouble(0));

                    if (min.signum() > 0 || max.signum() > 0) {
                        salarioRepo.save(
                                Salario.builder()
                                        .oferta(oferta)
                                        .moneda(moneda)
                                        .minimo(min)
                                        .maximo(max)
                                        .mostrarSueldo(true)
                                        .build()
                        );
                    }
                }

            nuevas++;
            } catch (Exception e) {
                // Si algo falla, registra el error y continúa con la siguiente oferta
                String url = job.path("url").asText("URL_DESCONOCIDA");
                System.err.println("Error al procesar oferta BNE " + url + ": " + e.getMessage());
            }
        }

        return nuevas;
    }

    private static LocalDate parseFecha(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            if (s.contains("T")) return LocalDate.parse(s.substring(0, 10), DateTimeFormatter.ISO_DATE);
            String norm = s.replace('/', '-');
            return LocalDate.parse(norm, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Ejecuta cada día a las 07:00 AM
    @Scheduled(cron = "0 0 7 * * *")
    public void importarDiariamente() {
        try {
            int count = importarOfertasDesdeBNE();
            System.out.println("Importación BNE diaria completada. Nuevas ofertas: " + count);
        } catch (Exception e) {
            System.err.println("Error al importar ofertas BNE: " + e.getMessage());
        }
    }
}
