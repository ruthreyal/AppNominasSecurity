package com.sotero.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sotero.model.Empleado;
import com.sotero.service.EmpleadoService;

@RestController
@RequestMapping("/api/empleados") // Cambiamos la ruta base para reflejar el propósito REST
public class AppController {

    @Autowired
    private EmpleadoService empleadoService;

    // Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay empleados
        }
        return ResponseEntity.ok(empleados); // 200 OK con la lista de empleados
    }

    // Buscar salario por DNI
    @GetMapping("/{dni}/salario")
    public ResponseEntity<?> obtenerSalarioPorDni(@PathVariable String dni) {
        Optional<Integer> salarioOpt = empleadoService.obtenerSalarioPorDni(dni);
        if (salarioOpt.isPresent()) {
            return ResponseEntity.ok(salarioOpt.get()); // 200 OK con el salario
        }
        return ResponseEntity.badRequest().body("No se encontró un empleado con el DNI proporcionado.");
    }

    // Filtrar empleados
    @GetMapping("/filtrar")
    public ResponseEntity<List<Empleado>> filtrarEmpleados(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) Integer categoria,
            @RequestParam(required = false) Integer anyos) {

        var spec = empleadoService.crearFiltro(nombre, dni, sexo, categoria, anyos);
        List<Empleado> empleados = empleadoService.filtrarEmpleados(spec);
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(empleados);
    }

    // Modificar empleado
    @PutMapping("/{dni}")
    public ResponseEntity<?> modificarEmpleado(
            @PathVariable String dni,
            @RequestBody Empleado empleado) {

        Empleado empleadoExistente = empleadoService.buscarEmpleadoPorDni(dni);
        if (empleadoExistente == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found si no existe
        }

        empleadoService.modificarEmpleado(
                dni,
                empleado.getNombre(),
                empleado.getSexo(),
                empleado.getCategoria(),
                empleado.getAnyos());
        return ResponseEntity.ok("Empleado modificado con éxito.");
    }

    // Manejo de errores genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarErrores(Exception ex) {
        return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    }
 // Crear un nuevo empleado
    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado) {
        try {
            // Validar el empleado antes de crear
            if (empleado.getSexo() == null || (!empleado.getSexo().equalsIgnoreCase("M") && !empleado.getSexo().equalsIgnoreCase("F"))) {
                return ResponseEntity.badRequest().body("El sexo debe ser 'M' o 'F'");
            }

            Empleado empleadoCreado = empleadoService.crearEmpleado(empleado); // Llamada al servicio para guardar el empleado
            return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCreado); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el empleado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable String dni) {
        try {
            // Buscamos el empleado
            Empleado empleadoExistente = empleadoService.buscarEmpleadoPorDni(dni);
            
            // Si existe, lo eliminamos
            empleadoService.eliminarEmpleado(empleadoExistente);
            
            // Devolvemos respuesta OK (200) si la eliminación fue exitosa
            return ResponseEntity.ok().build();
            
        } catch (IllegalArgumentException e) {
            // Si no se encuentra el empleado, devolvemos 404
            return ResponseEntity.notFound().build();
        }
    }

}

