package org.statsserver.util;

import org.statsserver.domain.Profile;
import org.statsserver.domain.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryBuilderTest {

    public static Query getQuery(ArrayList<Profile> profileList, String amount, String fromDate, String toDate, String labelForThisQuery, String colorQuery, Boolean visibilityQuery, ArrayList<HashMap> queryParamsList){
        HashMap<String, Object> query = new HashMap();

        query.put("amount", amount);
        query.put("fromDate", fromDate);
        query.put("toDate", toDate);

        ArrayList<String> profiles = new ArrayList<>();
        profileList.forEach(profileListItem -> {
            profiles.add(profileListItem.getName());
        });

        query.put("fromProfiles", profiles);
        query.put("labelForThisQuery", labelForThisQuery);
        query.put("colorQuery", colorQuery);
        query.put("visibilityQuery", visibilityQuery);

        ArrayList<String> returnFields = new ArrayList<>();
        returnFields.add("No");
        query.put("returnFields", returnFields);

        query.put("queryParameters", queryParamsList);

        return new Query(query, "T-Helper", profileList);
    }

    public static HashMap<String, Object> getQueryParam(String key, String operator, Object value){
        HashMap<String, Object> queryParam = new HashMap();
        queryParam.put("key", key);
        queryParam.put("operator", operator);
        queryParam.put("value", value);

        return queryParam;
    }
}
