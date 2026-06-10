package interfaces;

/**
 * INTERFAZ Reportable: contrato para objetos que generan reportes. Permite que
 * el sistema de reportes trate a cualquier objeto reportable de forma UNIFORME
 * sin conocer su tipo exacto.
 */
public interface Reportable {

    // Genera una linea de resumen (para listas)
    String generarLineaResumen();

    // Genera un reporte detallado (para un registro completo)
    String generarReporteDetallado();

    // Genera datos para exportar a CSV (para hojas de calculo)
    String generarCSV();

    // Metodo default: imprime el reporte en consola
    default void imprimirReporte() {
        System.out.println("==============================");
        System.out.println(generarReporteDetallado());
        System.out.println("==============================");
    }
}
