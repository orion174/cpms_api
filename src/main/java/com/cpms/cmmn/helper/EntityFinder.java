package com.cpms.cmmn.helper;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.cpms.cmmn.exception.CustomException;
import com.cpms.cmmn.response.ErrorCode;

@Component
public class EntityFinder {

    // 기본 예외: ENTITY_NOT_FOUND
    public <T, ID> T findByIdOrThrow(JpaRepository<T, ID> repository, ID id) {
        return findByIdOrThrow(repository, id, ErrorCode.ENTITY_NOT_FOUND);
    }

    // 예외 코드 선택 가능
    public <T, ID> T findByIdOrThrow(JpaRepository<T, ID> repository, ID id, ErrorCode errorCode) {
        return repository.findById(id).orElseThrow(() -> new CustomException(errorCode));
    }

    public <T> T findByOptionalOrThrow(Optional<T> optional, ErrorCode errorCode) {
        return optional.orElseThrow(() -> new CustomException(errorCode));
    }
}
