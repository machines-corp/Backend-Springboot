package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.Salario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Integer> {

	List<Salario> findByOfertaIdOferta(Integer idOferta);
    //List<Salario> findByOfertaIdOferta(Integer idOferta);
}
