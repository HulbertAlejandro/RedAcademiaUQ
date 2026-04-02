package co.edu.uniquindio.red_academica.dto;

public record ResponseDTO<T>(
        boolean exito,
        String mensaje,
        T datos
) {
    public static <T> ResponseDTO<T> exito(T datos) {
        return new ResponseDTO<>(true, "Operación exitosa", datos);
    }
    
    public static <T> ResponseDTO<T> error(String mensaje) {
        return new ResponseDTO<>(false, mensaje, null);
    }
}
