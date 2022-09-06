package org.statsserver.domain;

import java.util.HashMap;
import java.util.Objects;

public class KeyData {

    private String name = "";
    private String valueType = ""; //todo: refactor to enum

    public KeyData(String name, String valueType) {
        this.name = name;
        this.valueType = valueType;
    }

    public HashMap<String, String> getKeyDataAsHashMap(){
        HashMap<String, String> hashmap = new HashMap<String, String>();
        if(!Objects.equals(this.name, "") && !Objects.equals(this.valueType, "")){
            hashmap.put(this.name, this.valueType);
            return hashmap;
        }

        return hashmap;

        // LEARNED: do not return Java object instances of my own created classes (an error is returned if you do).
        // Instead ALWAYS return a known Java object ie.e. hashmap, map, array, etc.
//        These are automatically converted into JSON when returning these values to a client (hashmap becomes JSON object, ArrayList becomes JSON array etc.)

        // When returning objects to a REST endpoint that is
    }
}
