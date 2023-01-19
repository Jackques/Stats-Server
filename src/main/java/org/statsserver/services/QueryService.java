package org.statsserver.services;

import org.springframework.stereotype.Service;
import org.statsserver.domain.Query;

import java.util.ArrayList;
import java.util.HashMap;

@Service public class QueryService {

    ArrayList<Query> queries = new ArrayList<Query>();

    public boolean createQuery(String projectName, HashMap<String, String> query) {
        try{
            this.queries.add(new Query(query.get("name"), query.get("description"), query.get("queryCommandString"), projectName));
        }catch(Exception e){
            return false;
        }

        return true;
    }
}
