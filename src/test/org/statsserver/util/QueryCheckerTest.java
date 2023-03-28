package org.statsserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.statsserver.domain.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class QueryCheckerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void firstTest() throws IOException {
        // Test to check if it's possible to map a JSON string directly into a class instance
        QueryCheckerDataTest queryCheckerDataTest = new QueryCheckerDataTest();

        ArrayList<LinkedHashMap> ResultsMap = new ObjectMapper()
                .readerFor(Object.class)
                .readValue(queryCheckerDataTest.getFourResultsJson());

        Assertions.assertEquals(ResultsMap.size(), 4);
        Assertions.assertEquals(ResultsMap.get(0).get("Name"), "Daphne123");


    }
}