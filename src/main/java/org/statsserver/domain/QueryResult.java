package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryResult {
    private String id;
    private String labelForThisQuery = "";
    private int totalResults = 0;
    @JsonIgnore
    private ArrayList<HashMap> queryResults = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @JsonProperty("queryResults")
    private void unpackNestedQueryResults(List<HashMap> queryResult) {
        System.out.println("here is my queryResult:");
        System.out.println(queryResult);
        this.queryResults.addAll(queryResult);
    }
}
