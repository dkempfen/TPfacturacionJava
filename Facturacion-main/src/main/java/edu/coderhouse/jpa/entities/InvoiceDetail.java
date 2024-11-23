package edu.coderhouse.jpa.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "INVOICE_DETAILS")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    @Schema(description = "Identificación única del detalle de la factura", requiredMode = Schema.RequiredMode.AUTO, example = "11223344-81b7-4924-952e-8d3fe108ab8f")
    private String id;

    @Column(name = "AMOUNT", nullable = false)
    @Schema(description = "Cantidad de productos en este detalle", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private int amount;

    @Column(name = "PRICE", nullable = false)
    @Schema(description = "Precio del producto en el momento de la factura", requiredMode = Schema.RequiredMode.REQUIRED, example = "900.05")
    private double price;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonIgnoreProperties("details")
    @Schema(description = "Factura asociada a este detalle", requiredMode = Schema.RequiredMode.REQUIRED)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties("details")
    @Schema(description = "Producto asociado a este detalle", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "{ \"id\": \"234e4567-e89b-12d3-a456-426614174111\", \"description\": \"resma A5\", \"price\": 750.25 }")
    private Product product;

    @Transient
    @Schema(description = "Importe total para este detalle", accessMode = Schema.AccessMode.READ_ONLY)
    public double getTotal() {
        return amount * price;
    }

    public InvoiceDetail(int amount, double price, Invoice invoice, Product product) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor que cero");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        this.amount = amount;
        this.price = price;
        this.invoice = invoice;
        this.product = product;
    }

    public void updatePrice(double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("Nuevo precio debe ser mayor que cero");
        }
        this.price = newPrice;
    }
}
