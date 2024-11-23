package edu.coderhouse.jpa.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    @Schema(description = "Unique ID of the client", accessMode = Schema.AccessMode.READ_ONLY, example = "0124529f-81b7-4924-952e-8d3fe108ab8f")
    private String id;

    @Column(name = "NAME", nullable = false)
    @Schema(description = "First name of the client", requiredMode = Schema.RequiredMode.REQUIRED, example = "Dante")
    private String name;

    @Column(name = "LASTNAME", nullable = false)
    @Schema(description = "Last name of the client", requiredMode = Schema.RequiredMode.REQUIRED, example = "Kempfen")
    private String lastName;

    @Column(name = "DOCNUMBER", nullable = false)
    @Schema(description = "Document number of the client", requiredMode = Schema.RequiredMode.REQUIRED, example = "7536987")
    private String docNumber;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("client")
    @Schema(description = "List of invoices associated with the client")
    private List<Invoice> invoices = new ArrayList<>();

    public Client() {}

    public Client(String name, String lastName, String docNumber) {
        this.name = name;
        this.lastName = lastName;
        this.docNumber = docNumber;
    }

    public void addInvoice(Invoice invoice) {
        if (invoice != null) {
            if (invoices == null) {
                invoices = new ArrayList<>();
            }
            invoices.add(invoice);
            invoice.setClient(this);
        }
    }
}
