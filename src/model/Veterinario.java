package model;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE Veterinario: tercer nivel de herencia. HEREDA de Empleado (que a su vez
 * hereda de Persona). Tiene TODO de Empleado + datos específicos de un
 * veterinario.
 */
public class Veterinario extends Empleado {

// Atributos especificos del Veterinario
    private final String numeroLicencia;     // Numero de licencia medica
    private final String especialidad;       // Cirujia, Dermatologia, etc.
    private int consultasAtendidas; // Contador de consultas
    private final List<String> especiesAtiende; // Que especies puede atender

// Constructor de Veterinario
    public Veterinario(String nombre, String apellido, String cedula,
            double salarioBase, String especialidad,
            String numeroLicencia) {
        // 'super' llama al constructor de Empleado
        // Empleado a su vez llama a Persona con su super()
        // Cadena: Veterinario -> Empleado -> Persona
        super(nombre, apellido, cedula, salarioBase, "Clinica Veterinaria");

        this.especialidad = especialidad;
        this.numeroLicencia = numeroLicencia;
        this.consultasAtendidas = 0;
        this.especiesAtiende = new ArrayList<>();
    }

// Getters
    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public int getConsultasAtendidas() {
        return consultasAtendidas;
    }

    public List<String> getEspeciesAtiende() {
        return especiesAtiende;
    }

// Agregar una especie que puede atender
    public void agregarEspecieAtiende(String especie) {
        if (!especiesAtiende.contains(especie)) {
            especiesAtiende.add(especie);
        }
    }

// Verificar si puede atender una especie
    public boolean puedeAtender(String especie) {
        return especiesAtiende.isEmpty()
                || // Vacio = atiende todas
                especiesAtiende.contains(especie.toLowerCase());
    }
// Incrementar contador de consultas

    public void registrarConsulta() {
        consultasAtendidas++;
    }

    /**
     * @Override de calcularSalarioTotal() de Empleado. Los veterinarios reciben
     * BONO por consultas atendidas. POLIMORFISMO: este metodo se comporta
     * diferente para Veterinario que para Empleado, aunque la llamada es
     * identica.
     */
    @Override
    public double calcularSalarioTotal() {
        double bonoPorConsultas = consultasAtendidas * 15.0; // $15 por consulta
        return salarioBase + bonoPorConsultas;
        // 'salarioBase' es visible porque es 'protected' en Empleado
    }

    @Override
    public String toString() {
        // super.toString() llama a Empleado.toString()
        // que a su vez llama a Persona.toString()
        // Cadena de toString(): Persona <- Empleado <- Veterinario
        return "DR. " + super.toString()
                + " | Lic: " + numeroLicencia
                + " | Esp: " + especialidad
                + " | Consultas: " + consultasAtendidas;
    }

}
