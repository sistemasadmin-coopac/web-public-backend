package com.elsalvador.coopac.repository.join;

import com.elsalvador.coopac.entity.join.JoinRequirementGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JoinRequirementGroupRepository extends JpaRepository<JoinRequirementGroup, UUID> {
    @Query("SELECT jrg FROM JoinRequirementGroup jrg WHERE jrg.joinSection.id = :joinSectionId ORDER BY jrg.sectionOrder ASC")
    List<JoinRequirementGroup> findByJoinSectionIdOrderBySectionOrderAsc(@Param("joinSectionId") UUID joinSectionId);
}

