package org.statsserver.domain;

import java.util.ArrayList;

public class QueryMetaData {

    private ArrayList<String> affectedProfileNames = new ArrayList<String>();
    private ArrayList<String> affectedKeys = new ArrayList<String>();

    public QueryMetaData(ArrayList<String> affectedProfileNames, ArrayList<String> affectedKeys) {
        this.affectedProfileNames = affectedProfileNames;
        this.affectedKeys = affectedKeys;
    }
}
