package edu.coderhouse.jpa.dto;

public class ErrorResponseDto {
    private String code; // Código de error (e.g., 400, 404, 500)
    private String status; // Nombre del estado HTTP (e.g., "Bad Request", "Not Found")
    private String message; // Mensaje detallado del error
    private String field; // Campo afectado (opcional, puede ser nulo)


    public ErrorResponseDto(String code, String status, String message, String field) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.field = field;
    }

    // Getters y setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
