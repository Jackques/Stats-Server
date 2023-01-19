package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.statsserver.exceptions.InvalidQueryStringException;

import java.util.ArrayList;
import java.util.UUID;

public class Query {
    @JsonIgnore
    private UUID id;
    private String name;
    private String description;
    private String queryCommandString;
    private String projectName;

    private QueryResults queryResults;
    private QueryMetaData queryMetaData;

    public Query(String name, String description, String queryCommandString, String projectName) throws InvalidQueryStringException {
        this.name = name;
        this.description = description;
        this.queryCommandString = queryCommandString;
        this.projectName = projectName;

        if(!this.isValidQueryString(queryCommandString)){
            throw new InvalidQueryStringException("Query string is invalid");
        }

        this.id = UUID.randomUUID();
        this.queryMetaData = new QueryMetaData(this.getAffectedProfile(this.queryCommandString), this.getAffectedKeys(this.queryCommandString));
    }

    private boolean isValidQueryString(String queryString){
        return true;
        // todo todo todo: needs inplementation
    }

    private ArrayList<String> getAffectedProfile(String queryCommandString){
        // todo todo todo: needs inplementation
        return new ArrayList<>();
    }

    private ArrayList<String> getAffectedKeys(String queryCommandString){
        // todo todo todo: needs inplementation
        return new ArrayList<>();
    }
}
