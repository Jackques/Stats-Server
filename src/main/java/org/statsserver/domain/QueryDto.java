package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.statsserver.services.KeyDataListStatic;
import org.statsserver.services.ProjectService;
import org.statsserver.util.DateChecker;
import org.statsserver.util.HexValidatorWebColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class QueryDto {

    //    @JsonIgnore
    private UUID id;
    private String name;
    private String description;
    private String projectName;
    private QueryResults queryResults;
    private QueryMetaData queryMetaData;
    @JsonIgnore
    private boolean amountIsAll = false;
    @JsonIgnore
    private boolean amountHasLast = false;
    @JsonIgnore
    private Integer amountValue = null;
    @JsonIgnore
    private boolean fromDateIsAll = false;
    @JsonIgnore
    private String fromDateValue = null;
    @JsonIgnore
    private boolean toDateIsAll = false;
    @JsonIgnore
    private String toDateValue = null;
    private String labelForThisQuery = "";
    private Boolean visibilityQuery = true;
//    private ArrayList<String> fromProfiles = null;
    private ArrayList<String> returnFields = null;
    private ArrayList<QueryParameter> queryParameters = new ArrayList<>();
    @JsonIgnore
    public ProjectService projectService;

    public QueryDto(String name, String description, String projectName, String graphType, ArrayList<String> fromProfiles, HashMap<String, Object> queryContent) throws Exception {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.projectName = projectName;

        this.processQueryContent(queryContent);

        this.queryMetaData = this.generateQueryMetaData(fromProfiles, graphType);
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("queryMetaData")
    private void unpackNestedQueryMetaData(Map<String, Object> queryMetaData) {
        System.out.println("here is my query meta data:");
        System.out.println(queryMetaData);
        this.queryMetaData = new QueryMetaData((ArrayList<String>) queryMetaData.get("affectedProfileNames"), (String) queryMetaData.get("graphType"));
        System.out.println("will this bed executed?");
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("queryResults")
    private void unpackNestedQueryResults(Map<String, Object> queryResults) {
        System.out.println("here are my query results:");
        System.out.println(queryResults);
        //todo todo todo: need to create this once i know what this data will look like
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("queryContent")
    private void unpackNestedQueryContent(HashMap<String, Object> queryContent) {
        System.out.println("here are my query results:");
        System.out.println(queryContent);
        this.processQueryContent(queryContent);
    }

    private QueryMetaData generateQueryMetaData(ArrayList<String> fromProfiles, String graphType) {
        return new QueryMetaData(fromProfiles, graphType);
    }

    private void processQueryContent(HashMap<String, Object> queryContent) {
        queryContent.forEach((key, value) -> {

            if (value == null) {
                throw new RuntimeException("Value provided in toDate is not valid");
            }

            switch (key) {
                case "amount" -> {
                    String amountValue = (String) value;
                    if (amountValue.equals("ALL")) {
                        this.setAmountIsAll(true);
                    } else {
                        Integer amountValueInteger;
                        if (amountValue.startsWith("LAST_")) {
                            try {
                                amountValueInteger = this.getPositiveIntegerFromString(amountValue.replace("LAST_", ""));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            this.setAmountHasLast(true);
                            this.setAmountValue(amountValueInteger);

                        } else {
                            try {
                                amountValueInteger = this.getPositiveIntegerFromString(amountValue);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            this.setAmountValue(amountValueInteger);
                        }
                    }
                }
                case "fromDate" -> {
                    String fromDateValue = (String) value;

                    if (fromDateValue.equals("ALL")) {
                        this.setFromDateIsAll(true);
                    } else {

                        if (DateChecker.isValidDate(fromDateValue)) {
                            this.setFromDateValue(fromDateValue);
                        } else {
                            throw new RuntimeException("Value provided in fromDate is not valid");
                        }
                    }
                }
                case "toDate" -> {
                    String toDateValue = (String) value;

                    if (toDateValue.equals("ALL")) {
                        this.setToDateIsAll(true);
                    } else {

                        if (DateChecker.isValidDate(toDateValue)) {
                            this.setToDateValue(toDateValue);
                        } else {
                            throw new RuntimeException("Value provided in toDate is not valid");
                        }
                    }
                }
                case "labelForThisQuery" -> {
                    String queryLabel = (String) value;
                    this.labelForThisQuery = queryLabel;
                }
                case "colorForThisQuery" -> {
                    String queryColorCode = (String) value;
                    if (this.isQueryColorCodeValid(queryColorCode)) {
                        this.labelForThisQuery = queryColorCode;
                    } else {
                        throw new RuntimeException("Value provided in colorForThisQuery is not valid");
                    }
                }
                case "visibilityQuery" -> {
                    Boolean visibilityQuery = (Boolean) value;
                    this.visibilityQuery = visibilityQuery;
                }
                case "returnFields" -> {
                    ArrayList<String> returnFieldsValue = (ArrayList) value;
                    if (returnFieldsValue.size() != 0 && !KeyDataListStatic.doKeysExist(returnFieldsValue, this.projectName)) {
                        throw new RuntimeException("Value provided in returnFields is not valid");
                    }
                    this.setReturnFields(returnFieldsValue);
                }
                case "queryParameters" -> {
                    ArrayList<HashMap<String, Object>> queryParametersValues = (ArrayList<HashMap<String, Object>>) value;
                    if (queryParametersValues.size() == 0) {
                        throw new RuntimeException("No values found in queryParametersValues");
                    }
                    queryParametersValues.forEach(queryParameterValue -> this.queryParameters.add(new QueryParameter(this.projectService, queryParameterValue, this.projectName)));
                }
                default -> throw new RuntimeException("Key: '" + key + "' provided inside queryContent is invalid");
            }
        });
    }

    private boolean isQueryColorCodeValid(String value) {
        return HexValidatorWebColor.isValid(value);
    }

    private boolean isAmountSet() {
        // todo: Maybe DO inplement if everything is set correctly? because there is a chance i.e. date is null and isAllSet is also null while still being a valid request
        return this.amountIsAll || this.amountHasLast && this.amountValue != null || this.amountValue != null;
    }

    private Integer getPositiveIntegerFromString(String string) throws Exception {
        int no;
        try {
            no = Integer.parseInt(string);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return no >= 0 ? no : null;
    }
}