package org.statsserver.domain;

import lombok.Getter;
import lombok.Setter;
import org.statsserver.enums.GraphType;

import java.util.ArrayList;

@Getter
@Setter
public class QueryMetaData {

    private ArrayList<String> affectedProfileNames;
    private Enum<GraphType> graphType;

    public QueryMetaData(ArrayList<String> affectedProfileNames, String graph) {
        this.affectedProfileNames = affectedProfileNames;
        this.graphType = GraphType.valueOf(graph);
    }
}
