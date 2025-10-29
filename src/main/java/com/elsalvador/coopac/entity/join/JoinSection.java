package com.elsalvador.coopac.entity.join;

import com.elsalvador.coopac.entity.page.PageHeaders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "join_sections")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinSection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "page_header_id")
    private PageHeaders pageHeader;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "join_section_id")
    @OrderBy("sectionOrder ASC")
    private List<JoinBenefit> benefits;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "join_section_id")
    @OrderBy("sectionOrder ASC")
    private List<JoinCost> costs;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "join_section_id")
    @OrderBy("sectionOrder ASC")
    private List<JoinRequirementGroup> requirementGroups;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "join_section_id")
    @OrderBy("displayOrder ASC")
    private List<JoinSpecialBenefit> specialBenefits;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

