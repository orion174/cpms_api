package com.cpms.api.support.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.support.model.SupportResponse;
import com.cpms.common.helper.YesNo;

@Repository
public interface SupportResponseRepository extends JpaRepository<SupportResponse, Integer> {

    Optional<SupportResponse> findBySupportRequest_SupportRequestIdAndDelYn(Integer supportRequestId, YesNo delYn);
}
