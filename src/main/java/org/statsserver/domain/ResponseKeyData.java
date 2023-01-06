package org.statsserver.domain;

import lombok.Getter;

import java.util.HashMap;
public class ResponseKeyData {
    private String name;

    @Getter private String dataType;

    ResponseKeyData(String name, String dataType){
        this.name = name;
        this.dataType = dataType;
    }

    public HashMap<String, String> getResponseKeyDataAsHashMap(){
        HashMap<String, String> responseKeyData = new HashMap<String, String>();
        responseKeyData.put("keyName", name);
        responseKeyData.put("dataType", dataType);
        return responseKeyData;
    }
}
