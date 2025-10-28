package com.elsalvador.coopac.entity.join;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "join_special_benefits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinSpecialBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_section_id", nullable = false)
    private JoinSection joinSection;

    private String titleMain;

    private String subtitle;

    private String fundTitle;

    @Column(columnDefinition = "TEXT")
    private String fundDescription;

    private String benefitMaximum;

    private String maximumNote;

    private String annualAmount;

    private String annualNote;

    @OneToMany(mappedBy = "joinSpecialBenefit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("requirementOrder ASC")
    private List<JoinSpecialBenefitRequirement> requirements;

    private Integer displayOrder;

    @Builder.Default
    private Boolean active = true;

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

