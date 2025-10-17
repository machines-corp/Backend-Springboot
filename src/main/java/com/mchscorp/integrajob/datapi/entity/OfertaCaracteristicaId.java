package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OfertaCaracteristicaId implements Serializable {

    private Integer idOferta;
    private Integer idCaracteristica;

    public OfertaCaracteristicaId() {}

    public OfertaCaracteristicaId(Integer idOferta, Integer idCaracteristica) {
        this.idOferta = idOferta;
        this.idCaracteristica = idCaracteristica;
    }

    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public Integer getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(Integer idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfertaCaracteristicaId)) return false;
        OfertaCaracteristicaId that = (OfertaCaracteristicaId) o;
        return Objects.equals(idOferta, that.idOferta) &&
                Objects.equals(idCaracteristica, that.idCaracteristica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOferta, idCaracteristica);
    }
}
