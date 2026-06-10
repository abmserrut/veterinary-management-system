package dao;

import db.ConexionDB;
import model.Paciente;
import java.sql.*;
import java.util.*;

/**
 * PacienteDAO: implementa IDAO<Paciente>. Contiene TODO el SQL para operar con
 * la tabla 'pacientes'. El resto de la app solo llama los metodos, sin saber de
 * SQL.
 */
public class PacienteDAO implements IDAO<Paciente> {

// ─── CREATE: insertar un nuevo paciente ───────────────────────────────
    @Override
    public void guardar(Paciente p) throws SQLException {
        // PreparedStatement previene SQL Injection
        // Los '?' son placeholders que se reemplazan con valores seguros
        String sql = "INSERT INTO pacientes (nombre, especie, raza, peso_kg, edad_meses, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // try-with-resources: cierra el PreparedStatement automaticamente
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            // Establecer cada '?' con el valor correspondiente
            // El numero coincide con la posicion del '?' en el SQL
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getEspecie());
            ps.setString(3, p.getRaza());
            ps.setDouble(4, p.getPesoKg());
            ps.setInt(5, p.getEdadMeses());
            ps.setBoolean(6, p.isActivo());

            ps.executeUpdate(); // Ejecutar el INSERT

            // Obtener el ID que MySQL asigno automaticamente (AUTO_INCREMENT)
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getInt(1)); // Guardar el ID en el objeto
                }
            }
            System.out.println("[DAO] Paciente guardado con ID: " + p.getId());
        }
    }

// ─── READ: buscar por ID ──────────────────────────────────────────────
    @Override
    public Optional<Paciente> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE id = ?";

        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);

            // ResultSet es el resultado de un SELECT: una 'tabla virtual'
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Si hay al menos un resultado
                    return Optional.of(mapearResultSet(rs));
                }
            }
        }
        // Optional.empty() indica que no se encontro el paciente
        return Optional.empty();
    }

// ─── READ: buscar todos ───────────────────────────────────────────────
    @Override
    public List<Paciente> buscarTodos() throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes ORDER BY nombre";

        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            // rs.next() avanza al siguiente registro, false si no hay mas
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        }
        return lista;
    }

// ─── READ: buscar por especie (metodo extra) ──────────────────────────
    public List<Paciente> buscarPorEspecie(String especie) throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes WHERE especie = ? AND activo = true";

        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, especie.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearResultSet(rs));
                }
            }
        }
        return lista;
    }

// ─── UPDATE ──────────────────────────────────────────────────────────
    @Override
    public void actualizar(Paciente p) throws SQLException {
        String sql = "UPDATE pacientes SET nombre=?, especie=?, raza=?, "
                + "peso_kg=?, edad_meses=?, activo=? WHERE id=?";

        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getEspecie());
            ps.setString(3, p.getRaza());
            ps.setDouble(4, p.getPesoKg());
            ps.setInt(5, p.getEdadMeses());
            ps.setBoolean(6, p.isActivo());
            ps.setInt(7, p.getId());   // WHERE id = ?

            int filas = ps.executeUpdate();
            System.out.println("[DAO] Paciente actualizado. Filas afectadas: " + filas);
        }
    }

// ─── DELETE (logico: solo desactiva) ─────────────────────────────────
    @Override
    public void eliminar(int id) throws SQLException {
        // Eliminacion LOGICA: no borra el registro, solo lo desactiva
        // Esto preserva el historial medico del animal
        String sql = "UPDATE pacientes SET activo = false WHERE id = ?";

        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[DAO] Paciente ID " + id + " desactivado");
        }
    }

// ─── METODO PRIVADO: mapear ResultSet a objeto Paciente ──────────────
// Extrae los datos de una fila del ResultSet y crea un objeto Paciente
    private Paciente mapearResultSet(ResultSet rs) throws SQLException {
        Paciente p = new Paciente(
                rs.getString("nombre"),
                rs.getString("especie"),
                rs.getString("raza"),
                rs.getDouble("peso_kg"),
                rs.getInt("edad_meses")
        );
        p.setId(rs.getInt("id"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }
}
