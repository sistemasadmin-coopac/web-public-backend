package com.elsalvador.coopac.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "product_financial_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFinancialInfo {

    @Id
    @Column(name = "product_id")
    private UUID productId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @MapsId
    private Products product;

    @Column(name = "interest_rate_text", length = 100)
    private String interestRateText;

    @Column(name = "term_text", length = 100)
    private String termText;


    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
