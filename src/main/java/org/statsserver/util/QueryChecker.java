package org.statsserver.util;

import org.statsserver.domain.Query;
import org.statsserver.domain.QueryParameter;

import java.util.ArrayList;
import java.util.Collection;
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

    public static ArrayList<HashMap> getQueryParametersResultsToBeRemoved(Query query, ArrayList<HashMap> resultList) {
        ArrayList<HashMap> resultsToBeRemoved = new ArrayList<>();
        resultList.forEach((result)->{

            query.getQueryParameters().forEach((queryParameter)->{
                if(result.get(queryParameter.getKey()) == null){
                    resultsToBeRemoved.add(result);
                }
                switch (queryParameter.getKeyData().getValueType()) {
                    case "String" -> {
                        if(resultSatisfiesStringQueryParameter(queryParameter, queryParameter.getKey())){
                            resultsToBeRemoved.remove(result);
                        }
                    }
                    case "DateString" -> {
                        if(resultSatisfiesDateStringQueryParameter(queryParameter, queryParameter.getKey())){
                            resultsToBeRemoved.remove(result);
                        }
                    }
                    case "WholeNumber", "DecimalNumber" -> {
                        System.out.println("Wednesday");
                    }
                    case "Boolean" -> {
                        System.out.println("Monday");
                    }
                    case "List" -> {
                        System.out.println("Tuesday");
                    }
                    case "Map" -> {
                        System.out.println("Wednesday");
                    }
                }
            });

        });
        return resultsToBeRemoved;
    }

    private static boolean resultSatisfiesDateStringQueryParameter(QueryParameter queryParameter, String key) {
        String operator = queryParameter.getOperator();
        switch (operator) {
            case "BEFORE_DATE" -> {
                return DateChecker.isDateOneBeforeDateTwo(key, queryParameter.getValue().toString()) ? true : false;
            }
            case "AFTER_DATE" -> {
                return DateChecker.isDateOneAfterDateTwo(key, queryParameter.getValue().toString()) ? true : false;
            }
            default -> {
                throw new RuntimeException("Unrecognized datestring queryparameter operator: "+operator);
            }
        }
    }

    private static boolean resultSatisfiesStringQueryParameter(QueryParameter queryParameter, String resultProperty) {
        String operator = queryParameter.getOperator();
        switch (operator) {
            case "EQUALS" -> {
                return resultProperty.equals(queryParameter.getValue().toString()) ? true : false;
            }
            case "CONTAINS" -> {
                return resultProperty.contains(queryParameter.getValue().toString()) ? true : false;
            }
            case "EXCLUDES" -> {
                return !resultProperty.contains(queryParameter.getValue().toString()) ? true : false;
            }
            default -> {
                throw new RuntimeException("Unrecognized string queryparameter operator: "+operator);
            }
        }
    }
}
