package org.statsserver.domain;

import org.statsserver.enums.GraphType;

import java.util.UUID;

public class Query {
    private UUID id;
    private String name;
    private String description;
    private String projectName;
    private Enum<GraphType> graphType = GraphType.COLUMN_CHART;
    private QueryResults queryResults;
    private QueryMetaData queryMetaData;
}
