package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.Agrupacion;
import com.mchscorp.integrajob.datapi.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Integer> {
    List<Caracteristica> findByAgrupacion(Agrupacion agrupacion);
}
