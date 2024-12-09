package com.sotero.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sotero.model.Empleado;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, String>, JpaSpecificationExecutor<Empleado> {
    Optional<Empleado> findByDni(String dni);

}