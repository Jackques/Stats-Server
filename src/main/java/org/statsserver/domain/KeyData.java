package org.statsserver.domain;

import static org.statsserver.util.ValueDataTypeService.getValueDataType;

import java.util.*;

public class KeyData {
    private String name;
    private Set<Object> value = new HashSet<> ();
    private String valueType; //todo: refactor to enum
    private Boolean hasInnerValues = null;
    private String typeInnervalues = null;
    // todo 1; vervangen door enum?
    // todo 2; bij hoofdobject type object is deze waarde null?
    // todo 3; is dit wel nodig? want als er een inner object is (object in string OF key-value pairs in object) dan is er toch een aparte property om dit mee uit te lezen?

    private Boolean dataComplete = false;
    private ArrayList<KeyData> innerValuesList = null; // currently functions as the list where value of Maps are stored as Keydata objects (e.g. System-no, Response-speed etc.)
    public KeyData(String name, Object value) {

        this.updateKeyData(name, value);
    }
    public ResponseKeyData getKeyDataAsResponseKeyData(){
        if(Objects.equals(this.name, "")){
            return null;
        }
        return new ResponseKeyData(this.name, this.valueType);
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
        // todo todo todo: needs inplementation
        return true;
    }


    public Set<?> getValues() {
        if(this.hasInnerValues && this.typeInnervalues == "String"){ //todo: this line is just here to test if it works, but now actual strings should be countred as strings.. not date strings!
//        if(this.hasInnerValues && this.typeInnervalues == "DateString"){
            //todo todo todo: logic requested below needs to be set when looping over data

            // set 10 as variabele number
            // if values are greater than 10, return [null], otherwise return [value1, value2, value3 etc.]
            return this.value;
        }
        return null;
    }

    public void updateInnerValuesList(ArrayList<Object> valuesList) {
        if(valuesList.size() > 0 && !getValueDataType(valuesList.get(0)).equals("Map")){
            this.value.addAll(valuesList);
        }
    }

    public void updateKeyData(String name, Object value) {

        if(value instanceof ArrayList<?>){
            this.updateInnerValuesList((ArrayList<Object>)value);
        }

        if(this.dataComplete){
            return;
        }

        this.name = (this.name == null || this.name.isEmpty()) ? name : this.name;

        this.valueType = this.valueType == null || this.valueType.equals("OTHER") ? getValueDataType(value) : this.valueType;
        this.hasInnerValues = this.hasInnerValues == null ? Objects.equals(this.valueType, "Map") || Objects.equals(this.valueType, "List") : this.hasInnerValues;

        if(this.hasInnerValues){
            innerValuesList = this.setInnerValuesList(value);

            if(Objects.equals(this.valueType, "Map")){
                this.typeInnervalues = "MapKeyValues";
            }else{
                List<?> myValue = (List<?>)value;
                this.typeInnervalues = myValue.size() > 0 ? getValueDataType(myValue.get(0)) : null;
            }
        }else{
            this.typeInnervalues = "NA"; // Not Applicable
        }

        this.dataComplete = this.setIsDataComplete();
    }

    private boolean setIsDataComplete() {
        if(this.name.isEmpty()){
            return false;
        }
        if(this.valueType == null){
            return false;
        }
        if(this.hasInnerValues == null){
            return false;
        }
        if(this.typeInnervalues == null){
            return false;
        }
        return true;
    }

}
