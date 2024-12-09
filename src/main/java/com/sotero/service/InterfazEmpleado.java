package com.sotero.service;


//import com.awn.appnominaspring.entity.Nominas;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sotero.model.Empleado;


public interface InterfazEmpleado {
	
	void eliminarEmpleado(Empleado empleado);
	
	Empleado crearEmpleado(Empleado empleado);
	
    // Obtener todos los empleados
    List<Empleado> obtenerTodosLosEmpleados();

    // Filtrar empleados basados en una especificación
    List<Empleado> filtrarEmpleados(Specification<Empleado> specification);

    // Buscar un empleado por DNI
    Empleado buscarEmpleadoPorDni(String dni);

    // Obtener el salario de un empleado por su DNI
    Optional<Integer> obtenerSalarioPorDni(String dni);

    void modificarEmpleado(String dni, String nombre, String sexo, Integer categoria, Integer anyos);

    Specification<Empleado> crearFiltro(String nombre, String dni, String sexo, Integer categoria, Integer anyos);
}
