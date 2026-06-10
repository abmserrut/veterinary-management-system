package model;

import java.time.LocalDateTime;

/**
 * CLASE ABSTRACTA Servicio: molde para todos los servicios de la veterinaria.
 *
 * 'abstract class' = no se puede hacer new Servicio() directamente. Los
 * servicios concretos (Consulta, Cirugia, Vacunacion) la heredan.
 *
 * Metodos abstractos: declarados aqui pero IMPLEMENTADOS en subclases. La
 * subclase OBLIGATORIAMENTE debe sobreescribir los metodos abstractos.
 */
public abstract class Servicio {

    // Atributos comunes a TODOS los servicios
    protected int id;
    protected String descripcion;
    protected LocalDateTime fechaHora;
    protected Paciente paciente;    // A quien se le da el servicio
    protected Veterinario veterinario; // Quien da el servicio
    protected double precioBase;

// Constructor de la clase abstracta
// Las subclases lo llaman con super()
    public Servicio(String descripcion, Paciente paciente,
            Veterinario veterinario, double precioBase) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente requerido");
        }
        if (veterinario == null) {
            throw new IllegalArgumentException("Veterinario requerido");
        }
        if (precioBase < 0) {
            throw new IllegalArgumentException("Precio no puede ser negativo");
        }
        this.descripcion = descripcion;
        this.paciente = paciente;
        this.veterinario = veterinario;
        this.precioBase = precioBase;
        this.fechaHora = LocalDateTime.now();
    }
// Getters comunes

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setId(int id) {
        this.id = id;
    }
// ─── METODOS ABSTRACTOS ──────────────────────────────────────────────
    // NO tienen cuerpo ({}) — solo la firma.
    // Cada subclase DEBE implementarlos, o el compilador da error.

    /**
     * Calcula el costo total del servicio. Cada tipo de servicio tiene su
     * propia formula de calculo.
     */
    public abstract double calcularCosto();

    /**
     * Genera un resumen del servicio para mostrar o imprimir.
     */
    public abstract String generarResumen();

// ─── METODO CONCRETO: compartido por todas las subclases ─────────────
// Este SI tiene implementacion — las subclases lo heredan tal cual
    public String obtenerEncabezado() {
        return String.format(
                "=== SERVICIO VETERINARIO ===\n"
                + "Paciente:   %s\n"
                + "Veterinario: Dr. %s\n"
                + "Fecha/Hora:  %s\n",
                paciente.getNombre(),
                veterinario.getNombreCompleto(),
                fechaHora.toString()
        );
    }

// toString llama a los metodos abstractos
// Esto funciona porque en tiempo de ejecucion Java usa
// la implementacion concreta del objeto real (polimorfismo)
    @Override
    public String toString() {
        return obtenerEncabezado() + generarResumen()
                + "\nCOSTO TOTAL: $" + String.format("%.2f", calcularCosto());
    }

}
