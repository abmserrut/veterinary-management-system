package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Empleado extends Persona {
// Atributos PROPIOS del Empleado (que Persona no tiene)

    protected double salarioBase;
    protected LocalDate fechaContrato;
    protected String departamento;
    protected boolean activo;

// Constructor de Empleado. Nota: DEBE llamar a super() para inicializar la parte Persona
    public Empleado(String nombre, String apellido, String cedula,
            double salarioBase, String departamento) {

        super(nombre, apellido, cedula);

        setSalarioBase(salarioBase);
        this.departamento = departamento;
        this.fechaContrato = LocalDate.now();
        this.activo = true;
    }
// Getters propios de Empleado

    public double getSalarioBase() {
        return salarioBase;
    }

    public LocalDate getFechaContrato() {
        return fechaContrato;
    }

    public String getDepartamento() {
        return departamento;
    }

    public boolean isActivo() {
        return activo;
    }
// Setter con validacion

    public void setSalarioBase(double salario) {
        if (salario < 0) {
            throw new IllegalArgumentException("Salario no puede ser negativo");
        }
        this.salarioBase = salario;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
// Calcula años de antiguedad usando ChronoUnit de Java

    public long getAnosAntiguedad() {
        return ChronoUnit.YEARS.between(fechaContrato, LocalDate.now());
    }

    /**
     * METODO VIRTUAL: calcular salario total. Cada subclase puede SOBREESCRIBIR
     * este metodo para calcular el salario de manera diferente. En Empleado
     * base: salario total = salario base
     */
    public double calcularSalarioTotal() {
        return salarioBase;
    }

    /**
     * 'super.toString()' llama al toString() de Persona y le agrega informacion
     * de Empleado. Reutiliza codigo sin copiar y pegar.
     */
    @Override
    public String toString() {
        return super.toString() + // Llama a Persona.toString()
                " | " + departamento
                + " | Salario: $" + String.format("%.2f", salarioBase);
    }
}
