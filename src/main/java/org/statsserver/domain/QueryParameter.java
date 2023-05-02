package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.statsserver.services.KeyDataListStatic;
import org.statsserver.services.ProjectService;
import org.statsserver.util.ValueDataTypeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@NoArgsConstructor
public class QueryParameter {
    private String key;
    private String subKey;
    private String operator;
    private Object value;
    @JsonIgnore private String projectName;
    @JsonIgnore private KeyData keyData;
    @JsonIgnore private KeyData keySubData;
    @JsonIgnore
    private ProjectService projectService;
    public QueryParameter(HashMap<String, Object> queryParameterMap, String projectName) {
        this.key = (String) queryParameterMap.get("key");
        this.subKey = (String) queryParameterMap.get("subKey");
        this.operator = (String) queryParameterMap.get("operator");
        this.value = (Object) queryParameterMap.get("value");

        this.projectName = projectName;
        if(!this.isKeyValid()){
            throw new RuntimeException("Data provided in: '"+this.key+"' is invalid");
        }
        this.keyData = KeyDataListStatic.getKeyData(this.key, this.projectName);
        this.keySubData = getSubKeyData();

        KeyData providedkeyData = this.keySubData != null ? this.keySubData : this.keyData;

        if(!this.isOperatorValid(providedkeyData)){
            throw new RuntimeException("Operator: '"+this.operator+"' provided in: '"+this.key+"' is invalid");
        }

        if(!this.valueMatchesKeyValue(providedkeyData)){
            String requiredValueType = this.keyData.getValueType();
            if(this.keyData.getValueType().equals("List")){
                requiredValueType = this.keyData.getTypeInnervalues();
            }
            throw new RuntimeException("Value: '"+this.value+"' provided in: '"+this.key+"' is invalid, with operator: "+this.operator+". Expected value of type: "+requiredValueType);
        }

    }
    private boolean isKeyValid() {
        return KeyDataListStatic.doesKeyExist(this.key, this.projectName);
    }

    private boolean isOperatorValid(KeyData providedkeyData) {
        switch (providedkeyData.getValueType()) {
            case "String" -> {
                return this.operator.equals("EQUALS") || this.operator.equals("CONTAINS") || this.operator.equals("EXCLUDES");
            }
            case "DateString" -> {
                return this.operator.equals("BEFORE_DATE") || this.operator.equals("AFTER_DATE");
            }
            case "WholeNumber", "DecimalNumber" -> {
                return this.operator.equals("LESS_THAN") || this.operator.equals("LESS_THAN_OR_EQUAL_TO") || this.operator.equals("EQUALS") || this.operator.equals("GREATER_THAN") || this.operator.equals("GREATER_THAN_OR_EQUAL_TO");
            }
            case "Boolean" -> {
                return this.operator.equals("EQUALS") || this.operator.equals("NOT_EQUALS");
            }
            case "List" -> {
                return this.operator.equals("CONTAINS") || this.operator.equals("EXCLUDES");
            }
            case "Map" -> {
                return false; // todo: don't know what to do with this yet
            }
            default -> {
                return false;
            }
        }
    }

    private boolean valueMatchesKeyValue(KeyData providedkeyData) {
        String valueTypeReceivedData = ValueDataTypeService.getValueDataType(this.value);
        if(providedkeyData.getValueType().equals("List") && valueTypeReceivedData.equals("List")){
            ArrayList<?> receivedList = (ArrayList<?>)this.value;

            if(receivedList.size() == 0){
                throw new RuntimeException("Provided list value may not be empty: "+this.value);
            }

            if(!isReceivedListValuesSameValueType(receivedList)){
                throw new RuntimeException("Not all values in the provided list are of the same type: "+this.value);
            }

            String valueTypeKeyData = providedkeyData.getTypeInnervalues();
            String valueTypeReceivedListValue = ValueDataTypeService.getValueDataType(((ArrayList<?>) this.value).get(0));
            return valueTypeKeyData.equals(valueTypeReceivedListValue);
        }
        return providedkeyData.getValueType().equals(valueTypeReceivedData);
    }

    private boolean isReceivedListValuesSameValueType(ArrayList<?> receivedList) {
        String firstItemValueType = ValueDataTypeService.getValueDataType(receivedList.get(0));
        AtomicReference<Boolean> isAllValuesSameValue = new AtomicReference<>(true);
        receivedList.forEach(value -> {
            if(!ValueDataTypeService.getValueDataType(value).equals(firstItemValueType)){
                isAllValuesSameValue.set(false);
            }
        });
        return isAllValuesSameValue.get();
    }

    private KeyData getSubKeyData() {
        if(this.subKey == null){
            return null;
        }
        try{
            return this.keyData.getKeyDataFromInnerMap(this.subKey).orElseThrow();
        }catch(Exception e){
            throw new RuntimeException("No KeyData with name: '"+this.subKey+"' found inside KeyData '"+this+"', check error: "+e.getMessage());
        }
    }

}