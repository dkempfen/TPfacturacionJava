package edu.coderhouse.jpa.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoice")
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    @Schema(description = "Identificación única de la factura", accessMode = Schema.AccessMode.READ_ONLY, example = "0124529f-81b7-4924-952e-8d3fe108ab8f")
    private String id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties("invoices")
    @Schema(description = "Cliente relacionado con la factura", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "{ \"id\": \"123e4567-e89b-12d3-a456-426614174000\" }")
    private Client client;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Fecha en que se creó la factura", accessMode = Schema.AccessMode.READ_ONLY, example = "2024-11-18")
    private LocalDateTime createdAt;

    @Column(name = "total", nullable = false)
    @Schema(description = "Importe total de la factura", accessMode = Schema.AccessMode.READ_ONLY, example = "978424")
    private double total;

    @OneToMany(mappedBy = "invoice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("invoice")
    @Schema(description = "Lista de detalles asociados a la factura", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<InvoiceDetail> details = new ArrayList<>();

    public Invoice(Client client, List<InvoiceDetail> details) {
        this.client = client;
        this.details = details;
        this.createdAt = LocalDateTime.now();
        this.total = details.stream()
                .mapToDouble(InvoiceDetail::getAmount)
                .sum();
    }
}
