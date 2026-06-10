package model.enums;

/**
 * ENUM EstadoConsulta: ciclo de vida de una consulta medica. Cada estado sabe
 * si permite transicion a otro estado. Esto previene transiciones invalidas
 * (ej: CANCELADA -> COMPLETADA).
 */
public enum EstadoConsulta {

    PENDIENTE("Pendiente", true, "#FFA500"), // Naranja
    EN_PROGRESO("En Progreso", true, "#2196F3"), // Azul
    COMPLETADA("Completada", false, "#4CAF50"), // Verde
    CANCELADA("Cancelada", false, "#F44336");  // Rojo

    private final String etiqueta;
    private final boolean permiteModificacion; // Se puede cambiar aun?
    private final String colorHex;

    EstadoConsulta(String etiqueta, boolean permiteModificacion, String colorHex) {
        this.etiqueta = etiqueta;
        this.permiteModificacion = permiteModificacion;
        this.colorHex = colorHex;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

// Getters y Setters
    public boolean isPermiteModificacion() {
        return permiteModificacion;
    }

    public String getColorHex() {
        return colorHex;
    }

    /**
     * LOGICA EN ENUM: valida si es posible la transicion entre dos estados.
     * Previene flujos invalidos de negocio.
     */
    public boolean puedePasarA(EstadoConsulta nuevoEstado) {
        switch (this) {
            case PENDIENTE:
                // Desde PENDIENTE se puede ir a EN_PROGRESO o CANCELADA
                return nuevoEstado == EN_PROGRESO || nuevoEstado == CANCELADA;
            case EN_PROGRESO:
                // Desde EN_PROGRESO solo se puede COMPLETAR o CANCELAR
                return nuevoEstado == COMPLETADA || nuevoEstado == CANCELADA;
            case COMPLETADA:
            case CANCELADA:
                // Estados finales: no se puede cambiar
                return false;
            default:
                return false;
        }
    }

}
