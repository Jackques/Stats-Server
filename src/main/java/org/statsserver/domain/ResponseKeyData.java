package org.statsserver.domain;

import lombok.Getter;

import java.util.*;

public class ResponseKeyData {
    private String name;

    @Getter private String dataType;

    private Set<Object> innerListValues;

    private Set<KeyData> innerMapValues;

    private String typeInnerValues;

    ResponseKeyData(String name, String dataType, Set<Object> innerListValues, Set<KeyData> innerMapValues, String typeInnerValues){
        this.name = name;
        this.dataType = dataType;
        this.innerListValues = innerListValues;
        this.innerMapValues = innerMapValues;
        this.typeInnerValues = typeInnerValues;
    }
    public HashMap<String, Object> getResponseKeyDataAsHashMap(){
        HashMap<String, Object> responseKeyData = new HashMap<String, Object>();
        responseKeyData.put("keyName", name);
        responseKeyData.put("dataType", dataType);

        if(this.innerListValues.size() > 0){
            responseKeyData.put("innerListValues", this.innerListValues);
        }

        if(this.innerMapValues.size() > 0){
            ArrayList<HashMap<String, String>> innerMapData = new ArrayList<HashMap<String, String>>();
            this.innerMapValues.forEach((innerMapValue)->{
                HashMap<String, String> innerMapValuesMap = new HashMap<String, String>();
                innerMapValuesMap.put("keyName", innerMapValue.getName());
                innerMapValuesMap.put("dataType", innerMapValue.getValueType());
                innerMapData.add(innerMapValuesMap);
            });
            responseKeyData.put("innerMapValues", innerMapData);
        }

        if(this.innerListValues.size() > 0 || this.innerMapValues.size() > 0){
            responseKeyData.put("typeInnerValues", typeInnerValues);
        }

        return responseKeyData;

    }
}
