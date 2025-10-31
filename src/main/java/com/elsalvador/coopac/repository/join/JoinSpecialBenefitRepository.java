package com.elsalvador.coopac.repository.join;

import com.elsalvador.coopac.entity.join.JoinSpecialBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JoinSpecialBenefitRepository extends JpaRepository<JoinSpecialBenefit, UUID> {
    @Query("SELECT jsb FROM JoinSpecialBenefit jsb WHERE jsb.joinSection.id = :joinSectionId ORDER BY jsb.displayOrder ASC")
    List<JoinSpecialBenefit> findByJoinSectionIdOrderByDisplayOrderAsc(@Param("joinSectionId") UUID joinSectionId);
}

