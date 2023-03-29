package org.statsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class QueryResultDetail {
    private String queryResultId;
    private final ArrayList<HashMap> queryResultsList = new ArrayList();

    public QueryResultDetail(String queryResultId, ArrayList<HashMap> queryResultsList) {
        this.queryResultId = queryResultId;
        this.queryResultsList.addAll(queryResultsList);
    }
}
