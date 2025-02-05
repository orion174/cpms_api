package com.cpms.api.support.repository;

import java.util.List;

import com.cpms.api.support.model.SupportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.common.helper.YesNo;

@Repository
public interface SupportFileRepository extends JpaRepository<SupportFile, Integer> {

    List<SupportFile> findBySupportRequest_SupportRequestIdAndFileTypeAndDelYn(
            Integer supportRequestId, String fileType, YesNo delYn);
}
