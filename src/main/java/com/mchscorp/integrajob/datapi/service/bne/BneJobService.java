package com.mchscorp.integrajob.datapi.service.bne;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchscorp.integrajob.datapi.entity.*;
import com.mchscorp.integrajob.datapi.repository.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class BneJobService {

    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();

    private final OfertaJobRepository ofertaRepo;
    private final EmpresaRepository empresaRepo;
    private final UbicacionRepository ubicacionRepo;
    private final SalarioRepository salarioRepo;
    private final BNETokenService tokenService;

    public BneJobService(
            OfertaJobRepository ofertaRepo,
            EmpresaRepository empresaRepo,
            UbicacionRepository ubicacionRepo,
            SalarioRepository salarioRepo,
            BNETokenService tokenService) {
        this.ofertaRepo = ofertaRepo;
        this.empresaRepo = empresaRepo;
        this.ubicacionRepo = ubicacionRepo;
        this.salarioRepo = salarioRepo;
        this.tokenService = tokenService;

        this.webClient = WebClient.builder()
                .baseUrl("https://test.api.bne.cl/JobOfferingsService/v1/1.0.0")
                .build();
    }

    public int importarOfertasDesdeBNE() throws Exception {

        // 🔑 Obtener token válido desde el servicio
        String token = tokenService.obtenerToken();

        String json = webClient.get()
                .uri("/jobofferings/active?limit=20") // puedes ajustar limit
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    System.err.println("⚠️ Error al consultar BNE: " + e.getMessage());
                    return Mono.empty();
                })
                .block();

        if (json == null || json.isBlank()) {
            System.out.println("⚠️ Sin respuesta válida desde la BNE.");
            return 0;
        }

        JsonNode root = mapper.readTree(json);
        JsonNode arr = root.path("searchResult");
        if (!arr.isArray() || arr.isEmpty()) return 0;

        int nuevas = 0;

        for (JsonNode job : arr) {

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
                comuna = (partes.length > 1) ? partes[1].trim() : null;
            } else {
                comuna = null;
                region = null;
            }

            Ubicacion ubic = ubicacionRepo.findByRegionAndComuna(region, comuna)
                    .orElseGet(() -> {
                        Ubicacion ubicacion;
                        ubicacion = ubicacionRepo.save(
                                Ubicacion.builder()
                                        .region(region)
                                        .comuna(comuna)
                                        .build()
                        );
                        return ubicacion;
                    });

            // ---------------- OFERTA ----------------
            String url = job.path("url").asText(null);
            if (url != null && ofertaRepo.existsByUrl(url)) continue;

            String titulo = job.hasNonNull("title") ? job.get("title").asText() : job.path("name").asText(null);
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
            if (salary != null && salary.has("minValue")) {
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
        }

        return nuevas;
    }

    private static LocalDate parseFecha(String s) {
        if (s == null || s.isBlank()) return null;
        String onlyDate = s.contains("T") ? s.substring(0, 10) : s;
        onlyDate = onlyDate.replace('/', '-');
        return LocalDate.parse(onlyDate);
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
