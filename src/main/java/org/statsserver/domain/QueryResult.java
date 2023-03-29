package org.statsserver.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.statsserver.services.QueryResultDetailsFakeDB;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class QueryResult {
    private String id;
    private String labelForThisQuery = "";
    private int totalResults = 0;

    public QueryResult(String id, String labelForThisQuery, ArrayList<HashMap> queryResults) {
        this.id = id;
        this.labelForThisQuery = labelForThisQuery;
        this.totalResults = queryResults.size();

        QueryResultDetailsFakeDB.writeQueryResultDetailsToFile(id, queryResults);
    }

    public ArrayList<HashMap> readQueryResultsFromFakeDB() {
        try {
            return QueryResultDetailsFakeDB.getQueryResultDetailsFromFile(id);
        } catch (Exception e) {
            throw new RuntimeException("Could not read from file with id: " + id);
        }
    }
}
