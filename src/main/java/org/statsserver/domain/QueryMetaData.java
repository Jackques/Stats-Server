package org.statsserver.domain;

import lombok.Getter;
import lombok.Setter;
import org.statsserver.enums.GraphType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class QueryMetaData {
    private Set<String> affectedProfileNames = new HashSet<>();
    private Enum<GraphType> graphType;

    public QueryMetaData(ArrayList<String> affectedProfileNames, String graph) {
        this.affectedProfileNames.addAll(affectedProfileNames);
        this.graphType = GraphType.valueOf(graph);
    }
}
