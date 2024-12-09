package com.sotero.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nominas")
@Getter
@Setter
public class Nominas {

    @Id
    @NotNull
    @Column(name = "dni", insertable = false, updatable = false)
    private String dni;

    @NotNull
    private Integer sueldo;

    @OneToOne
    @JoinColumn(name = "dni", referencedColumnName = "dni")
    private Empleado empleado;

    public Nominas() {
    }

    public Nominas(String dni, Integer sueldo) {
        this.dni = dni;
        this.sueldo = sueldo;
    }
    
  

}