package model;

import model.enums.EstadoConsulta;

/**
 * CLASE CONCRETA Consulta: extiende la clase abstracta Servicio. Al ser
 * 'concreta' (no abstracta), DEBE implementar todos los metodos abstractos de
 * Servicio: calcularCosto() y generarResumen().
 */
public class Consulta extends Servicio {

// Atributos especificos de una Consulta
    private String motivo;       // Motivo de la consulta
    private String diagnostico;  // Que encontro el veterinario
    private String tratamiento;  // Que se receta/indica
    private EstadoConsulta estado;       // Estado del ciclo de vida
    private boolean esUrgente;    // Urgencia de la atencion

// Constructor llama a super() con los datos de Servicio
    public Consulta(String motivo, Paciente paciente,
            Veterinario veterinario, boolean esUrgente) {
        super("Consulta General", paciente, veterinario, 50.0);
        this.motivo = motivo;
        this.esUrgente = esUrgente;
        this.estado = EstadoConsulta.PENDIENTE;
        if (esUrgente) {
            this.precioBase = 80.0; // Urgente es mas costoso

        }
    }

// Getters
    public String getMotivo() {
        return motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public EstadoConsulta getEstado() {
        return estado;
    }

    public boolean isEsUrgente() {
        return esUrgente;
    }

    public void setDiagnostico(String d) {
        this.diagnostico = d;
    }

    public void setTratamiento(String t) {
        this.tratamiento = t;
    }

// Cambiar estado usando la logica del enum
    public void cambiarEstado(EstadoConsulta nuevoEstado) {
        if (!estado.puedePasarA(nuevoEstado)) {
            throw new IllegalStateException(
                    "No se puede pasar de " + estado + " a " + nuevoEstado
            );
        }
        this.estado = nuevoEstado;
    }

// ─── IMPLEMENTACION de metodos abstractos de Servicio ────────────────
    @Override
    public double calcularCosto() {
        double costo = precioBase;
        if (esUrgente) {
            costo += 30.0;         // Cargo por urgencia

        }
        if (diagnostico != null) {
            costo += 20.0; // Cargo por diagnostico

        }
        return costo;
    }

    @Override
    public String generarResumen() {
        return String.format(
                "Tipo: CONSULTA %s\n"
                + "Motivo: %s\n"
                + "Estado: %s\n"
                + "Diagnostico: %s\n"
                + "Tratamiento: %s",
                esUrgente ? "(URGENTE)" : "(NORMAL)",
                motivo,
                estado.getEtiqueta(),
                diagnostico != null ? diagnostico : "Pendiente",
                tratamiento != null ? tratamiento : "Pendiente"
        );
    }

}
