package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SinonimoRepository extends JpaRepository<Sinonimo, Integer> {
    List<Sinonimo> findByPalabraContainingIgnoreCase(String palabra);
}