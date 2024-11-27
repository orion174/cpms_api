package com.cpms.api.suport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.suport.model.SuportFile;
import com.cpms.common.helper.YesNo;

@Repository
public interface SuportFileRepository extends JpaRepository<SuportFile, Integer> {

    List<SuportFile> findBySuportReq_SuportReqIdAndFileTypeAndDelYn(
            Integer suportReqId, String fileType, YesNo delYn);
}
