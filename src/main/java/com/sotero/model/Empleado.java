package com.sotero.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "empleados")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Empleado {

    @Id
    @Column(name = "dni", unique = true)  // Establecer 'dni' como clave primaria
    @NotBlank(message = "El DNI no puede estar vacío")
    private String dni;  // 'dni' ahora es la clave primaria

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El sexo es obligatorio")
    private String sexo;

    @Min(value = 1, message = "La categoría debe estar entre 1 y 10")
    @Max(value = 10, message = "La categoría debe estar entre 1 y 10")
    private int categoria = 1;

    @Min(value = 0, message = "Los años no pueden ser negativos")
    private int anyos = 0;

    // Constructor personalizado
    public Empleado(String dni, String nombre, String sexo, int categoria, int anyos) {
        this.dni = dni;
        this.nombre = nombre;
        this.sexo = sexo;
        this.setCategoria(categoria);
        this.setAnyos(anyos);
    }

    
    public void setCategoria(int categoria) {
        if (categoria < 1 || categoria > 10) {
            throw new IllegalArgumentException("La categoría debe estar entre 1 y 10");
        }
        this.categoria = categoria;
    }
    
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void setSexo(String sexo) {
        if (sexo == null || (!sexo.equalsIgnoreCase("M") && !sexo.equalsIgnoreCase("F"))) {
            throw new IllegalArgumentException("El sexo debe ser 'M' o 'F'");
        }
        this.sexo = sexo.toUpperCase(); // Normalizamos a mayúsculas para guardar 'M' o 'F'
    }
    
    public void setAnyos(int anyos) {
        if (anyos < 0) {
            throw new IllegalArgumentException("Los años no pueden ser negativos");
        }
        this.anyos = anyos;
    }

    public void incrAnyo() {
        this.anyos++;
    }

    public void imprime() {
        System.out.println(nombre + ", " + dni + ", " + sexo + ", " + categoria + ", " + anyos);
    }

    
}





