package com.elsalvador.coopac.entity.join;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "join_special_benefit_requirements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinSpecialBenefitRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_special_benefit_id", nullable = false)
    private JoinSpecialBenefit joinSpecialBenefit;

    @Column(columnDefinition = "TEXT")
    private String requirementText;

    private Integer requirementOrder;

    private Long createdAt;

    private Long updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = System.currentTimeMillis();
        }
        updatedAt = System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = System.currentTimeMillis();
    }
}

