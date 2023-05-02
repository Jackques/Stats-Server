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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.statsserver.util.QueryBuilderTest.getQuery;
import static org.statsserver.util.QueryBuilderTest.getQueryParam;
import static org.statsserver.util.QueryCheckerDataTest.getTHelperMockDataSet;

class QueryParameterListCheckerTest {

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
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_CONTAINS_containsValidArrayItem() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList("Bioscoop")))
                )
        );
        setupQuerySetDomainWithQueryParams(queryParamsList);

        // Assert
        Assertions.assertEquals(
                3, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Daphne123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "Cindy123");
        Assertions.assertEquals(resultDetails.get(2).get("Name"), "Priscilla123");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_CONTAINS_containsValidArrayItem2() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList("Bioscoop", "Gamer")))
                )
        );
        setupQuerySetDomainWithQueryParams(queryParamsList);

        // Assert
        Assertions.assertEquals(
                1, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Daphne123");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_CONTAINS_containsValidArrayItem3() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList("Bioscoop", "Archeologie")))
                )
        );
        setupQuerySetDomainWithQueryParams(queryParamsList);

        // Assert
        Assertions.assertEquals(
                0, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_EXCLUDES_containsValidArrayItem() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "EXCLUDES", new ArrayList<>(Arrays.asList("Fotografie")))
                )
        );
        setupQuerySetDomainWithQueryParams(queryParamsList);

        // Assert
        Assertions.assertEquals(
                7, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Daphne123");
        Assertions.assertEquals(resultDetails.get(1).get("Name"), "Cindy123");
        Assertions.assertEquals(resultDetails.get(2).get("Name"), "VerzorgendeMeid123");
        Assertions.assertEquals(resultDetails.get(3).get("Name"), "SnelleReageerder123");
        Assertions.assertEquals(resultDetails.get(4).get("Name"), "AnotherNiceGirl123");
        Assertions.assertEquals(resultDetails.get(5).get("Name"), "SpiritualGirl123");
        Assertions.assertEquals(resultDetails.get(6).get("Name"), "PhotographyGirl123"); // this girl is called PhotographyGirl123 whilst she doesnt have photography as an interest.. i know, it's weird but it can happen

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_EXCLUDES_containsValidArrayItem2() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "EXCLUDES", new ArrayList<>())
                )
        );
        Throwable raisedException = catchThrowable(() ->
                setupQuerySetDomainWithQueryParams(queryParamsList)
        );

        // Assert
        assertThat(raisedException).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Provided list value may not be empty: []");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_CONTAINS_containsInvalidArrayItem() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList(1)))
                )
        );
        Throwable raisedException = catchThrowable(() ->
                setupQuerySetDomainWithQueryParams(queryParamsList)
        );

        // Assert
        assertThat(raisedException).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Value: '[1]' provided in: 'Interests' is invalid, with operator: CONTAINS. Expected value of type: String");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_invalidValue() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "CONTAINS", true)
                )
        );

        Throwable raisedException = catchThrowable(() ->
                setupQuerySetDomainWithQueryParams(queryParamsList)
        );

        // Assert
        assertThat(raisedException).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Value: 'true' provided in: 'Interests' is invalid, with operator: CONTAINS. Expected value of type: String");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_invalidValue2() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "CONTAINS", "Bioscoop")
                )
        );

        Throwable raisedException = catchThrowable(() ->
                setupQuerySetDomainWithQueryParams(queryParamsList)
        );

        // Assert
        assertThat(raisedException).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Value: 'Bioscoop' provided in: 'Interests' is invalid, with operator: CONTAINS. Expected value of type: String");

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDateALL_queryParam_Interests_invalidValue3() {
        ArrayList<HashMap> queryParamsList = new ArrayList<>(
                Arrays.asList(
                        getQueryParam("Interests", "CONTAINS", new ArrayList<>(Arrays.asList("Bioscoop", "Gamer", 1)))
                )
        );

        Throwable raisedException = catchThrowable(() ->
                setupQuerySetDomainWithQueryParams(queryParamsList)
        );

        // Assert
        assertThat(raisedException).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Not all values in the provided list are of the same type: [Bioscoop, Gamer, 1]");

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