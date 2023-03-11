package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.statsserver.services.KeyDataListStatic;
import org.statsserver.util.DateChecker;
import org.statsserver.util.HexValidatorWebColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Query {

    private String amountValue;
    @JsonIgnore
    private Boolean setAmountIsAll = false;
    @JsonIgnore
    private Boolean setAmountHasLast = false;
    @JsonIgnore
    private Integer setAmountValue;
    private String fromDate;
    @JsonIgnore
    private Boolean setFromDateIsAll = false;
    @JsonIgnore
    private String fromDateValue = null;
    private String toDate;
    @JsonIgnore
    private boolean toDateIsAll = false;
    @JsonIgnore
    private String toDateValue = null;
    private String colorQuery;
    private String labelForThisQuery = "";
    private Boolean visibilityQuery = true;
    private Set<String> returnFields = new HashSet<>();
    private final ArrayList<QueryParameter> queryParameters = new ArrayList<>();

    public Query(HashMap<String, Object> queryContent, String projectName) {
        queryContent.forEach((key, value) -> {

            if (value == null) {
                throw new RuntimeException("Value provided in toDate is not valid");
            }

            switch (key) {
                case "amount" -> {
                    String amountValue = (String) value;
                    this.amountValue = amountValue;
                    if (amountValue.equals("ALL")) {
                        this.setAmountIsAll = true;
                    } else {
                        Integer amountValueInteger;
                        if (amountValue.startsWith("LAST_")) {
                            try {
                                amountValueInteger = this.getPositiveIntegerFromString(amountValue.replace("LAST_", ""));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            this.setAmountHasLast = true;
                            this.setAmountValue = amountValueInteger;

                        } else {
                            try {
                                amountValueInteger = this.getPositiveIntegerFromString(amountValue);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            this.setAmountValue = amountValueInteger;
                        }
                    }
                }
                case "fromDate" -> {
                    String fromDateValue = (String) value;
                    this.fromDate = fromDateValue;

                    if (fromDateValue.equals("ALL")) {
                        this.setFromDateIsAll = true;
                    } else {

                        if (DateChecker.isValidDate(fromDateValue)) {
                            this.fromDateValue = fromDateValue;
                        } else {
                            throw new RuntimeException("Value provided in fromDate is not valid");
                        }
                    }
                }
                case "toDate" -> {
                    String toDateValue = (String) value;
                    this.toDate = toDateValue;

                    if (toDateValue.equals("ALL")) {
                        this.toDateIsAll = true;
                    } else {

                        if (DateChecker.isValidDate(toDateValue)) {
                            this.toDateValue = toDateValue;
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
                        this.colorQuery = queryColorCode;
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
                    if (returnFieldsValue.size() != 0 && !KeyDataListStatic.doKeysExist(returnFieldsValue, projectName)) {
                        throw new RuntimeException("Value provided in returnFields is not valid");
                    }
                    this.returnFields.addAll(returnFieldsValue);
                }
                case "queryParameters" -> {
                    ArrayList<HashMap<String, Object>> queryParametersValues = (ArrayList<HashMap<String, Object>>) value;
                    if (queryParametersValues.size() == 0) {
                        throw new RuntimeException("No values found in queryParametersValues");
                    }
                    queryParametersValues.forEach(queryParameterValue -> this.queryParameters.add(new QueryParameter(queryParameterValue, projectName)));
                }
                default -> throw new RuntimeException("Key: '" + key + "' provided inside queryContent is invalid");
            }
        });
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

    private boolean isQueryColorCodeValid(String value) {
        return HexValidatorWebColor.isValid(value);
    }
}
