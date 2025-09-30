package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.Taxonomia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxonomiaRepository extends JpaRepository<Taxonomia, Integer> {
    // Búsqueda por categoría
    List<Taxonomia> findByCategoria(String categoria);

    // Búsqueda por valor
    List<Taxonomia> findByValorContaining(String valor);
}
