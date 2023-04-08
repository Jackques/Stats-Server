package org.statsserver.util;

import org.statsserver.domain.Profile;
import org.statsserver.domain.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryBuilderTest {

    public static Query getQuery(ArrayList<Profile> profileList, ArrayList<HashMap> queryParamsList){
        HashMap<String, Object> query = new HashMap();

        query.put("amount", "LAST_100");
        query.put("fromDate", "ALL");
        query.put("toDate", "ALL");

        ArrayList<String> profiles = new ArrayList<>();
        profiles.add("Jack");

        query.put("fromProfiles", profiles);
        query.put("labelForThisQuery", "jacklabel");
        query.put("colorQuery", "#000080");
        query.put("visibilityQuery", true);

        ArrayList<String> returnFields = new ArrayList<>();
        returnFields.add("No");
        query.put("returnFields", returnFields);

        query.put("queryParameters", queryParamsList);

        return new Query(query, "T-Helper", profileList);
    }

    public static HashMap<String, Object> getQueryParam(){
        HashMap<String, Object> queryParam = new HashMap();
        queryParam.put("key", "No");
        queryParam.put("operator", "EQUALS");
        queryParam.put("value", 1);

        return queryParam;
    }
}
