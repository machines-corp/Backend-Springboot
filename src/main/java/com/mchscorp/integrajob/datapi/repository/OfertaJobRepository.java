package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.OfertaJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaJobRepository extends JpaRepository<OfertaJob, Integer> {

}

