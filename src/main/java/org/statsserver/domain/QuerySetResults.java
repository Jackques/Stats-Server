package org.statsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter // Remember; for sending back data to the REST controller it needs to have a @Getter otherwise Jackson (part of Springboot RESTcontroller?) cannot access the properties in this class
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuerySetResults {
    private int totalResults = 123456789;
    private int amountOfQueries = 1132579486;
    private ArrayList<QueryResult> queryResults = new ArrayList<>();
    public boolean isValidIds(ArrayList<String> queryIds) {
        return queryIds
                .stream()
                .allMatch((queryId)-> this.queryResults
                        .stream()
                        .anyMatch((queryResult)-> queryResult.getId().equals(queryId))
                );
    };

    public ArrayList<QueryResult> getQueryResultsByQueryIds(ArrayList<String> queryIds) {
        return this.queryResults.stream()
                .filter((queryResult)-> queryIds.contains(queryResult.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
