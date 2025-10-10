package com.elsalvador.coopac.entity.about;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "about_mission_vision")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutMissionVision {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Builder.Default
    @Column(name = "mission_title", columnDefinition = "varchar(255) default 'Nuestra Misión'")
    private String missionTitle = "Nuestra Misión";

    @Column(name = "mission_text", nullable = false, columnDefinition = "TEXT")
    private String missionText;

    @Column(name = "mission_icon", length = 50)
    private String missionIcon;

    @Builder.Default
    @Column(name = "vision_title", columnDefinition = "varchar(255) default 'Nuestra Visión'")
    private String visionTitle = "Nuestra Visión";

    @Column(name = "vision_text", nullable = false, columnDefinition = "TEXT")
    private String visionText;

    @Column(name = "vision_icon", length = 50)
    private String visionIcon;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
