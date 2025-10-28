package com.elsalvador.coopac.entity.join;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "join_costs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinCost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String label;

    private String amount;

    private Integer sectionOrder;
}

