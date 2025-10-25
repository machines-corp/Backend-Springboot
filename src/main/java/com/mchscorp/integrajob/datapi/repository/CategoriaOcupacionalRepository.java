package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.CategoriaOcupacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaOcupacionalRepository extends JpaRepository<CategoriaOcupacional, Integer> {
    // List<CategoriaOcupacional> findByNombreContaining(String nombre);
}
