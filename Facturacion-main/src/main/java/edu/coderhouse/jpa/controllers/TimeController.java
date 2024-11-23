package edu.coderhouse.jpa.controllers;
import edu.coderhouse.jpa.services.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/time")
public class TimeController {

    @Autowired
    private MainService mainService;

    @Operation(summary = "Obtener la fecha y hora actual de Argentina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al obtener la fecha")
    })
    @GetMapping("/now")
    public String getCurrentTime() {
        try {
            LocalDateTime currentTime = mainService.getCurrentArgentinaDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return currentTime.format(formatter);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener la fecha", e);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(ResponseStatusException e) {
        return e.getMessage();
    }
}

