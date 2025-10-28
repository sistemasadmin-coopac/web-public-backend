package com.elsalvador.coopac.repository.join;

import com.elsalvador.coopac.entity.join.JoinSpecialBenefitRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JoinSpecialBenefitRequirementRepository extends JpaRepository<JoinSpecialBenefitRequirement, UUID> {
    List<JoinSpecialBenefitRequirement> findByJoinSpecialBenefitIdOrderByRequirementOrderAsc(UUID joinSpecialBenefitId);
}

