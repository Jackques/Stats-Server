package org.statsserver.domain;

import static org.statsserver.util.ValueDataTypeService.getValueDataType;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class KeyData {
    private String name = "";
    private Object value = "";
    private String valueType; //todo: refactor to enum
    private Boolean hasInnerValues;
    private String typeInnervalues = "DateString";
    // todo 1; vervangen door enum?
    // todo 2; bij hoofdobject type object is deze waarde null?
    // todo 3; is dit wel nodig? want als er een inner object is (object in string OF key-value pairs in object) dan is er toch een aparte property om dit mee uit te lezen?

    private ArrayList<KeyData> innerValuesList = null;


    public KeyData(String name, Object value) {
        this.name = name;
        this.value = value;

        this.valueType = getValueDataType(value);
        this.hasInnerValues = Objects.equals(this.valueType, "Map") || Objects.equals(this.valueType, "List");

        if(this.hasInnerValues){
            innerValuesList = this.setInnerValuesList(value);

            if(Objects.equals(this.valueType, "Map")){
                this.typeInnervalues = "MapKeyValues";
            }
        }
        //todo; use normal array since i already know the amount of values and type of value this array will get?
        // e.g. int a[]=new int[5];
    }

    public HashMap<String, String> getKeyDataAsHashMap(){
        HashMap<String, String> hashmap = new HashMap<String, String>();
        if(!Objects.equals(this.name, "") && !Objects.equals(this.value, "")){
            hashmap.put(this.name, this.valueType);
            return hashmap;
        }

        return hashmap;

        // LEARNED: do not return Java object instances of my own created classes (an error is returned if you do).
        // Instead ALWAYS return a known Java object ie.e. hashmap, map, array, etc.
//        These are automatically converted into JSON when returning these values to a client (hashmap becomes JSON object, ArrayList becomes JSON array etc.)

        // When returning objects to a REST endpoint that is
    }

    private ArrayList<KeyData> setInnerValuesList(Object value){
        ArrayList<KeyData> list = new ArrayList<KeyData>();
        // TODO: Now i'm getting a pretty decent list of the inner contents of a map/list, but what if..
        //  there are multiple maps/lists wih different type of values?
        // will my app even read multiple instances of this map/list from different result objects? to determine if the contents are indeed different or not?
        // > no it will not, it will read the first occurance, add it to the list and leave it at that
        // if my app does not read multiple.. then how will it determine what kind off OTHER values there are present? and OTHER key values? and how will it even read the values at all?
        // > it wont besides the first occurance
        // > even if i want my app to read multiple/all the contents of all the result instances, it will be very preformance heavy. Am i sure i want to do so? Or simply a subset? (a.k.a. only when list contains non-objects i.e. list of strings, list of numbers etc.)

        if(this.valueType.equals("Map")){
            HashMap<String, Object> mapValues = (HashMap<String, Object>) value;
            mapValues.forEach((key, innerValue) -> {
                list.add(new KeyData(key, innerValue));
            });
        }
        if(this.valueType.equals("List")){
            ArrayList<Object> listValues = (ArrayList<Object>) value;
            listValues.forEach((listValue) -> {
                if(!this.isValueSameTypeOfExistingValuesInList(listValue, listValues)){
                    //todo: throw error
                }
                list.add(new KeyData("", listValue));
            });
        }
        return list;
    }

    private boolean isValueSameTypeOfExistingValuesInList(Object listValue, ArrayList<Object> listValues) {
        //todo: needs inplementation
        return true;
    }


}
