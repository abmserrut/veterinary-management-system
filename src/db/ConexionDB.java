package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * CLASE ConexionDB — Patron Singleton. Garantiza que solo exista UNA conexion a
 * MySQL en toda la app.
 *
 * Singleton: clase que tiene maximo UNA instancia en toda la ejecucion. El
 * constructor es private, y se accede por getInstance().
 */
public class ConexionDB {

// ─── CONFIGURACION ───────────────────────────────────────────────────
    private static final String URL = "jdbc:mysql://localhost:3307/veterinaria_db";
    private static final String USUARIO = "TU_USUARIO_AQUI";
    private static final String PASSWORD = "TU_CONTRASENA_AQUI"; // CAMBIAR

// Opciones de conexion:
// useSSL=false      -> Desactiva SSL (para desarrollo local)
// allowPublicKeyRetrieval=true -> Necesario en MySQL 8+
// serverTimezone=UTC -> Zona horaria estandar
    private static final String URL_COMPLETA
            = URL + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

// ─── INSTANCIA UNICA (Singleton) ─────────────────────────────────────
// 'static' = pertenece a la CLASE, no a un objeto
// 'volatile' = importante para multiples hilos (thread-safety)
    private static volatile Connection conexion = null;

// ─── CONSTRUCTOR PRIVADO ──────────────────────────────────────────────
// Nadie puede hacer 'new ConexionDB()' desde afuera
    private ConexionDB() {
    }

// ─── METODO ESTATICO: punto de acceso unico ───────────────────────────
// Uso: Connection conn = ConexionDB.getConexion();
    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            synchronized (ConexionDB.class) {
                if (conexion == null || conexion.isClosed()) {
                    try {
                        // Cargar el driver de MySQL en memoria
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        // Crear la conexion con los datos de configuracion
                        conexion = DriverManager.getConnection(
                                URL_COMPLETA, USUARIO, PASSWORD
                        );
                        System.out.println("[DB] Conexion establecida con MySQL");
                    } catch (ClassNotFoundException e) {
                        throw new SQLException("Driver MySQL no encontrado. "
                                + "Agrega mysql-connector-j.jar a la carpeta lib/");
                    }
                }
            }
        }
        return conexion;
    }

// Cerrar la conexion al terminar el programa
    public static void cerrar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("[DB] Conexion cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("[DB] Error al cerrar: " + e.getMessage());
            }
        }
    }
}
