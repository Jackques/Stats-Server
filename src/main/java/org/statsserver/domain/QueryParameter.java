package org.statsserver.domain;

import org.statsserver.services.KeyDataListStatic;
import org.statsserver.services.ProjectService;
import org.statsserver.util.ValueDataTypeService;

import java.util.HashMap;

public class QueryParameter {
    private String key;
    private String operator;
    private Object value;
    private String projectName;
    private KeyData keyData;

    private ProjectService projectService;
    public QueryParameter(ProjectService projectService, HashMap<String, Object> queryParameterMap, String projectName) {
        this.projectService = projectService;
        this.key = (String) queryParameterMap.get("whereKey");
        this.operator = (String) queryParameterMap.get("operator");
        this.value = (Object) queryParameterMap.get("value");

        //todo todo todo: what about keys received i.e. response-speed?
        // e.g.:
        /*
        * {
        *   key: Response-speed
        *   innerMapValue: "Wholenumber"
        *   operator: "GREATER_THAN"
        *   value: 5000
        * }
        * */
        // solution: these values will have a dot in the name
        // TODO TODO TODO: Also filter & throw error of any non alphanumeric character in name EXCEPT dashes AND dots (dashes are allowed, dots are to indicate a map)
        // TODO TODO TODO:
        // Put key in this.key, and put subkey in this.subkey (create subKey)
        // WAIT.. why do all this? why not just put an extra field called 'whereSubKey' inside the request?
        // it PREVENTS some extra logic; i.e. having to do a regex on the key,
        // add logic; if received subkey, check for exitence of normal key first. If subkey is present also check for correct operator & value of subkey

        // TODO TODO TODO:
        // check if numbers, decimalnumbers, lists, boolean etc are NOT converted to strings when received! Otherwise everythingggg will be considered string!

        // check if fromDate & toDate are valid string in Query class!

        this.projectName = projectName;
        if(!this.isKeyValid()){
            throw new RuntimeException("Data provided in: '"+this.key+"' is invalid");
        }

        this.keyData = KeyDataListStatic.getKeyData(this.key, this.projectName);

        if(!this.isOperatorValid()){
            throw new RuntimeException("Operator: '"+this.operator+"' provided in: '"+this.key+"' is invalid");
        }

        if(!this.valueMatchesKeyValue()){
            throw new RuntimeException("Value: '"+this.operator+"' provided in: '"+this.key+"' is invalid. Expected value of type: "+this.keyData.getValueType());
        }

    }
    private boolean isKeyValid() {
        return KeyDataListStatic.doesKeyExist(this.key, this.projectName);
    }

    private boolean isOperatorValid() {
        switch (this.keyData.getValueType()) {
            case "String" -> {
                return this.operator.equals("EQUALS") || this.operator.equals("CONTAINS") || this.operator.equals("EXCLUDES");
            }
            case "DateString" -> {
                return this.operator.equals("BEFORE_DATE") || this.operator.equals("ON_DATE") || this.operator.equals("AFTER_DATE");
            }
            case "WholeNumber", "DecimalNumber" -> {
                return this.operator.equals("LESS_THAN") || this.operator.equals("LESS_THAN_OR_EQUAL_TO") || this.operator.equals("EQUALS") || this.operator.equals("GREATER_THAN") || this.operator.equals("GREATER_THAN_OR_EQUAL_TO");
            }
            case "Boolean" -> {
                return this.operator.equals("EQUALS_TRUE") || this.operator.equals("EQUALS_FALSE");
            }
            case "List" -> {
                return this.operator.equals("CONTAINS") || this.operator.equals("EXCLUDES");
            }
            default -> {
                return false;
            }
        }
    }

    private boolean valueMatchesKeyValue() {
        String valueTypeReceivedData = ValueDataTypeService.getValueDataType(this.value);
        return this.keyData.getValueType().equals(valueTypeReceivedData);
    }

}