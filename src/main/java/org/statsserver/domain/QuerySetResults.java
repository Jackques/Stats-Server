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

    public ArrayList<QueryResultDetail> getQueryResultsByQueryIds(ArrayList<String> queryIds) {
        return this.queryResults.stream()
                .filter((queryResult)-> queryIds.contains(queryResult.getId()))
                .map((queryResult)->{
                    QueryResultDetail queryResultDetail = new QueryResultDetail(queryResult.getId(), queryResult.readQueryResultsFromFakeDB());
                    return queryResultDetail;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addQueryResult(QueryResult queryResult){
        this.setTotalResults(this.getTotalResults() + queryResult.getTotalResults());
        this.setAmountOfQueries(this.getAmountOfQueries() + 1);

        this.queryResults.add(queryResult);
    }

    public boolean removeResults() {
        this.queryResults.forEach((queryResult)->{
            if(!queryResult.removeQueryResultDBFile()){
                throw new RuntimeException("File: " + queryResult.getId() + ".json could not be removed.");
            }
        });
        return true;
    }
}
