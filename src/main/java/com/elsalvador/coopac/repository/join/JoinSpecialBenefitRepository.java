package com.elsalvador.coopac.repository.join;

import com.elsalvador.coopac.entity.join.JoinSpecialBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JoinSpecialBenefitRepository extends JpaRepository<JoinSpecialBenefit, UUID> {
    List<JoinSpecialBenefit> findByJoinSectionIdOrderByDisplayOrderAsc(UUID joinSectionId);
}

