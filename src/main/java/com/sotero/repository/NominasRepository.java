package com.sotero.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sotero.model.Nominas;

public interface NominasRepository extends JpaRepository<Nominas, String>, JpaSpecificationExecutor<Nominas> {
}

