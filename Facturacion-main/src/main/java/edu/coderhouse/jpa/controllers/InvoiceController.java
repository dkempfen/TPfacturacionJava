package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.dto.ErrorResponseDto;
import edu.coderhouse.jpa.entities.Invoice;
import edu.coderhouse.jpa.exceptions.InsufficientStockException;
import edu.coderhouse.jpa.services.InvoiceService;
import edu.coderhouse.jpa.services.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/invoices")
public class InvoiceController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private MainService mainService;

    private ResponseEntity<?> createErrorResponse(HttpStatus status, String message, String field) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                String.valueOf(status.value()), status.getReasonPhrase(), message, field
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        if (invoice.getClient() == null) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "El cliente es obligatorio", "client");
        }

        if (invoice.getDetails() == null || invoice.getDetails().isEmpty()) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "Los detalles de la factura son obligatorios", "details");
        }

        for (var detail : invoice.getDetails()) {
            if (detail.getProduct() == null) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "Cada detalle debe tener un producto asociado", "product");
            }
            if (detail.getAmount() <= 0) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "La cantidad del producto debe ser mayor a 0", "amount");
            }
            detail.setInvoice(invoice);
        }

        try {
            LocalDateTime currentDateTime = mainService.getCurrentArgentinaDateTime();
            invoice.setCreatedAt(currentDateTime);
            log.info("Fecha de creación de la factura: {}", currentDateTime);

            Invoice createdInvoice = invoiceService.createInvoice(invoice);
            double totalAmount = invoiceService.calculateTotal(createdInvoice);
            int totalProducts = invoiceService.calculateTotalProducts(createdInvoice);

            Map<String, Object> response = new HashMap<>();
            response.put("invoice", createdInvoice);
            response.put("totalProducts", totalProducts);
            response.put("totalAmount", totalAmount);

            log.info("Factura creada exitosamente con ID: {}", createdInvoice.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (InsufficientStockException e) {
            log.error("Stock insuficiente para uno de los productos de la factura. Detalle: {}", e.getMessage());
            return createErrorResponse(HttpStatus.CONFLICT, e.getMessage(), "stock");

        }catch (ResponseStatusException e) {
            log.error("Error en validaciones de la factura: {}", e.getReason());
            return createErrorResponse(HttpStatus.valueOf(e.getStatusCode().value()), e.getReason(), "validation_error");
        }catch (Exception e) {
            log.error("Error inesperado al crear la factura", e);
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado del servidor. Inténtalo más tarde.", "internal_error");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        if (invoices.isEmpty()) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "No se encontraron todas las facturas", "invoices");
        }
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable String id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "Factura no encontrada", "id");
        }
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable String id, @RequestBody Invoice invoiceDetails) {
        Invoice updatedInvoice = invoiceService.updateInvoice(id, invoiceDetails);
        if (updatedInvoice == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "Factura no encontrada", "id");
        }
        double totalAmount = invoiceService.calculateTotal(updatedInvoice);
        int totalProducts = invoiceService.calculateTotalProducts(updatedInvoice);

        Map<String, Object> response = new HashMap<>();
        response.put("invoice", updatedInvoice);
        response.put("totalProducts", totalProducts);
        response.put("totalAmount", totalAmount);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable String id) {
        boolean deleted = invoiceService.deleteInvoice(id);
        if (!deleted) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "Factura no encontrada", "id");
        }
        return ResponseEntity.noContent().build();
    }
}
