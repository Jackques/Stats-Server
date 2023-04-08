package org.statsserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.statsserver.domain.*;
import org.statsserver.services.KeyDataListStatic;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.statsserver.util.QueryBuilderTest.getQuery;
import static org.statsserver.util.QueryBuilderTest.getQueryParam;
import static org.statsserver.util.QueryCheckerDataTest.getTHelperMockDataSet;

class QueryCheckerTest {

    private LinkedHashMap<String, Object> mockData;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // populate the keyData list in my app
        KeyDataListStatic.FILL_KEYDATAMAP(KeyDataMockClass.generateTHelperData());

        // Get the Mockdata
        this.mockData = getTHelperMockDataSet("Jack");
    }

    @Test
    public void secondTest() {
        // Get the profiles as list as domain Profile
        ArrayList<Profile> profileList = new ArrayList<>(Arrays.asList(new Profile("Jack", "./")));

        // Set up querySet and fill it with query & params
        QuerySet querySetDomain = new QuerySet(
                "test",
                "testdescription",
                "T-Helper",
                "LINE_GRAPH",
                profileList,
                new ArrayList<>());
        ArrayList<HashMap> queryParamsList = new ArrayList<>(Arrays.asList(getQueryParam()));
        Query query = getQuery(profileList, queryParamsList);
        querySetDomain.setQueries(new ArrayList<>(Arrays.asList(query)));

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");


        // Assert
        Assertions.assertEquals(querySetDomain.getQuerySetResults().getQueryResults().size(), 1);

        ArrayList<HashMap> resultDetails = querySetDomain.getQuerySetResults().getQueryResults().get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Daphne123");
    }
}