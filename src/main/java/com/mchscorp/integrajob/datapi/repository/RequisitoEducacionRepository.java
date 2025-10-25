package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.RequisitoEducacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisitoEducacionRepository extends JpaRepository<RequisitoEducacion, Integer> {
    // List<RequisitoEducacion> findByNivel(String nivel);
}
