package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class QuerySet {

    private UUID id;
    private String name;
    private String description;
    private String projectName;
    private QueryResults queryResults;
    private QueryMetaData queryMetaData;
    private ArrayList<Query> queries = new ArrayList<>();

    public QuerySet(String name, String description, String projectName, String graphType, ArrayList<String> fromProfiles, List<HashMap<String, Object>> queryList) throws Exception {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.projectName = projectName;

        queryList.forEach((query) -> {
            this.queries.add(new Query(query, projectName));
            this.checkDuplicatesInQueryLabels(this.queries);
        });

        this.queryMetaData = this.generateQueryMetaData(fromProfiles, graphType);
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
    @JsonProperty("queryResults")
    private void unpackNestedQueryResults(Map<String, Object> queryResults) {
        System.out.println("here are my query results:");
        System.out.println(queryResults);
        //todo todo todo: need to create this once i know what this data will look like
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("queryList")
    private void unpackNestedqueryList(List<HashMap<String, Object>> queryList) {
        System.out.println("here is my queryList:");
        System.out.println(queryList);
        queryList.forEach((query) -> {
            this.queries.add(new Query(query, projectName));
        });
        this.checkDuplicatesInQueryLabels(this.queries);
    }

    private void checkDuplicatesInQueryLabels(ArrayList<Query> queries) {
        ArrayList<String> queryLabels = new ArrayList<>();
        queries.forEach((query)->{
            String label = query.getLabelForThisQuery();
            if(queryLabels.contains(label)){
                throw new RuntimeException("QueryList already has a query with label: "+label);
            }
            queryLabels.add(label);
        });
    }

    private QueryMetaData generateQueryMetaData(ArrayList<String> fromProfiles, String graphType) {
        return new QueryMetaData(fromProfiles, graphType);
    }

}