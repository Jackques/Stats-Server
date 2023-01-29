package org.statsserver.domain;

import lombok.Getter;
import org.statsserver.util.ValueDataTypeService;

import java.util.*;

public class KeyDataList {
    private Set<String> keysList = new LinkedHashSet<String>();
    @Getter
    private HashMap<String, KeyData> keyDataList = new HashMap<String, KeyData>();

    public KeyDataList() {

    }
    public void addKeyAndDataType(String keyName, Object value) throws Exception {
        if(!keyName.isEmpty() && value != null){
            if(!this.keysList.contains(keyName)){
                this.keysList.add(keyName);
                this.keyDataList.put(keyName, new KeyData(keyName, value));
            }else{
                this.keyDataList.get(keyName).updateKeyData(keyName, value);
            }

            if(!this.isValueSameKeyEqualDataType(keyName, ValueDataTypeService.getValueDataType(value))){
                throw new Exception("Key: "+keyName+" already exists in dataList with type: "+this.keyDataList.get(keyName).getKeyDataAsResponseKeyData().getDataType()+" but new entry type: "+value+" creates conflict");
            }
        }
    }

    public ArrayList<HashMap<String, Object>> getAllKeysAndDataTypes(){
        ArrayList<HashMap<String, Object>> keyAndDataTypeList = new ArrayList<>();
        this.keyDataList.forEach((key, value) -> {
            keyAndDataTypeList.add(value.getKeyDataAsResponseKeyData().getResponseKeyDataAsHashMap());
        });
        return keyAndDataTypeList;
    }
    public boolean getKeyExists(String keyName) {
        return this.keysList.contains(keyName);
    }
    private boolean isValueSameKeyEqualDataType(String keyName, Object dataTypeOfValue) {
        //get current entry type
        // is equal to new entry value type, return true
        // if not, return false
        boolean result = this.keyDataList.get(keyName).getKeyDataAsResponseKeyData().getDataType().equals(dataTypeOfValue);
        return result;
    }

    public KeyData getKey(String keyName) {
        return this.keyDataList.get(keyName);
    }
}
