package com.elsalvador.coopac.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "product_actions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductActions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "action_type", nullable = false, length = 20)
    private String actionType;

    @Column(name = "action_value", nullable = false, length = 1000)
    private String actionValue;

    @Builder.Default
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @Builder.Default
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;
}
