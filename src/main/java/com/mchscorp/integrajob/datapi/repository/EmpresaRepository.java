package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    // List<Empresa> findByNombreEmpContaining(String nombre);
}
