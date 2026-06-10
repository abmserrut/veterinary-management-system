package model;

/**
 * CLASE Recepcionista: otra subclase de Empleado (nivel 3). Demuestra que
 * pueden haber MULTIPLES subclases en el mismo nivel. Recepcionista y
 * Veterinario COMPARTEN la logica de Empleado/Persona pero tienen sus propias
 * particularidades.
 */

public class Recepcionista extends Empleado {

    private String turno;          // MANANA, TARDE, NOCHE
    private int citasAgendadas; // Citas que ha programado
    private boolean bilingue;      // Habla espanol e ingles

    public Recepcionista(String nombre, String apellido, String cedula,
            double salarioBase, String turno) {
        super(nombre, apellido, cedula, salarioBase, "Recepcion");
        this.turno = turno;
        this.citasAgendadas = 0;
        this.bilingue = false;
    }
// Getters y setters

    public String getTurno() {
        return turno;
    }

    public int getCitasAgendadas() {
        return citasAgendadas;
    }

    public boolean isBilingue() {
        return bilingue;
    }

    public void setBilingue(boolean b) {
        this.bilingue = b;
    }

    public void registrarCita() {
        citasAgendadas++;
    }

    /**
     * @Override de calcularSalarioTotal() con logica diferente. Recepcionistas
     * reciben bono por citas agendadas + bono por bilingue.
     */
    @Override
    public double calcularSalarioTotal() {
        double bonoCitas = citasAgendadas * 2.50;  // $2.50 por cita
        double bonoBilingue = bilingue ? 200.0 : 0.0; // $200 si es bilingue
        return salarioBase + bonoCitas + bonoBilingue;
    }

    @Override
    public String toString() {
        return super.toString() + " | Turno: " + turno
                + " | Citas: " + citasAgendadas
                + (bilingue ? " | BILINGUE" : "");
    }

}
