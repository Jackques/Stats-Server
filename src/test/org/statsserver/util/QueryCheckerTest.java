package org.statsserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.statsserver.domain.*;
import org.statsserver.services.KeyDataListStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.statsserver.util.QueryBuilderTest.getQuery;
import static org.statsserver.util.QueryCheckerDataTest.getTHelperMockDataSet;

class QueryCheckerTest {

    private LinkedHashMap<String, Object> mockData;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // populate the keyData list in my app
        KeyDataListStatic.FILL_KEYDATAMAP(KeyDataMockClass.generateTHelperData());

        // Get the Mockdata
        // HINT: In Profile_JackUpdatedKopie_15-09-2022--22-53-39.json I keep the same in json format for easy copy & pasting
        // HINT 2: https://jsoneditoronline.org/
        this.mockData = getTHelperMockDataSet("Jack");
    }

    @Test
    public void generalTestAllTrue() {
        // Get the profiles as list as domain Profile
        ArrayList<Profile> profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./")
                )
        );

        // Set up querySet and fill it with query & params
        QuerySet querySetDomain = new QuerySet(
                "test",
                "testdescription",
                "T-Helper",
                "LINE_GRAPH",
                profileList,
                new ArrayList<>());
        ArrayList<HashMap> queryParamsList = new ArrayList<>();
//        Arrays.asList(
//                getQueryParam("No", "EQUALS", 1),
//                getQueryParam("No", "EQUALS", 1)
//        )
        Query query = getQuery(
                profileList,
                "LAST_9",
                "ALL",
                "ALL",
                "jacklabel",
                "#000080",
                true,
                queryParamsList
        );
        querySetDomain.setQueries(new ArrayList<>(Arrays.asList(query)));

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");


        // Assert
        Assertions.assertEquals(
                querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults(), 8);

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#000080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), true);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "LINE_GRAPH");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Daphne123");
    }


    @Test
    public void generalTestAllFalse() {
        // Get the profiles as list as domain Profile
        ArrayList<Profile> profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./")
                )
        );

        // Set up querySet and fill it with query & params
        QuerySet querySetDomain = new QuerySet(
                "test2",
                "testdescription2",
                "T-Helper",
                "COLUMN_CHART",
                profileList,
                new ArrayList<>());
        ArrayList<HashMap> queryParamsList = new ArrayList<>();
        Query query = getQuery(
                profileList,
                "LAST_1",
                "ALL",
                "ALL",
                "jacklabel1",
                "#990080",
                false,
                queryParamsList
        );
        querySetDomain.setQueries(new ArrayList<>(Arrays.asList(query)));

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");


        // Assert
        Assertions.assertEquals(
                querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults(), 1);

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel1");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "AnotherNiceGirl123");
    }
}