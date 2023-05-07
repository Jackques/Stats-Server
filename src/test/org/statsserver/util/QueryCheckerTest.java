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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.statsserver.util.QueryBuilderTest.getQuery;
import static org.statsserver.util.QueryCheckerDataTest.getTHelperMockDataSet;

class QueryCheckerTest {

    private final static ArrayList<String> queryDetailsResultsFileNames = new ArrayList<String>();
    private LinkedHashMap<String, Object> mockData;

    @BeforeAll
    static void beforeAll(){
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
    }
    @AfterAll
    static void afterAll(){
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
    public void amountALL_fromDateALL_toDateALL() {
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

        // Assert
        Assertions.assertEquals(
                querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults(), 10);

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#000080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), true);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "LINE_GRAPH");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals(resultDetails.get(0).get("Name"), "Daphne123");



        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        String detailResultId = querySetDomain.getQuerySetResults().getQueryResults().get(0).getId();
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLAST1_fromDateALL_toDateALL() {
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
                "3223455%^&'",
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
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "3223455%^&'");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals("PhotographyGirl123", resultDetails.get(0).get("Name"));


        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        String detailResultId = querySetDomain.getQuerySetResults().getQueryResults().get(0).getId();
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLAST3_fromDateALL_toDateALL() {
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
                "LAST_3",
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
                querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults(), 3);

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel1");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals("AnotherNiceGirl123", resultDetails.get(0).get("Name"));
        Assertions.assertEquals("SpiritualGirl123", resultDetails.get(1).get("Name"));
        Assertions.assertEquals("PhotographyGirl123", resultDetails.get(2).get("Name"));


        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        String detailResultId = querySetDomain.getQuerySetResults().getQueryResults().get(0).getId();
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amount5_fromDateALL_toDateALL() {
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
                "5",
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
                querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults(), 5);

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel1");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals("Daphne123", resultDetails.get(0).get("Name"));
        Assertions.assertEquals("Cindy123", resultDetails.get(1).get("Name"));
        Assertions.assertEquals("Priscilla123", resultDetails.get(2).get("Name"));
        Assertions.assertEquals("VerzorgendeMeid123", resultDetails.get(3).get("Name"));
        Assertions.assertEquals("SnelleReageerder123", resultDetails.get(4).get("Name"));


        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        String detailResultId = querySetDomain.getQuerySetResults().getQueryResults().get(0).getId();
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void invalidGraphType() {
        assertThatThrownBy(() -> {
            QuerySet querySetDomain = new QuerySet(
                    null,
                    null,
                    "T-Helper",
                    "jibberish",
                    new ArrayList<>(),
                    new ArrayList<>());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant org.statsserver.enums.GraphType.jibberish");
    }

    @Test
    public void amountALL_fromDate01_10_2017_toDateALL() {
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
                "ALL",
                "2017-10-01T14:26:11.339Z",
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
                querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults(), 5);

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel1");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals("TopPickGhosting123", resultDetails.get(0).get("Name"));
        Assertions.assertEquals("2018-09-08T12:53:23.385Z", resultDetails.get(0).get("Date-liked-or-passed"));

        Assertions.assertEquals("GhostingGirl123", resultDetails.get(1).get("Name"));
        Assertions.assertEquals("2019-09-08T12:53:23.385Z", resultDetails.get(1).get("Date-liked-or-passed"));

        Assertions.assertEquals("AnotherNiceGirl123", resultDetails.get(2).get("Name"));
        Assertions.assertEquals("2020-09-08T12:53:23.385Z", resultDetails.get(2).get("Date-liked-or-passed"));

        Assertions.assertEquals("SpiritualGirl123", resultDetails.get(3).get("Name"));
        Assertions.assertEquals("2022-09-08T12:53:23.385Z", resultDetails.get(3).get("Date-liked-or-passed"));

        Assertions.assertEquals("PhotographyGirl123", resultDetails.get(4).get("Name"));
        Assertions.assertEquals("2021-09-08T12:53:23.385Z", resultDetails.get(4).get("Date-liked-or-passed"));

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDateALL_toDate01_10_2018() {
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
                "ALL",
                "ALL",
                "2018-09-08T12:53:23.386Z",
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
                6, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel1");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals("Daphne123", resultDetails.get(0).get("Name"));
        Assertions.assertEquals("2015-09-08T12:53:23.385Z", resultDetails.get(0).get("Date-liked-or-passed"));

        Assertions.assertEquals("Cindy123", resultDetails.get(1).get("Name"));
        Assertions.assertEquals("2016-09-08T12:53:23.385Z", resultDetails.get(1).get("Date-liked-or-passed"));

        Assertions.assertEquals("Priscilla123", resultDetails.get(2).get("Name"));
        Assertions.assertEquals("2016-09-08T12:53:23.385Z", resultDetails.get(2).get("Date-liked-or-passed"));

        Assertions.assertEquals("VerzorgendeMeid123", resultDetails.get(3).get("Name"));
        Assertions.assertEquals("2017-09-08T12:53:23.385Z", resultDetails.get(3).get("Date-liked-or-passed"));

        Assertions.assertEquals("SnelleReageerder123", resultDetails.get(4).get("Name"));
        Assertions.assertEquals("2017-09-08T12:53:23.385Z", resultDetails.get(4).get("Date-liked-or-passed"));

        Assertions.assertEquals("TopPickGhosting123", resultDetails.get(5).get("Name"));
        Assertions.assertEquals("2018-09-08T12:53:23.385Z", resultDetails.get(5).get("Date-liked-or-passed"));

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountALL_fromDate08_09_2019_toDate08_09_2020() {
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
                "ALL",
                "2019-09-08T12:53:23.384Z",
                "2020-09-08T12:53:23.386Z",
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
                2, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel1");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals("GhostingGirl123", resultDetails.get(0).get("Name"));
        Assertions.assertEquals("2019-09-08T12:53:23.385Z", resultDetails.get(0).get("Date-liked-or-passed"));

        Assertions.assertEquals("AnotherNiceGirl123", resultDetails.get(1).get("Name"));
        Assertions.assertEquals("2020-09-08T12:53:23.385Z", resultDetails.get(1).get("Date-liked-or-passed"));

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amountLAST_1_fromDate08_09_2019_toDate08_09_2020() {
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
                "2019-09-08T12:53:23.384Z",
                "2020-09-08T12:53:23.386Z",
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
                1, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel1");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertNotEquals("GhostingGirl123", resultDetails.get(0).get("Name"));
        Assertions.assertNotEquals("2019-09-08T12:53:23.385Z", resultDetails.get(0).get("Date-liked-or-passed"));

        Assertions.assertEquals("AnotherNiceGirl123", resultDetails.get(0).get("Name"));
        Assertions.assertEquals("2020-09-08T12:53:23.385Z", resultDetails.get(0).get("Date-liked-or-passed"));

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }

    @Test
    public void amount1_fromDate08_09_2019_toDate08_09_2020() {
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
                "1",
                "2019-09-08T12:53:23.384Z",
                "2020-09-08T12:53:23.386Z",
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
                1, querySetDomain.getQuerySetResults().getQueryResults().get(0).getTotalResults());

        ArrayList<QueryResult> queryResults = querySetDomain.getQuerySetResults().getQueryResults();
        Assertions.assertEquals(queryResults.get(0).getLabelForThisQuery(), "jacklabel1");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getColorQuery(), "#990080");
        Assertions.assertEquals(querySetDomain.getQueries().get(0).getVisibilityQuery(), false);
        Assertions.assertEquals(querySetDomain.getQueryMetaData().getGraphType().name(), "COLUMN_CHART");

        ArrayList<HashMap> resultDetails = queryResults.get(0).readQueryResultsFromFakeDB();
        Assertions.assertEquals("GhostingGirl123", resultDetails.get(0).get("Name"));
        Assertions.assertEquals("2019-09-08T12:53:23.385Z", resultDetails.get(0).get("Date-liked-or-passed"));

        Assertions.assertNotEquals("AnotherNiceGirl123", resultDetails.get(0).get("Name"));
        Assertions.assertNotEquals("2020-09-08T12:53:23.385Z", resultDetails.get(0).get("Date-liked-or-passed"));

        // manually remove the produced queryDetailsResults file or call endpoint to remove it (if latter; check if removed succedfully)
        querySetDomain.removeQuerySetResults();
    }
}