package com.example.common.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResultTest {

    @Test
    void successShouldCarryData() {
        Result<String> result = Result.success("payload");

        assertEquals("00000", result.errorCode());
        assertEquals(CommonErrorCode.SUCCESS.getMessage(), result.message());
        assertEquals("payload", result.data());
    }

    @Test
    void failureShouldPreserveErrorCodeAndMessage() {
        Result<Void> result = Result.failure("20101", "库存不足");

        assertEquals("20101", result.errorCode());
        assertEquals("库存不足", result.message());
        assertNull(result.data());
    }
}
