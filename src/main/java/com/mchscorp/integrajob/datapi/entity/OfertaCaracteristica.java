package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "oferta_caracteristica")
public class OfertaCaracteristica {

    @EmbeddedId
    private OfertaCaracteristicaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idOferta")
    @JoinColumn(name = "id_oferta", nullable = false)
    private OfertaJob oferta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCaracteristica")
    @JoinColumn(name = "id_caracteristica", nullable = false)
    private Caracteristica caracteristica;
}
