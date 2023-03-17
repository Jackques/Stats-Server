package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class QueryResult {
    private String id;
    private String labelForThisQuery = "";
    private int totalResults = 123456789;
    @JsonIgnore
    private ArrayList<Object> queryResultsABC = new ArrayList<>();

    public QueryResult(String labelForThisQuery, int totalResults) {
        this.labelForThisQuery = labelForThisQuery;
        this.totalResults = totalResults;
    }
}
