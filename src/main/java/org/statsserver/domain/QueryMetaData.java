package org.statsserver.domain;

import lombok.Getter;
import lombok.Setter;
import org.statsserver.enums.GraphType;

import java.util.ArrayList;
@Getter
@Setter
public class QueryMetaData {
    private ArrayList<Profile> affectedProfileNames = new ArrayList<>();
    private Enum<GraphType> graphType;

    public QueryMetaData(ArrayList<Profile> affectedProfileNames, String graph) {
        this.affectedProfileNames.addAll(affectedProfileNames);
        this.graphType = GraphType.valueOf(graph);
    }
}
