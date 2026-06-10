
import dao.PacienteDAO;
import db.ConexionDB;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import model.*;
import model.enums.*;

/**
 * CLASE PRINCIPAL: punto de entrada del programa. Integra todos los conceptos:
 * POO + MySQL + JDBC.
 */
public class Main {

// Scanner para leer la entrada del usuario
    private static Scanner scanner;
    private static final PacienteDAO pacienteDAO = new PacienteDAO();

    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   SISTEMA DE GESTION VETERINARIA");
        System.out.println("   Java POO + MySQL");
        System.out.println("===============================================");

// Verificar conexion a MySQL al inicio
        try {
            ConexionDB.getConexion();
            System.out.println("[OK] Conectado a MySQL exitosamente\n");
        } catch (SQLException e) {
            System.err.println("[ERROR] No se pudo conectar a MySQL:");
            System.err.println(e.getMessage());
            System.err.println("Verifique que MySQL este corriendo y las credenciales sean correctas.");
            return; // Salir si no hay conexion
        }

// Menu principal
        boolean salir = false;
        try (Scanner sc = new Scanner(System.in)) {
            scanner = sc;
            while (!salir) {
                mostrarMenu();
                int opcion = leerEntero("Selecciona una opcion: ");

                switch (opcion) {
                    case 1 ->
                        agregarPaciente();
                    case 2 ->
                        listarPacientes();
                    case 3 ->
                        buscarPaciente();
                    case 4 ->
                        demostrarPolimorfismo();
                    case 5 ->
                        demostrarEnums();
                    case 0 ->
                        salir = true;
                    default ->
                        System.out.println("Opcion invalida");
                }
            }
        }

// Cerrar recursos al salir
        ConexionDB.cerrar();
        System.out.println("\nSistema cerrado. Hasta pronto!");
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Agregar nuevo paciente");
        System.out.println("2. Listar todos los pacientes");
        System.out.println("3. Buscar paciente por ID");
        System.out.println("4. Demostrar polimorfismo");
        System.out.println("5. Demostrar enums con logica");
        System.out.println("0. Salir");
    }

    private static void agregarPaciente() {
        System.out.println("\n--- NUEVO PACIENTE ---");
        try {
            String nombre = leerTexto("Nombre del animal: ");
            String especie = leerTexto("Especie (perro/gato/conejo/ave): ");
            String raza = leerTexto("Raza: ");
            double peso = leerDecimal("Peso en kg: ");
            int meses = leerEntero("Edad en meses: ");

    // Crear objeto Paciente usando el constructor (encapsulamiento)
            Paciente nuevo = new Paciente(nombre, especie, raza, peso, meses);

    // Guardar en MySQL via el DAO
            pacienteDAO.guardar(nuevo);
            System.out.println("[OK] Paciente guardado: " + nuevo);

        } catch (IllegalArgumentException e) {
            System.err.println("[Error validacion] " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[Error BD] " + e.getMessage());
        }
    }

    private static void listarPacientes() {
        System.out.println("\n--- LISTA DE PACIENTES ---");
        try {
            List<Paciente> pacientes = pacienteDAO.buscarTodos();
            if (pacientes.isEmpty()) {
                System.out.println("No hay pacientes registrados.");
                return;
            }
            System.out.printf("%-5s %-20s %-10s %-15s %8s%n",
                    "ID", "NOMBRE", "ESPECIE", "RAZA", "PESO(kg)");
            System.out.println("-".repeat(65));
            // Polimorfismo: generarLineaResumen() de la interfaz Reportable
            for (Paciente p : pacientes) {
                System.out.println(p.generarLineaResumen());
            }
            System.out.println("Total: " + pacientes.size() + " pacientes");
        } catch (SQLException e) {
            System.err.println("[Error BD] " + e.getMessage());
        }
    }

    private static void buscarPaciente() {
        int id = leerEntero("Ingresa el ID del paciente: ");
        try {
            // Optional evita NullPointerException
            Optional<Paciente> resultado = pacienteDAO.buscarPorId(id);
            resultado.ifPresentOrElse(
                    p -> System.out.println(p.generarReporteDetallado()),
                    () -> System.out.println("Paciente ID " + id + " no encontrado.")
            );
        } catch (SQLException e) {
            System.err.println("[Error BD] " + e.getMessage());
        }
    }

    private static void demostrarPolimorfismo() {
        System.out.println("\n--- DEMOSTRACION DE POLIMORFISMO ---");
        Veterinario v1 = new Veterinario("Carlos", "Martinez", "12345678", 2500.0, "Cirugia", "V001");
        Recepcionista r1 = new Recepcionista("Ana", "Gomez", "87654321", 800.0, "MANANA");

// Simular trabajo
        for (int i = 0; i < 5; i++) {
            v1.registrarConsulta();
        }
        for (int i = 0; i < 20; i++) {
            r1.registrarCita();
        }
        r1.setBilingue(true);

// Polimorfismo: lista de Empleado
        Empleado[] personal = {v1, r1};
        double totalNomina = 0;
        for (Empleado e : personal) {
            double salario = e.calcularSalarioTotal();
            totalNomina += salario;
            System.out.printf("%-30s Tipo: %-15s Salario: $%.2f%n",
                    e.getNombreCompleto(),
                    e.getClass().getSimpleName(), // Tipo real del objeto
                    salario);
        }
        System.out.println("TOTAL NOMINA: $" + String.format("%.2f", totalNomina));
    }

    private static void demostrarEnums() {
        System.out.println("\n--- DEMOSTRACION DE ENUMS CON LOGICA ---");
        for (TipoEspecie especie : TipoEspecie.values()) {
            System.out.println(especie.obtenerRecomendacion());
            System.out.println();
        }

        System.out.println("--- Transiciones de estado de consulta ---");
        EstadoConsulta estado = EstadoConsulta.PENDIENTE;
        System.out.println("Estado actual: " + estado.getEtiqueta());
        System.out.println("Puede pasar a EN_PROGRESO? " + estado.puedePasarA(EstadoConsulta.EN_PROGRESO));
        System.out.println("Puede pasar a COMPLETADA? " + estado.puedePasarA(EstadoConsulta.COMPLETADA));
    }

// ─── METODOS UTILITARIOS ─────────────────────────────────────────────
    private static String leerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print(prompt);
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }

    private static double leerDecimal(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print(prompt);
        }
        double v = scanner.nextDouble();
        scanner.nextLine();
        return v;
    }
}
