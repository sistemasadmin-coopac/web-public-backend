package com.elsalvador.coopac.entity.home;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "home_promotion_features")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomePromotionFeatures {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private HomePromotions promotion;

    @Column(name = "feature_text", nullable = false, length = 500)
    private String featureText;

    @Builder.Default
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;
}
