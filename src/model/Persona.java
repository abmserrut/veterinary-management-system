package model;

public class Persona {

// 1. ATRIBUTOS
    protected int id;
    protected String nombre;
    protected String apellido;
    protected String cedula;    // Numero de identidad
    protected String telefono;
    protected String email;

// 2. CONSTRUCTOR
    public Persona(String nombre, String apellido, String cedula) {
        this.nombre = validarTexto(nombre, "Nombre");
        this.apellido = validarTexto(apellido, "Apellido");
        this.cedula = validarCedula(cedula);
    }
// 3. GETTERS Y SETTERS
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

// 4. Setters con validacion
    public void setId(int id) {
        this.id = id;
    }

    public void setTelefono(String tel) {
        this.telefono = tel;
    }

    public void setEmail(String email) {
        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Email invalido: " + email);
        }
        this.email = email;
    }
// Metodo de utilidad (privado): valida que un texto no sea vacio
    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacio");
        }
        return texto.trim();
    }

    // Valida formato basico de cedula (solo numeros, longitud minima 8)
    private String validarCedula(String cedula) {
        if (cedula == null || !cedula.matches("\\d{8,12}")) {
            throw new IllegalArgumentException("Cedula invalida: " + cedula);
        }
        return cedula;
    }
// Nombre completo: combina nombre y apellido
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
// 5. toString
@Override
    public String toString() {
        return getNombreCompleto() + " (CC: " + cedula + ")";
    }

}
