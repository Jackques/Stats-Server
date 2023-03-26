package org.statsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Getter // Remember; for sending back data to the REST controller it needs to have a @Getter otherwise Jackson (part of Springboot RESTcontroller?) cannot access the properties in this class
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuerySetResults {
    private int totalResults;
    private int amountOfQueries;
    private ArrayList<QueryResult> queryResults = new ArrayList<>();
    public boolean isValidIds(ArrayList<String> queryIds) {
        return queryIds
                .stream()
                .allMatch((queryId)-> this.queryResults
                        .stream()
                        .anyMatch((queryResult)-> queryResult.getId().equals(queryId))
                );
    };

    public ArrayList<HashMap<String, Object>> getQueryResultsByQueryIds(ArrayList<String> queryIds) {
        return this.queryResults.stream()
                .filter((queryResult)-> queryIds.contains(queryResult.getId()))
                .map((queryResult)->{
                    HashMap<String, Object> resultMap = new HashMap<>();
                    resultMap.put("queryResultId", queryResult.getId());
                    resultMap.put("queryResultsList", queryResult.getQueryResults());
                    return resultMap; //todo: there is probably a better way to do this
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addQueryResult(QueryResult queryResult){
        this.setTotalResults(this.getTotalResults() + queryResult.getTotalResults());
        this.setAmountOfQueries(this.getAmountOfQueries() + 1);

        this.queryResults.add(queryResult);
    }
}
