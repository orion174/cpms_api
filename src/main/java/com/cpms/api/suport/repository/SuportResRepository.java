package com.cpms.api.suport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.suport.model.SuportRes;
import com.cpms.common.helper.YesNo;

@Repository
public interface SuportResRepository extends JpaRepository<SuportRes, Integer> {

    Optional<SuportRes> findBySuportReq_SuportReqIdAndDelYn(Integer suportReqId, YesNo delYn);
}
