package com.elsalvador.coopac.repository.join;

import com.elsalvador.coopac.entity.join.JoinBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JoinBenefitRepository extends JpaRepository<JoinBenefit, UUID> {
    @Query("SELECT jb FROM JoinBenefit jb WHERE jb.joinSection.id = :joinSectionId ORDER BY jb.sectionOrder ASC")
    List<JoinBenefit> findByJoinSectionIdOrderBySectionOrderAsc(@Param("joinSectionId") UUID joinSectionId);
}

