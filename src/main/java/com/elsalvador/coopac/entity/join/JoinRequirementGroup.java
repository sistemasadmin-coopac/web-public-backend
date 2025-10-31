package com.elsalvador.coopac.entity.join;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "join_requirement_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequirementGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String groupLabel;

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "item_order")
    @CollectionTable(name = "join_requirement_items", joinColumns = @JoinColumn(name = "join_requirement_group_id"))
    @Column(name = "item_text", columnDefinition = "TEXT")
    private List<String> items;

    private Integer sectionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_section_id")
    private JoinSection joinSection;
}

