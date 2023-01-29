package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.statsserver.services.KeyDataListStatic;
import org.statsserver.util.DateChecker;
import org.statsserver.services.ProjectService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class Query {

    @JsonIgnore
    private UUID id;
    private String name;
    private String description;
    private String projectName;
    private QueryResults queryResults;
    private QueryMetaData queryMetaData;
    private boolean amountIsAll = false;
    private boolean amountHasLast = false;
    private Integer amountValue = null;
    private boolean fromDateIsAll = false;
    private String fromDateValue = null;
    private boolean toDateIsAll = false;
    private String toDateValue = null;
    private ArrayList<String> fromProfiles = null;
    private ArrayList<String> returnFields = null;
    private ArrayList<QueryParameter> queryParameters = new ArrayList<>();
    public ProjectService projectService;

    public Query(String name, String description, String projectName, ArrayList<String> fromProfiles, HashMap<String, Object> queryContent) throws Exception {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.projectName = projectName;
        this.fromProfiles = fromProfiles;

        this.processQueryContent(queryContent);


        this.queryMetaData = this.generateQueryMetaData();
    }

    private QueryMetaData generateQueryMetaData() {
        return new QueryMetaData(this.fromProfiles, this.returnFields);
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

    private boolean isAmountSet() {
        // todo todo todo: Maybe DO inplement if everything is set correctly? because there is a chance i.e. date is null and isAllSet is also null while still being a valid request
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