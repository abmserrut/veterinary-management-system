package model.enums;

/**
 * ENUM TipoEspecie: define las especies que atiende la veterinaria. Un ENUM con
 * atributos funciona asi: 1. Se declaran los VALORES del enum (PERRO, GATO,
 * etc.) 2. Cada valor tiene entre parentesis los datos de sus atributos 3. Se
 * declaran los atributos del enum (private final) 4. El constructor del enum
 * inicializa esos atributos 5. Se agregan metodos que usan esos atributos
 */
public enum TipoEspecie {

// ─── VALORES DEL ENUM ────────────────────────────────────────────────
// Formato: NOMBRE(descripcion, vacunasAnuales, vidaPromAnos, cuidadoEspecial)
// Cada linea ES un objeto de este enum con esos valores
    PERRO("Perro domestico", 3, 12, false),
    GATO("Gato domestico", 2, 15, false),
    CONEJO("Conejo domestico", 1, 8, true),
    AVE("Ave (loro/canario)", 1, 20, true),
    REPTIL("Reptil (tortuga/etc)", 0, 30, true),
    PECES("Peces de acuario", 0, 5, true);

// ─── ATRIBUTOS DEL ENUM ──────────────────────────────────────────────
// 'final' porque los atributos de un enum NO cambian una vez creados
    private final String descripcion;
    private final int vacunasAnuales;    // Cuantas vacunas necesita por ano
    private final int vidaPromedioAnos;  // Esperanza de vida aproximada
    private final boolean cuidadoEspecial; // Requiere veterinario especializado

// ─── CONSTRUCTOR DEL ENUM ────────────────────────────────────────────
// SIEMPRE es private (Java lo hace automaticamente, no hace falta escribirlo)
// Se llama cuando se inicializa el enum (al arrancar el programa)
    TipoEspecie(String descripcion, int vacunasAnuales, int vidaPromedioAnos, boolean cuidadoEspecial) {
        this.descripcion = descripcion;
        this.vacunasAnuales = vacunasAnuales;
        this.vidaPromedioAnos = vidaPromedioAnos;
        this.cuidadoEspecial = cuidadoEspecial;
    }
// ─── GETTERS ─────────────────────────────────────────────────────────

    public String getDescripcion() {
        return descripcion;
    }

    public int getVacunasAnuales() {
        return vacunasAnuales;
    }

    public int getVidaPromedioAnos() {
        return vidaPromedioAnos;
    }

    public boolean isCuidadoEspecial() {
        return cuidadoEspecial;
    }

// ─── METODO CON LOGICA ───────────────────────────────────────────────
// Este metodo usa los atributos para calcular algo util
    public String obtenerRecomendacion() {
        StringBuilder recomendacion = new StringBuilder();
        recomendacion.append(descripcion).append(":\n");

        if (vacunasAnuales > 0) {
            recomendacion.append("  - Requiere ")
                    .append(vacunasAnuales)
                    .append(" vacuna(s) al ano\n");
        } else {
            recomendacion.append("  - No requiere vacunas anuales\n");
        }

        if (cuidadoEspecial) {
            recomendacion.append("  - ATENCION: requiere veterinario especializado\n");
        }

        recomendacion.append("  - Vida promedio: ")
                .append(vidaPromedioAnos)
                .append(" anos");
        return recomendacion.toString();
    }

// ─── METODO ESTATICO: buscar por texto ───────────────────────────────
// 'static' = pertenece al enum en si, no a un valor especifico
// Uso: TipoEspecie.fromTexto("perro") devuelve TipoEspecie.PERRO
    public static TipoEspecie fromTexto(String texto) {
        if (texto == null) {
            return null;
        }
        for (TipoEspecie t : values()) { // values() = todos los valores del enum
            if (t.name().equalsIgnoreCase(texto)
                    || t.descripcion.equalsIgnoreCase(texto)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Especie no reconocida: " + texto);
    }

}
