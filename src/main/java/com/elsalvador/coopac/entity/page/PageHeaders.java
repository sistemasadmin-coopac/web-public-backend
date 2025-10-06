package com.elsalvador.coopac.entity.page;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "page_headers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageHeaders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "page_slug", nullable = false, length = 50)
    private String pageSlug;

    @Column(name = "badge_text")
    private String badgeText;

    @Column(name = "title_main", nullable = false, length = 500)
    private String titleMain;

    @Column(name = "title_highlight", length = 500)
    private String titleHighlight;

    @Column(name = "subtitle", length = 1000)
    private String subtitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "primary_cta_text")
    private String primaryCtaText;

    @Column(name = "primary_cta_url", length = 1000)
    private String primaryCtaUrl;

    @Column(name = "secondary_cta_text")
    private String secondaryCtaText;

    @Column(name = "secondary_cta_url", length = 1000)
    private String secondaryCtaUrl;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Builder.Default
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PageHeaderCards> cards;
}
