package com.filmtrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;
/**
  Clase base que representa a una persona dentro del sistema.
  Se utiliza como superclase para otras entidades (por ejemplo, Usuario o Actor).
  Conceptos de POO presentes:
  - Abstracción: agrupa los datos y comportamientos comunes a todas las personas del sistema.
  - Herencia: permite que las subclases hereden estos atributos y agreguen los suyos propios.
  - Encapsulamiento: protege los atributos mediante el uso de modificadores de acceso y métodos getters/setters.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /** Campo de control de versiones para manejar actualizaciones concurrentes. */
    @Version
    private int version;
    /** Indica si la persona está activa dentro del sistema. */
    private boolean activo;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    /** Constructor vacío requerido por JPA. */
    public Persona() {
        this.activo = true; // activo por defecto
    }

    public Persona(String nombre, String apellido, LocalDate fechaNacimiento) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }
    // Getters y Setters
    public int getId() { return id; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}