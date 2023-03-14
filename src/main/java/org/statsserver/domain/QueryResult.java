package org.statsserver.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class QueryResult {
    private String labelForThisQuery = "";
    private int totalResults = 123456789;
    private ArrayList<Object> queryResults = new ArrayList<>();

}
