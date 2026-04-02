package co.edu.uniquindio.red_academica.modelo.enums;

public enum NivelParticipacion {
    NOVATO(0, 49),
    INTERMEDIO(50, 149),
    AVANZADO(150, 299),
    EXPERTO(300, Integer.MAX_VALUE);

    private final int puntosMinimos;
    private final int puntosMaximos;

    NivelParticipacion(int puntosMinimos, int puntosMaximos) {
        this.puntosMinimos = puntosMinimos;
        this.puntosMaximos = puntosMaximos;
    }

    public static NivelParticipacion determinarNivel(int puntos) {
        if (puntos >= EXPERTO.puntosMinimos) return EXPERTO;
        if (puntos >= AVANZADO.puntosMinimos) return AVANZADO;
        if (puntos >= INTERMEDIO.puntosMinimos) return INTERMEDIO;
        return NOVATO;
    }

    public int getPuntosMinimos() { return puntosMinimos; }
    public int getPuntosMaximos() { return puntosMaximos; }
}
