package edu.coderhouse.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    @Schema(description = "Identificación única del producto", accessMode = Schema.AccessMode.READ_ONLY, example = "99887766-81b7-4924-952e-8d3fe108ab8f")
    private String id;

    @Column(name = "DESCRIPTION", nullable = false)
    @Schema(description = "Descripción del producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "Monitor")
    private String description;

    @Column(name = "CODIGO", nullable = false)
    @Schema(description = "Código del producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "78534")
    private String codigo;

    @Column(name = "STOCK", nullable = false)
    @Schema(description = "Stock disponible para este producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
    private int stock;

    @Column(name = "PRICE", nullable = false)
    @Schema(description = "Precio del producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "950")
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("product")
    @Schema(description = "Lista de detalles de facturas asociadas a este producto", example = "null")
    private List<InvoiceDetail> details;

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad a incrementar debe ser mayor a cero");
        }
        this.stock += quantity;
    }
    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad a disminuir debe ser mayor que cero");
        }
        if (this.stock - quantity < 0) {
            throw new IllegalArgumentException("Stock insuficiente para disminuir");
        }
        this.stock -= quantity;
    }
    public boolean isInStock(int quantity) {
        return this.stock >= quantity;
    }
    public void updatePrice(double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        this.price = newPrice;
    }
    public boolean hasDetails() {
        return details != null && !details.isEmpty();
    }
}
