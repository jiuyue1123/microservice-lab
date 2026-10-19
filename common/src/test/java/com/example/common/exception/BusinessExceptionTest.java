package com.example.common.exception;

import com.example.common.result.CommonErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessExceptionTest {

    @Test
    void shouldDeriveSemanticsFromErrorCode() {
        BusinessException exception = new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND);

        assertEquals(CommonErrorCode.RESOURCE_NOT_FOUND, exception.getErrorCode());
        assertEquals("10004", exception.getErrorCode().getCode());
        assertEquals(CommonErrorCode.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());
    }
}
