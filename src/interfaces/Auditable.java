package interfaces;

import java.time.LocalDateTime;

/**
 * INTERFAZ Auditable: contrato para objetos que se auditan. Cualquier clase que
 * la implemente puede ser rastreada.
 *
 * En Java moderno las interfaces pueden tener: - Metodos abstractos
 * (obligatorio implementar): void metodo(); - Metodos default (implementacion
 * por defecto): default void... - Metodos static (utilitarios de la interfaz):
 * static void...
 */
public interface Auditable {

    // Metodos abstractos: la clase implementadora DEBE escribirlos
    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    String getCreadoPor();

    /**
     * METODO DEFAULT: tiene implementacion pero las clases pueden
     * sobreescribirlo si necesitan otro comportamiento.
     */
    default String getInfoAuditoria() {
        return String.format(
                "Creado: %s por %s | Modificado: %s",
                getCreatedAt(),
                getCreadoPor(),
                getUpdatedAt() != null ? getUpdatedAt().toString() : "nunca"
        );
    }
}
