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
        this.querySetResults = new QuerySetResults(0, 0, new ArrayList<>());
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

            resultList = QueryChecker.getFromOrToDateResults(query, resultList, dateKeyName, true);
            resultList = QueryChecker.getFromOrToDateResults(query, resultList, dateKeyName, false);

            System.out.println("So,.. what is the result we end up with so far with checking the amount, from & to-date & profiles?");
            System.out.println(resultList.size());

            resultList = QueryChecker.getQueryParametersResults(query, resultList);

            resultList = QueryChecker.getAmountResults(query, resultList);

            QueryResult queryResult = new QueryResult(UUID.randomUUID().toString(), query.getLabelForThisQuery(), resultList.size(), resultList);
            this.getQuerySetResults().addQueryResult(queryResult);
        });
    }
}