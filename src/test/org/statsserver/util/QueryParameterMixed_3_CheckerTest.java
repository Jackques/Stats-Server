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

class QueryParameterMixed_3_CheckerTest {

    private final static ArrayList<String> queryDetailsResultsFileNames = new ArrayList<String>();
    private LinkedHashMap<String, Object> mockData;
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
        this.mockData = getTHelperMockDataSet("Jack");

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
    public void amountALL_fromDateALL_toDateALL_queryParam_MixedDecimalNumberBooleanWholeNumberString() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Age", "LESS_THAN", 30.0),
                        getQueryParam("Has-profiletext", "EQUALS", true),
                        getQueryParam("Vibe-conversation", "GREATER_THAN", 5),
                        getQueryParam("Job", "CONTAINS", "Docente")
                )
        );
        setupQuerySetDomainWithQueryParams(queryParamsList);

        // Assert
        Assertions.assertEquals(
                2, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Priscilla123");
        Assertions.assertEquals(resultDetails.get(0).get("Age"), 23);
        Assertions.assertEquals(resultDetails.get(0).get("Has-profiletext"), true);
        Assertions.assertEquals(resultDetails.get(0).get("Vibe-conversation"), 8);
        Assertions.assertEquals(resultDetails.get(0).get("Job"), "Docente");

        Assertions.assertEquals(resultDetails.get(1).get("Name"), "SnelleReageerder123");
        Assertions.assertEquals(resultDetails.get(1).get("Age"), 23.56456);
        Assertions.assertEquals(resultDetails.get(1).get("Has-profiletext"), true);
        Assertions.assertEquals(resultDetails.get(1).get("Vibe-conversation"), 6);
        Assertions.assertEquals(resultDetails.get(1).get("Job"), "HoogDocente");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLAST2_fromDate2017_toDate2022_queryParam_MixedWholeNumberBooleanDateString() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Age", "LESS_THAN", 30.0),
                        getQueryParam("Has-profiletext", "EQUALS", true),
                        getQueryParam("Vibe-conversation", "GREATER_THAN", 5),
                        getQueryParam("Job", "CONTAINS", "Docente")
                )
        );
        query = getQuery(
                profileList,
                "LAST_2",
                "2016-09-07T12:53:23.385Z",
                "2017-09-08T12:53:22.385Z",
                // just a second more and 'SnelleReageerder123' would have been included in the results
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
                1, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Priscilla123");
        Assertions.assertEquals(resultDetails.get(0).get("Age"), 23);
        Assertions.assertEquals(resultDetails.get(0).get("Has-profiletext"), true);
        Assertions.assertEquals(resultDetails.get(0).get("Vibe-conversation"), 8);
        Assertions.assertEquals(resultDetails.get(0).get("Job"), "Docente");

//        Assertions.assertEquals(resultDetails.get(0).get("Name"), "SnelleReageerder123");
//        Assertions.assertEquals(resultDetails.get(0).get("Age"), 23.56456);
//        Assertions.assertEquals(resultDetails.get(0).get("Has-profiletext"), true);
//        Assertions.assertEquals(resultDetails.get(0).get("Vibe-conversation"), 6);
//        Assertions.assertEquals(resultDetails.get(0).get("Job"), "HoogDocente");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
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

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");
    }
}