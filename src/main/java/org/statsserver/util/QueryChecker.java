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
        if (useBefore) {
            if (query.getSetFromDateIsAll()) {
                return new ArrayList<>();
            }
        } else {
            if (query.isToDateIsAll()) {
                return new ArrayList<>();
            }
        }

        ArrayList<HashMap> results = new ArrayList<>();
        resultList.forEach((result) -> {
            String dateKeyField = (String) result.get(dateKeyName);
            if (dateKeyField == null) {
                results.add(result); // results which are null do NOT SATISFY the request thus are not returned
            } else {
                if (useBefore) {
                    if (!DateChecker.isDateOneBeforeDateTwo(query.getFromDate(), dateKeyField)) {
                        results.add(result);
                    }
                } else {
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
        resultList.forEach((result) -> {

            query.getQueryParameters().forEach((queryParameter) -> {
                Object resultProperty = result.get(queryParameter.getKey());
                if (resultProperty == null) {
                    resultsToBeRemoved.add(result);
                } else {
                    // TODO TODO TODO: does the resultListresultsToBeRemoved actually get 'refreshed' on every query? Otherwise i run the risk of queries taking results from previous runs of other queries
                    switch (queryParameter.getKeyData().getValueType()) {
                        case "String" -> {
                            if (resultSatisfiesStringQueryParameter(queryParameter, resultProperty)) {
                                //TODO TODO TODO: This now returns results WHICH DO satisfy the queryparam, but shouldn't it return results which DO NOT satisfy the query param?
                                resultsToBeRemoved.remove(result);
                            }
                        }
                        case "DateString" -> {
                            if (resultSatisfiesDateStringQueryParameter(queryParameter, resultProperty)) {
                                //TODO TODO TODO: This now returns results WHICH DO satisfy the queryparam, but shouldn't it return results which DO NOT satisfy the query param?
                                resultsToBeRemoved.remove(result);
                            }
                        }
                        case "WholeNumber", "DecimalNumber" -> {
                            if (resultSatisfiesNumberQueryParameter(queryParameter, resultProperty)) {
                                //TODO TODO TODO: This now returns results WHICH DO satisfy the queryparam, but shouldn't it return results which DO NOT satisfy the query param?
                                resultsToBeRemoved.remove(result);
                            }
                        }
                        case "Boolean" -> {
                            if (resultSatisfiesBooleanQueryParameter(queryParameter, resultProperty)) {
                                //TODO TODO TODO: This now returns results WHICH DO satisfy the queryparam, but shouldn't it return results which DO NOT satisfy the query param?
                                resultsToBeRemoved.remove(result);
                            }
                        }
                        case "List" -> {
                            System.out.println("Tuesday");
                            // regardless if the value is a string (which is mostly/always the case for now) or if the list contains numbers, dates etc.. I can always safely do a check IF the list contains this item
                            // simply IF the queryparam has a sublist (e.g. ghost moments, which it should, otherwise throw error in QueryParam class check), use the sublist valuetype.. if not use this BUT this should already correctly be set!

                        }
                        case "Map" -> {
                            System.out.println("Wednesday");
                            // simply IF the queryparam has a sublist (which it should, otherwise throw error in QueryParam class check), use the sublist valuetype
                        }
                    }
                }
            });

        });
        return resultsToBeRemoved;
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
        double queryParamNumericValue = (double) queryParameter.getValue();
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
