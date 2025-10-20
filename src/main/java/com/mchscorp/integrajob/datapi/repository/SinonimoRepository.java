package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.Sinonimo;
import com.mchscorp.integrajob.datapi.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SinonimoRepository extends JpaRepository<Sinonimo, Integer> {
    List<Sinonimo> findByCaracteristica(Caracteristica caracteristica);
}
