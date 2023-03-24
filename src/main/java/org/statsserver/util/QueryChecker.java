package org.statsserver.util;

import org.statsserver.domain.Query;
import org.statsserver.domain.QueryParameter;

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

    public static ArrayList<HashMap> getAmountResults(Query query, ArrayList<HashMap> resultList) {
        if (query.getSetAmountIsAll()) {
            return resultList;
        }

        // get the last xxx results, starting with the last profile
        if (query.getSetAmountHasLast()) {
            if (query.getFromProfiles().size() > 1) {
                throw new RuntimeException("Amount option 'LAST_xxx' cannot be set if multiple profiles are active");
            }
            int amount = query.getSetAmountValue();

            if (resultList.size() > amount) {
                int difference = resultList.size() - amount;
                return new ArrayList<>(resultList.subList(difference, resultList.size()));
            }
            return resultList;
        }

        // get the first xxx results, starting with the first profile
        if(resultList.size() > query.getSetAmountValue()){
            return new ArrayList<>(resultList.subList(0, query.getSetAmountValue()));
        }
        return resultList;
    }

    public static ArrayList<HashMap> getFromOrToDateResults(Query query, ArrayList<HashMap> resultList, String dateKeyName, Boolean useBefore) {
        if (useBefore) {
            if (query.getSetFromDateIsAll()) {
                return resultList;
            }
        } else {
            if (query.isToDateIsAll()) {
                return resultList;
            }
        }

        ArrayList<HashMap> results = new ArrayList<>();
        resultList.forEach((result) -> {
            String dateKeyField = (String) result.get(dateKeyName);

            // results which are null do NOT SATISFY the request thus are not returned
            if (dateKeyField != null) {

                if (useBefore) {
                    if (DateChecker.isDateOneBeforeDateTwo(query.getFromDate(), dateKeyField)) {
                        results.add(result);
                    }
                } else {
                    if (DateChecker.isDateOneAfterDateTwo(query.getToDateValue(), dateKeyField)) {
                        results.add(result);
                    }
                }
            }
        });
        return results;
    }

    public static ArrayList<HashMap> getQueryParametersResults(Query query, ArrayList<HashMap> resultList) {
        if(query.getQueryParameters().size() == 0){
            return resultList;
        }

        ArrayList<HashMap> resultsToBeReturned = new ArrayList<>();
        resultList.forEach((result) -> {
            query.getQueryParameters().forEach((queryParameter) -> {
                Object resultProperty = result.get(queryParameter.getKey());
                    Boolean hasSubData = queryParameter.getKeySubData() != null;
                    String valueType = hasSubData ? queryParameter.getKeySubData().getValueType() : queryParameter.getKeyData().getValueType();

                    if (resultProperty.getClass().toString().equals("class java.util.ArrayList")) {
                        // if result is an empty array (e.g. ghosts list),
                        // skip this result
                    } else {

                        switch (valueType) {
                            case "String" -> {
                                if (resultSatisfiesStringQueryParameter(queryParameter, resultProperty)) {
                                    resultsToBeReturned.add(result);
                                }
                            }
                            case "DateString" -> {
                                if (resultSatisfiesDateStringQueryParameter(queryParameter, resultProperty)) {
                                    resultsToBeReturned.add(result);
                                }
                            }
                            case "WholeNumber", "DecimalNumber" -> {
                                if (resultSatisfiesNumberQueryParameter(queryParameter, resultProperty)) {
                                    resultsToBeReturned.add(result);
                                }
                            }
                            case "Boolean" -> {
                                if (resultSatisfiesBooleanQueryParameter(queryParameter, resultProperty)) {
                                    resultsToBeReturned.add(result);
                                }
                            }
                            case "List" -> {
                                if (resultSatisfiesListQueryParameter(queryParameter, resultProperty)) {
                                    resultsToBeReturned.add(result);
                                }
                            }
                        }
                    }

            });

        });
        return resultsToBeReturned;
    }

    private static boolean resultSatisfiesListQueryParameter(QueryParameter queryParameter, Object resultProperty) {
        String operator = queryParameter.getOperator();
        ArrayList<?> queryParamListValue = (ArrayList<?>) queryParameter.getValue();
        ArrayList<?> resultListValue = (ArrayList<?>) resultProperty;

        if (queryParamListValue.size() == 0) {
            return true;
        }

        switch (operator) {
            case "CONTAINS" -> {
                return resultListValue.containsAll(queryParamListValue) ? true : false;
            }
            case "EXCLUDES" -> {
                return resultListValue.stream().noneMatch((result) -> queryParamListValue.contains(result)) ? true : false;
            }
            default -> {
                throw new RuntimeException("Unrecognized boolean queryparameter operator: " + operator);
            }
        }
    }

    private static boolean resultSatisfiesBooleanQueryParameter(QueryParameter queryParameter, Object resultProperty) {
        String operator = queryParameter.getOperator();
        boolean queryParamBooleanValue = (boolean) queryParameter.getValue();
        boolean resultBooleanValue = (boolean) resultProperty;

        switch (operator) {
            case "EQUALS" -> {
                return queryParamBooleanValue == resultBooleanValue ? true : false;
            }
            case "NOT_EQUALS" -> {
                return queryParamBooleanValue != resultBooleanValue ? true : false;
            }
            default -> {
                throw new RuntimeException("Unrecognized boolean queryparameter operator: " + operator);
            }
        }
    }

    private static boolean resultSatisfiesNumberQueryParameter(QueryParameter queryParameter, Object resultProperty) {
        String operator = queryParameter.getOperator();
        double queryParamNumericValue = Double.parseDouble(queryParameter.getValue().toString());
        double numericKey = Double.parseDouble(resultProperty.toString());

        switch (operator) {
            case "LESS_THAN" -> {
                return queryParamNumericValue < numericKey ? true : false;
            }
            case "LESS_THAN_OR_EQUAL_TO" -> {
                return queryParamNumericValue <= numericKey ? true : false;
            }
            case "EQUALS" -> {
                return queryParamNumericValue == numericKey ? true : false;
            }
            case "GREATER_THAN" -> {
                return queryParamNumericValue > numericKey ? true : false;
            }
            case "GREATER_THAN_OR_EQUAL_TO" -> {
                return queryParamNumericValue >= numericKey ? true : false;
            }
            default -> {
                throw new RuntimeException("Unrecognized number queryparameter operator: " + operator);
            }
        }
    }

    private static boolean resultSatisfiesDateStringQueryParameter(QueryParameter queryParameter, Object resultProperty) {
        String dateStringResultProperty = resultProperty.toString();
        String operator = queryParameter.getOperator();

        switch (operator) {
            case "BEFORE_DATE" -> {
                return DateChecker.isDateOneBeforeDateTwo(dateStringResultProperty, queryParameter.getValue().toString()) ? true : false;
            }
            case "AFTER_DATE" -> {
                return DateChecker.isDateOneAfterDateTwo(dateStringResultProperty, queryParameter.getValue().toString()) ? true : false;
            }
            default -> {
                throw new RuntimeException("Unrecognized datestring queryparameter operator: " + operator);
            }
        }
    }

    private static boolean resultSatisfiesStringQueryParameter(QueryParameter queryParameter, Object resultProperty) {
        String stringResultProperty = resultProperty.toString();
        String operator = queryParameter.getOperator();

        switch (operator) {
            case "EQUALS" -> {
                return stringResultProperty.equals(queryParameter.getValue().toString()) ? true : false;
            }
            case "CONTAINS" -> {
                return stringResultProperty.contains(queryParameter.getValue().toString()) ? true : false;
            }
            case "EXCLUDES" -> {
                return !stringResultProperty.contains(queryParameter.getValue().toString()) ? true : false;
            }
            default -> {
                throw new RuntimeException("Unrecognized string queryparameter operator: " + operator);
            }
        }
    }
}
