package org.statsserver.domain;

import lombok.Getter;

import static org.statsserver.util.ValueDataTypeService.getValueDataType;

import java.util.*;

public class KeyData {
    @Getter
    private String name;
    private Set<Object> innerValuesList = new HashSet<>();
    @Getter
    private String valueType; //todo: refactor to enum
    private Boolean hasInnerValues = null;
    private String typeInnervalues = null;
    // todo 1; vervangen door enum?
    // todo 2; bij hoofdobject type object is deze waarde null?
    // todo 3; is dit wel nodig? want als er een inner object is (object in string OF key-value pairs in object) dan is er toch een aparte property om dit mee uit te lezen?

    private Boolean dataComplete = false;
    private Set<KeyData> innerValuesMap = new HashSet<>(); // currently functions as the list where value of Maps are stored as Keydata objects (e.g. System-no, Response-speed etc.)

    public KeyData(String name, Object value) {

        this.updateKeyData(name, value);
    }

    public ResponseKeyData getKeyDataAsResponseKeyData() {
        if (Objects.equals(this.name, "")) {
            return null;
        }
        return new ResponseKeyData(this.name, this.valueType, this.innerValuesList, this.innerValuesMap, this.typeInnervalues);
    }

    private void setMapOrListValues(Object value) {
        // TODO: Now i'm getting a pretty decent list of the inner contents of a map/list, but what if..
        //  there are multiple maps/lists wih different type of values?
        // will my app even read multiple instances of this map/list from different result objects? to determine if the contents are indeed different or not?
        // > no it will not, it will read the first occurance, add it to the list and leave it at that
        // if my app does not read multiple.. then how will it determine what kind off OTHER values there are present? and OTHER key values? and how will it even read the values at all?
        // > it wont besides the first occurance
        // > even if i want my app to read multiple/all the contents of all the result instances, it will be very preformance heavy. Am i sure i want to do so? Or simply a subset? (a.k.a. only when list contains non-objects i.e. list of strings, list of numbers etc.)

        if (this.valueType.equals("Map")) {
            HashMap<String, Object> mapValues = (HashMap<String, Object>) value;
            mapValues.forEach((key, innerValue) -> {
                this.innerValuesMap.add(new KeyData(key, innerValue));
            });
        }
        if (this.valueType.equals("List") && this.typeInnervalues == "Map") {
            ArrayList<HashMap<String, ?>> listValues = (ArrayList<HashMap<String, ?>>) value;
            if (listValues.size() > 0) {
                if (this.isValueSameTypeOfExistingValuesInList(this.innerValuesMap, listValues)) {
                    HashMap<String, ?> firstListObject = listValues.get(0);
                    firstListObject.forEach((mapPropertyName, mapPropertyValue) -> this.innerValuesMap.add(new KeyData(mapPropertyName, mapPropertyValue)));
                }
            }
        }
    }

    private boolean isValueSameTypeOfExistingValuesInList(Object currentListValues, ArrayList<?> newListValues) {
        // todo todo todo: needs inplementation
        // if current values and new values are of same type, is true (e.g. currently maps/objects are in currentList, newList also has maps/objects)
        // if current values are of different type than new values, return false (and somewhere should throw error)?
        return true;
    }


    public Set<?> getListValues() {
        if (this.hasInnerValues && this.valueType == "List" && this.typeInnervalues != "Map") {
            //todo: this line is just here to test if it works, but what if a request is done for a map? or the list would ever contain numbers?
            return this.innerValuesList;
        }
        return null;
    }

    public void updateInnerValuesList(ArrayList<Object> valuesList) {
        if (valuesList.size() > 0 && !getValueDataType(valuesList.get(0)).equals("Map")) {
            if (this.isValueSameTypeOfExistingValuesInList(this.innerValuesList, valuesList)) {
                this.innerValuesList.addAll(valuesList);
            }
        }
    }

    public void updateKeyData(String name, Object value) {

        if (value instanceof ArrayList<?>) {
            this.updateInnerValuesList((ArrayList<Object>) value);
        }

        if (this.dataComplete) {
            return;
        }

        this.name = (this.name == null || this.name.isEmpty()) ? name : this.name;

        this.valueType = this.valueType == null || this.valueType.equals("OTHER") ? getValueDataType(value) : this.valueType;
        this.hasInnerValues = this.hasInnerValues == null ? Objects.equals(this.valueType, "Map") || Objects.equals(this.valueType, "List") : this.hasInnerValues;

        if (this.hasInnerValues) {

            if (Objects.equals(this.valueType, "Map")) {
                this.typeInnervalues = "MapKeyValues";
            } else {
                List<?> myValue = (List<?>) value;
                this.typeInnervalues = myValue.size() > 0 ? getValueDataType(myValue.get(0)) : null;
            }

            this.setMapOrListValues(value);
        } else {
            this.typeInnervalues = "NA"; // Not Applicable
        }

        this.dataComplete = this.setIsDataComplete();
    }

    private boolean setIsDataComplete() {
        if (this.name.isEmpty()) {
            // name cannot be empty
            return false;
        }
        if (this.valueType == null) {
            // valueType may not be empty
            return false;
        }
        if (this.hasInnerValues == null) {
            // hasInnerValues may not be null, must be either true or false
            return false;
        }
        if (this.typeInnervalues == null) {
            // typeInnerValues may not be empty
            return false;
        }
        if (this.typeInnervalues.equals("Map") && innerValuesMap.size() == 0) {
            // if typeInnerValues is of type Map, innerValuesMap may not be empty
            return false;
        }
        return true;
    }

}
