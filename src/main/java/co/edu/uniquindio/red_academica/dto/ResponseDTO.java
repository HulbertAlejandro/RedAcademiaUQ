package co.edu.uniquindio.red_academica.dto;

public record ResponseDTO<T>(
        String mensaje,
        T datos
) {
    public static <T> ResponseDTO<T> exito(T datos) {
        return new ResponseDTO<>("Operación exitosa", datos);
    }
    
    public static <T> ResponseDTO<T> error(String mensaje) {
        return new ResponseDTO<>(mensaje, null);
    }
}
