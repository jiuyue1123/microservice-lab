package com.example.common.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageResultTest {

    @Test
    void nullRecordsShouldBecomeEmptyList() {
        PageResult<String> result = PageResult.of(1, 10, 0, null);

        assertTrue(result.records().isEmpty());
    }

    @Test
    void emptyShouldReturnZeroTotal() {
        PageResult<String> result = PageResult.empty(1, 10);

        assertEquals(0L, result.total());
        assertTrue(result.records().isEmpty());
    }
}
