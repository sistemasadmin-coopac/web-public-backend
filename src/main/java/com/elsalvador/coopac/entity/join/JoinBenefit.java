package com.elsalvador.coopac.entity.join;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "join_benefits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer sectionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_section_id")
    private JoinSection joinSection;
}

