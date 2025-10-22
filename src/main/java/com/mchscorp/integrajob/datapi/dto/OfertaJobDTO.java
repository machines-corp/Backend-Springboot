package com.mchscorp.integrajob.datapi.dto;

import java.time.LocalDate;

public record OfertaJobDTO(
        Integer idOferta,
        String puesto,
        String descripcion,
        String contrato,
        String expReq,
        LocalDate fechaPost,
        String nombreEmpresa,
        String direccionEmpresa
) {}
