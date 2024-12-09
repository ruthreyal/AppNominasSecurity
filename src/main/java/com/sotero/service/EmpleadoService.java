package com.sotero.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sotero.model.Empleado;
import com.sotero.model.Nominas;
import com.sotero.repository.EmpleadoRepository;
import com.sotero.repository.NominasRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class EmpleadoService implements InterfazEmpleado{
    private final EmpleadoRepository empleadoRepository;
    private final NominasRepository nominasRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository, NominasRepository nominasRepository) {
        this.empleadoRepository = empleadoRepository;
        this.nominasRepository = nominasRepository;
    }
    
 // Crear un nuevo empleado
    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado); // Usamos el repositorio para guardar al empleado en la base de datos
    }
    
    
    @Override
    public List<Empleado> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = empleadoRepository.findAll();
        System.out.println("Empleados desde la base de datos: " + empleados);
        if (empleados.isEmpty()) {
            throw new IllegalStateException("No hay empleados registrados.");
        }
        return empleados;
    }
    
   @Override
    public void eliminarEmpleado(Empleado empleado) {
        empleadoRepository.delete(empleado); 
    }



    @Override
    public List<Empleado> filtrarEmpleados(Specification<Empleado> specification) {
        return empleadoRepository.findAll(specification);
    }

    @Override
    public Empleado buscarEmpleadoPorDni(String dni) {
        return empleadoRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un empleado con el DNI " + dni));
    }


    @Override
    public Optional<Integer> obtenerSalarioPorDni(String dni) {
        System.out.println("DEBUG: Buscando salario para DNI: " + dni);
        Optional<Nominas> nominaOpt = nominasRepository.findById(dni);
        if (nominaOpt.isPresent()) {
            System.out.println("DEBUG: Salario encontrado: " + nominaOpt.get().getSueldo());
            return Optional.of(nominaOpt.get().getSueldo());
        } else {
            System.out.println("DEBUG: No se encontró una nómina para el DNI: " + dni);
            return Optional.empty();
        }
    }


    @Override
    public Specification<Empleado> crearFiltro(String nombre, String dni, String sexo, Integer categoria, Integer anyos) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null && !nombre.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("nombre"), "%" + nombre + "%"));
            }
            if (dni != null && !dni.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("dni"), "%" + dni + "%"));
            }
            if (sexo != null && !sexo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("sexo"), sexo));
            }
            if (categoria != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoria"), categoria));
            }
            if (anyos != null) {
                predicates.add(criteriaBuilder.equal(root.get("anyos"), anyos));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public void modificarEmpleado(String dni, String nombre, String sexo, Integer categoria, Integer anyos) {
        Empleado empleado = empleadoRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un empleado con el DNI " + dni));

        empleado.setNombre(nombre);
        empleado.setSexo(sexo);
        empleado.setCategoria(categoria);
        empleado.setAnyos(anyos);

        empleadoRepository.save(empleado);
    }
	
}
