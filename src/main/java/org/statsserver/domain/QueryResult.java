package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryResult {
    private String id;
    private String labelForThisQuery = "";
    private int totalResults = 123456789;
    @JsonIgnore
    private ArrayList<HashMap> queryResultsABC = new ArrayList<>();
}
