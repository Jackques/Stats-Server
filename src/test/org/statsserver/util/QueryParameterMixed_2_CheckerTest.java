package org.statsserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.statsserver.domain.*;
import org.statsserver.services.KeyDataListStatic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.statsserver.util.QueryBuilderTest.getQuery;
import static org.statsserver.util.QueryBuilderTest.getQueryParam;
import static org.statsserver.util.QueryCheckerDataTest.getTHelperMockDataSet;

class QueryParameterMixed_2_CheckerTest {

    private final static ArrayList<String> queryDetailsResultsFileNames = new ArrayList<String>();
    private LinkedHashMap<String, Object> mockData;
    private LinkedHashMap<String, Object> mockData2;
    private QuerySet querySetDomain;
    private Query query;
    private ArrayList<Profile> profileList;

    @BeforeAll
    static void beforeAll() {
        File folder = new File("src/main/resources/queryDetailResults");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            queryDetailsResultsFileNames.add(listOfFiles[i].getName());
        }
    }

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // populate the keyData list in my app
        KeyDataListStatic.FILL_KEYDATAMAP(KeyDataMockClass.generateTHelperData());

        // Get the Mockdata
        // HINT: In Profile_JackUpdatedKopie_15-09-2022--22-53-39.json I keep the same in json format for easy copy & pasting
        // HINT 2: https://jsoneditoronline.org/
        this.mockData = getTHelperMockDataSet("Jack", null);
        this.mockData2 = getTHelperMockDataSet("Jack2", "./src/test/org/statsserver/util/Profile_Jack2UpdatedKopie_15-09-2022--22-53-39.json");

        // Get the profiles as list as domain Profile
        profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./")
                )
        );

        // Set up querySet and fill it with query & params
        querySetDomain = new QuerySet(
                "test",
                "testdescription",
                "T-Helper",
                "LINE_GRAPH",
                profileList,
                new ArrayList<>()
        );
    }

    @AfterAll
    static void afterAll() {
        File folder = new File("src/main/resources/queryDetailResults");
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            if (!queryDetailsResultsFileNames.contains(listOfFile.getName())) {
                System.out.println("WARNING: File with filename: " + listOfFile.getName() + " has been creating during this testsuite but not deleted. Please review the testcode.");
                boolean isSuccessfullDelete = listOfFile.delete();
                System.out.println("WARNING: " + (isSuccessfullDelete ? "Succesfully" : "Failed to") + " delete file with filename: " + listOfFile.getName() + ", please manually delete the file");
            }
        }
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_MixedWholeNumberBooleanDateString() {
        profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./"),
                        new Profile("Jack2", "./")
                )
        );

        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Attractiveness-score", "GREATER_THAN_OR_EQUAL_TO", 7),
                        getQueryParam("Is-verified", "EQUALS", true),
                        getQueryParam("Date-match", "BEFORE_DATE", "2020-10-12T12:53:23.386Z")
                )
        );
        setupQuerySetDomainWithQueryParams(queryParamsList);

        // Assert
        Assertions.assertEquals(
                11, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Daphne123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "Cindy123");
        Assertions.assertEquals(resultDetails.get(2).get("Name"), "SnelleReageerder123");
        Assertions.assertEquals(resultDetails.get(3).get("Name"), "GhostingGirl123");
        Assertions.assertEquals(resultDetails.get(4).get("Name"), "AnotherNiceGirl123");

        Assertions.assertEquals(resultDetails.get(5).get("Name"), "Daphne123-Jack2");
        Assertions.assertEquals(resultDetails.get(6).get("Name"), "Cindy123-Jack2");
        Assertions.assertEquals(resultDetails.get(7).get("Name"), "SnelleReageerder123-Jack2");
        Assertions.assertEquals(resultDetails.get(8).get("Name"), "GhostingGirl123-Jack2");
        Assertions.assertEquals(resultDetails.get(9).get("Name"), "AnotherNiceGirl123-Jack2");

        Assertions.assertEquals(resultDetails.get(10).get("Name"), "SnelleReageerderDeTweede123-Jack2");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLAST2_fromDateALL_toDateALL_queryParam_MixedWholeNumberBooleanDateString() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Attractiveness-score", "GREATER_THAN_OR_EQUAL_TO", 7),
                        getQueryParam("Is-verified", "EQUALS", true),
                        getQueryParam("Date-match", "BEFORE_DATE", "2020-10-12T12:53:23.386Z")
                )
        );
        query = getQuery(
                profileList,
                "LAST_2",
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
                2, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "GhostingGirl123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "AnotherNiceGirl123");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLAST2_fromDate2019_toDate2020_queryParam_MixedWholeNumberBooleanDateString() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Attractiveness-score", "GREATER_THAN_OR_EQUAL_TO", 7),
                        getQueryParam("Is-verified", "EQUALS", true),
                        getQueryParam("Date-match", "BEFORE_DATE", "2020-10-12T12:53:23.386Z")
                )
        );
        query = getQuery(
                profileList,
                "LAST_2",
                "2019-09-07T12:53:23.385Z",
                "2020-10-12T12:53:23.385Z",
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
                2, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "GhostingGirl123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "AnotherNiceGirl123");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLASTAll_fromDateALL_toDateALL_queryParam_MixedWholeNumberBooleanDateString() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Attractiveness-score", "GREATER_THAN_OR_EQUAL_TO", 7),
                        getQueryParam("Is-verified", "EQUALS", true),
                        getQueryParam("Date-match", "BEFORE_DATE", "2020-10-12T12:53:23.386Z")
                )
        );
        query = getQuery(
                profileList,
                "ALL",
                "2017-09-07T12:53:23.385Z",
                "2022-10-12T12:53:23.385Z",
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
                3, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "SnelleReageerder123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "GhostingGirl123");
        Assertions.assertEquals(resultDetails.get(2).get("Name"), "AnotherNiceGirl123");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLAST2_fromDate2017_toDate2022_queryParam_MixedWholeNumberBooleanDateString_doubleProfiles() {
        // Overwrite the profileList because I want to use both profiles in my query here
        profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./"),
                        new Profile("Jack2", "./")
                )
        );

        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Attractiveness-score", "GREATER_THAN_OR_EQUAL_TO", 7),
                        getQueryParam("Is-verified", "EQUALS", true),
                        getQueryParam("Date-match", "BEFORE_DATE", "2020-10-12T12:53:23.386Z")
                )
        );

        // despite having multiple profiles set, I only want to see the first 2 results which come from the first profile set
        query = getQuery(
                profileList,
                "2",
                "2017-09-07T12:53:23.385Z",
                "2022-10-12T12:53:23.385Z",
                "jacklabel",
                "#000080",
                true,
                queryParamsList
        );

        querySetDomain.setQueries(new ArrayList<>(Arrays.asList(query)));

        // set the mocked data for profile Jack2
        this.mockData.putAll(this.mockData2);

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");


        // Assert
        Assertions.assertEquals(
                2, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "SnelleReageerder123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "GhostingGirl123");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLAST2_fromDate2017_toDate2022_queryParam_MixedWholeNumberBooleanDateString_doubleProfilesWithResults() {
        // Overwrite the profileList because I want to use both profiles in my query here
        profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./"),
                        new Profile("Jack2", "./")
                )
        );

        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Attractiveness-score", "GREATER_THAN_OR_EQUAL_TO", 7),
                        getQueryParam("Is-verified", "EQUALS", true),
                        getQueryParam("Date-match", "BEFORE_DATE", "2020-10-12T12:53:23.386Z")
                )
        );

        // Now I want to see more results (6 max) because I have two profiles set like
        query = getQuery(
                profileList,
                "6",
                "2017-09-07T12:53:23.385Z",
                "2022-10-12T12:53:23.385Z",
                "jacklabel",
                "#000080",
                true,
                queryParamsList
        );

        querySetDomain.setQueries(new ArrayList<>(Arrays.asList(query)));

        // set the mocked data for profile Jack2
        this.mockData.putAll(this.mockData2);

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");
        // Assert
        Assertions.assertEquals(
                6, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "SnelleReageerder123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "GhostingGirl123");
        Assertions.assertEquals(resultDetails.get(2).get("Name"), "AnotherNiceGirl123");
        Assertions.assertEquals(resultDetails.get(3).get("Name"), "SnelleReageerder123-Jack2");
        Assertions.assertEquals(resultDetails.get(4).get("Name"), "GhostingGirl123-Jack2");
        Assertions.assertEquals(resultDetails.get(5).get("Name"), "AnotherNiceGirl123-Jack2");
    }

    private void setupQuerySetDomainWithQueryParams(ArrayList<HashMap> queryParamsList) {
        query = getQuery(
                profileList,
                "ALL",
                "ALL",
                "ALL",
                "jacklabel",
                "#000080",
                true,
                queryParamsList
        );
        querySetDomain.setQueries(new ArrayList<>(Arrays.asList(query)));

        // set the mocked data for profile Jack2
        this.mockData.putAll(this.mockData2);

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");
    }
}