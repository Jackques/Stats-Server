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

class QueryParameterMixed_1_CheckerTest {

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
    public void amountALL_fromDateALL_toDateALL_queryParam_MixedCityAgeInterests() {
        profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./"),
                        new Profile("Jack2", "./")
                )
        );

        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("City", "EQUALS", "Rotterdam"),
                        getQueryParam("Age", "GREATER_THAN", 23.0),
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList("Hardlopen")))
                )
        );
        setupQuerySetDomainWithQueryParams(queryParamsList);

        // Assert
        Assertions.assertEquals(
                5, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "SnelleReageerder123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "PhotographyGirl123");
        Assertions.assertEquals(resultDetails.get(2).get("Name"), "SnelleReageerder123-Jack2");
        Assertions.assertEquals(resultDetails.get(3).get("Name"), "PhotographyGirl123-Jack2");
        Assertions.assertEquals(resultDetails.get(4).get("Name"), "SnelleReageerderDeTweede123-Jack2");


        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_MixedCityAgeInterests2() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("City", "EQUALS", "Rotterdam"),
                        getQueryParam("Age", "GREATER_THAN", 25.0),
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList("Hardlopen")))
                )
        );
        setupQuerySetDomainWithQueryParams(queryParamsList);

        // Assert
        Assertions.assertEquals(
                1, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "PhotographyGirl123");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amount1_fromDateALL_toDateALL_queryParam_MixedCityAgeInterests_TwoProfilesSet() {
        profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./"),
                        new Profile("Jack2", "./")
                )
        );

        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("City", "EQUALS", "Rotterdam"),
                        getQueryParam("Age", "GREATER_THAN", 23.0),
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList("Hardlopen")))
                )
        );
        query = getQuery(
                profileList,
                "1",
                "ALL",
                "ALL",
                "jacklabel",
                "#000080",
                true,
                queryParamsList
        );

        querySetDomain.setQueries(new ArrayList<>(Arrays.asList(query)));

        this.mockData.putAll(this.mockData2);

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");

        // Assert
        Assertions.assertEquals(
                1, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "SnelleReageerder123");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amount1_fromDateALL_toDateALL_queryParam_MixedCityAgeInterests_TwoProfilesSetWithResults() {
        profileList = new ArrayList<>(
                Arrays.asList(
                        new Profile("Jack", "./"),
                        new Profile("Jack2", "./")
                )
        );

        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("City", "EQUALS", "Rotterdam"),
                        getQueryParam("Age", "GREATER_THAN", 23.0),
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList("Hardlopen")))
                )
        );
        query = getQuery(
                profileList,
                "55",
                "ALL",
                "ALL",
                "jacklabel",
                "#000080",
                true,
                queryParamsList
        );
        // Despite having a big return amount set, I will only get 4 results from two profiles set
        // since those are the only results available

        querySetDomain.setQueries(new ArrayList<>(Arrays.asList(query)));

        this.mockData.putAll(this.mockData2);

        // Process the queries & mockData in order to be able to get QuerySetResults
        querySetDomain.processQueriesResults(this.mockData, "Date-liked-or-passed");

        // Assert
        Assertions.assertEquals(
                5, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "SnelleReageerder123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "PhotographyGirl123");
        Assertions.assertEquals(resultDetails.get(2).get("Name"), "SnelleReageerder123-Jack2");
        Assertions.assertEquals(resultDetails.get(3).get("Name"), "PhotographyGirl123-Jack2");
        Assertions.assertEquals(resultDetails.get(4).get("Name"), "SnelleReageerderDeTweede123-Jack2");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    //todo: extend the last test by giving the Jack2 data 1 extra data person to work with,.. thus providing slightly different data than the first dataset

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