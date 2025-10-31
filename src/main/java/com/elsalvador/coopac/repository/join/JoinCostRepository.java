package com.elsalvador.coopac.repository.join;

import com.elsalvador.coopac.entity.join.JoinCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JoinCostRepository extends JpaRepository<JoinCost, UUID> {
    @Query("SELECT jc FROM JoinCost jc WHERE jc.joinSection.id = :joinSectionId ORDER BY jc.sectionOrder ASC")
    List<JoinCost> findByJoinSectionIdOrderBySectionOrderAsc(@Param("joinSectionId") UUID joinSectionId);
}

