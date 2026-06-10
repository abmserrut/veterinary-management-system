package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ IDAO: contrato comun para todos los DAOs. Usa GENERICS: <T> es un
 * tipo generico que se reemplaza por el tipo real al implementar. Ej:
 * IDAO<Paciente>.
 */
public interface IDAO<T> {

    void guardar(T entidad) throws SQLException;

    void actualizar(T entidad) throws SQLException;

    void eliminar(int id) throws SQLException;

    Optional<T> buscarPorId(int id) throws SQLException;

    List<T> buscarTodos() throws SQLException;
}
