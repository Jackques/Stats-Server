package org.statsserver.util;

import org.statsserver.domain.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryChecker {

    public static ArrayList<HashMap> getProfileResults(Query query, HashMap<String, HashMap> querySetResults) {
        ArrayList<HashMap> resultList = new ArrayList<>();
        query.getFromProfiles().forEach((profile) -> {
            resultList.addAll(querySetResults.get(profile).values());
        });
        return resultList;
    }

    public static ArrayList<HashMap> getAmountResultsToBeRemoved(Query query, ArrayList<HashMap> resultList) {
        // needs to return the resultList of objects which need to be removed
        if (query.getSetAmountHasLast()) {
            if (query.getFromProfiles().size() > 1) {
                throw new RuntimeException("Amount option 'LAST_xxx' cannot be set if multiple profiles are active");
            }
            int amount = query.getSetAmountValue();
            return new ArrayList<>(resultList.subList(0, (resultList.size() - amount)));
        }
        if (query.getSetAmountIsAll()) {
            return new ArrayList<>();
        }
        int resultAmount = resultList.size() - query.getSetAmountValue();
        return new ArrayList<>(resultList.subList(0, resultAmount));
    }

    public static ArrayList<HashMap> getFromOrToDateResultsToBeRemoved(Query query, ArrayList<HashMap> resultList, String dateKeyName, Boolean useBefore) {
        if(useBefore){
            if (query.getSetFromDateIsAll()) {
                return new ArrayList<>();
            }
        }else{
            if (query.isToDateIsAll()) {
                return new ArrayList<>();
            }
        }

        ArrayList<HashMap> results = new ArrayList<>();
        resultList.forEach((result) -> {
            String dateKeyField = (String) result.get(dateKeyName);
            if (dateKeyField == null) {
                results.add(result); // results which are null do NOT SATISFY the request thus are not returned
            }else{
                if(useBefore){
                    if (!DateChecker.isDateOneBeforeDateTwo(query.getFromDate(), dateKeyField)) {
                        results.add(result);
                    }
                }else{
                    if (!DateChecker.isDateOneAfterDateTwo(query.getToDateValue(), dateKeyField)) {
                        results.add(result);
                    }
                }
            }
        });
        return results;
    }
}
