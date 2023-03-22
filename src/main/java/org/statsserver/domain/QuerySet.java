package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.statsserver.util.QueryChecker;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class QuerySet {
    private UUID id;
    private String name;
    private String description;
    private String projectName;
    private QuerySetResults querySetResults;
    private QueryMetaData queryMetaData;
    private ArrayList<Query> queries = new ArrayList<>();

    public QuerySet(String name, String description, String projectName, String graphType, ArrayList<String> usedProfiles, List<HashMap<String, Object>> queryList) throws Exception {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.projectName = projectName;

        queryList.forEach((query) -> {
            this.queries.add(new Query(query, projectName, usedProfiles));
            this.checkDuplicatesInQueryLabels(this.queries);
        });

        this.queryMetaData = this.generateQueryMetaData(usedProfiles, graphType);
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("queryMetaData")
    private void unpackNestedQueryMetaData(Map<String, Object> queryMetaData) {
        System.out.println("here is my query meta data:");
        System.out.println(queryMetaData);
        this.queryMetaData = new QueryMetaData((ArrayList<String>) queryMetaData.get("affectedProfileNames"), (String) queryMetaData.get("graphType"));
        System.out.println("will this bed executed?");
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("queryList")
    private void unpackNestedqueryList(List<HashMap<String, Object>> queryList) {
        System.out.println("here is my queryList:");
        System.out.println(queryList);
        queryList.forEach((query) -> {
            this.queries.add(new Query(query, projectName, null));
        });
        this.checkDuplicatesInQueryLabels(this.queries);
    }

    private void checkDuplicatesInQueryLabels(ArrayList<Query> queries) {
        ArrayList<String> queryLabels = new ArrayList<>();
        queries.forEach((query) -> {
            String label = query.getLabelForThisQuery();
            if (queryLabels.contains(label)) {
                throw new RuntimeException("QueryList already has a query with label: " + label);
            }
            queryLabels.add(label);
        });
    }

    private QueryMetaData generateQueryMetaData(ArrayList<String> fromProfiles, String graphType) {
        return new QueryMetaData(fromProfiles, graphType);
    }

    public void processQueriesResults(HashMap<String, HashMap> querySetResults, String dateKeyName) {

        this.queries.forEach((query)->{
            ArrayList<HashMap> resultList = new ArrayList<>();
            resultList.addAll(QueryChecker.getProfileResults(query, querySetResults));
            resultList.removeAll(QueryChecker.getAmountResultsToBeRemoved(query, resultList));
            resultList.removeAll(QueryChecker.getFromOrToDateResultsToBeRemoved(query, resultList, dateKeyName, true));
            resultList.removeAll(QueryChecker.getFromOrToDateResultsToBeRemoved(query, resultList, dateKeyName, false));

            System.out.println("So,.. what is the result we end up with so far with checking the amount, from & to-date & profiles?");
            System.out.println(resultList.size());

            resultList.removeAll(QueryChecker.getQueryParametersResultsToBeRemoved(query, resultList));

        });
        //todo todo todo: don't forget to set the correct amounts on query and querySetResult after filtering these results and setting to to querySetResults
        //todo: in query options;
        // fixed amount (e.g; 100; return 100 results WHICH satisfy the above params)
        // last x amount; (e.g; LAST_50; return the last 50 results REGARDLESS wether they satisfy the above parameters)
        // ALL; simply returns all REGARDLESS wether they satisfy the above parameters)
    }
}