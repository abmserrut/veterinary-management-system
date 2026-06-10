package model;

import interfaces.Auditable;
import interfaces.Reportable;
import java.time.LocalDate;
import java.time.LocalDateTime;

// 'implements Auditable, Reportable' = implementa DOS interfaces
// La clase DEBE proporcionar todos los metodos de ambas interfaces
/**
 * CLASE Paciente: representa un animal que recibe atencion en la veterinaria.
 * Demuestra encapsulamiento avanzado con validaciones en setters.
 */
public class Paciente implements Auditable, Reportable {

// ─── ATRIBUTOS PRIVADOS ─────────────────────────────────────────────
// 'private' = solo esta clase puede acceder directamente a estos datos
// Nadie de afuera puede hacer: paciente.nombre = "..." directamente
    private int id;             // Identificador unico en la BD
    private String nombre;         // Nombre del animal
    private String especie;        // Perro, Gato, Conejo, etc.
    private String raza;           // Raza especifica
    private double pesoKg;         // Peso en kilogramos
    private int edadMeses;      // Edad en meses (mas preciso que anos)
    private LocalDate fechaIngreso; // Cuando el paciente se registro
    private boolean activo;        // Si sigue siendo paciente activo

// Atributos requeridos por Auditable
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String creadoPor;

// ─── CONSTRUCTOR ────────────────────────────────────────────────
// Recibe los datos obligatorios para crear un Paciente valido
// 'this.' diferencia el atributo del parametro cuando tienen el mismo nombre
    public Paciente(String nombre, String especie, String raza,
            double pesoKg, int edadMeses) {
        // Usamos los setters para aprovechar las validaciones
        setNombre(nombre);
        setEspecie(especie);
        this.raza = raza;
        setPesoKg(pesoKg);
        setEdadMeses(edadMeses);
        this.fechaIngreso = LocalDate.now(); // Fecha actual automaticamente
        this.activo = true;            // Nuevo paciente = activo
    }
// ─── GETTERS: metodos para LEER atributos privados ──────────────────
// Convención: getName() donde 'Name' es el nombre del atributo

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public int getEdadMeses() {
        return edadMeses;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isActivo() {
        return activo;
    }
// Nota: para boolean se usa 'is' en lugar de 'get': isActivo()

// ─── SETTERS con VALIDACION: metodos para MODIFICAR con control ─────
// Cada setter valida el valor antes de asignarlo
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID no puede ser negativo");
        }
        this.id = id;
    }

    public void setNombre(String nombre) {
        // Validar que el nombre no sea nulo ni vacio
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente es obligatorio");
        }
        // trim() elimina espacios al inicio y al final
        // toUpperCase() convierte a mayusculas para consistencia
        this.nombre = nombre.trim().toUpperCase();
    }

    public void setEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("La especie es obligatoria");
        }
        this.especie = especie.trim().toLowerCase();
    }

    public void setPesoKg(double pesoKg) {
        // Un animal no puede pesar 0 o menos
        if (pesoKg <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg");
        }
        // Un animal de mas de 1000 kg es probablemente un error de dato
        if (pesoKg > 1000) {
            throw new IllegalArgumentException("Peso inusual: " + pesoKg + " kg");
        }
        this.pesoKg = pesoKg;
    }

    public void setEdadMeses(int edadMeses) {
        if (edadMeses < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        // 240 meses = 20 anos, limite razonable para la mayoria de animales
        if (edadMeses > 240) {
            throw new IllegalArgumentException("Edad en meses inusual: " + edadMeses);
        }
        this.edadMeses = edadMeses;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

// ─── METODO DE NEGOCIO: calcula anos a partir de meses ───────────────
// Este metodo DEVUELVE un double (division puede dar decimal)
    public double getEdadEnAnos() {
        return (double) edadMeses / 12.0;
    }

// ─── toString: representacion en texto del objeto ────────────────────
// Se llama automaticamente cuando se usa el objeto en un String
// Ejemplo: System.out.println(paciente) llama a este metodo
    @Override
    public String toString() {
        return String.format(
                "[%d] %s (%s - %s) | %.1f kg | %.1f años | %s",
                id, nombre, especie, raza, pesoKg,
                getEdadEnAnos(),
                activo ? "ACTIVO" : "INACTIVO"
        );
    }

// ─── IMPLEMENTACION de Auditable ─────────────────────────────────────
// El compilador obliga a tener ESTOS metodos porque implementamos Auditable
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String getCreadoPor() {
        return creadoPor;
    }

// ─── IMPLEMENTACION de Reportable ────────────────────────────────────
    @Override
    public String generarLineaResumen() {
        return String.format("%-5d %-20s %-10s %-10s %5.1f kg",
                id, nombre, especie, raza, pesoKg);
    }

    @Override
    public String generarReporteDetallado() {
        return "=== FICHA DE PACIENTE ===\n"
                + "ID:        " + id + "\n"
                + "Nombre:    " + nombre + "\n"
                + "Especie:   " + especie + "\n"
                + "Raza:      " + raza + "\n"
                + "Peso:      " + pesoKg + " kg\n"
                + "Edad:      " + getEdadEnAnos() + " anos\n"
                + "Estado:    " + (activo ? "ACTIVO" : "INACTIVO") + "\n"
                + getInfoAuditoria(); // Metodo de Auditable (default)
    }

    @Override
    public String generarCSV() {
        // Formato CSV: valores separados por coma
        return id + "," + nombre + "," + especie + "," + raza + ","
                + pesoKg + "," + edadMeses + "," + activo;
    }

}
