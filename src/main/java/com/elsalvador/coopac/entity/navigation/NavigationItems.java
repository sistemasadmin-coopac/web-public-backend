package com.elsalvador.coopac.entity.navigation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "navigation_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NavigationItems {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private NavigationMenus menu;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "url", nullable = false, length = 1000)
    private String url;

    @Builder.Default
    @Column(name = "external", nullable = false)
    private Boolean external = false;

    @Builder.Default
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
