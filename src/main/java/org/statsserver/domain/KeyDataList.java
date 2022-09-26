package org.statsserver.domain;

import static org.statsserver.util.ValueDataTypeService.getValueDataType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

public class KeyDataList {
    private Set<String> keysList = new LinkedHashSet<String>();
    private HashMap<String, KeyData> keyDataList = new HashMap<String, KeyData>();

    public KeyDataList() {

    }
    public void addKeyAndDataType(String keyName, Object value) throws Exception {
        if(!keyName.isEmpty() && value != null){
            if(!this.keysList.contains(keyName)){
                this.keysList.add(keyName);
                this.keyDataList.put(keyName, new KeyData(keyName, value));
            }
            if(!this.isValueSameKeyEqualDataType(keyName, getValueDataType(value))){
                //todo: throw exception
                throw new Exception("Key: "+keyName+" already exists in dataList with type: "+this.keyDataList.get(keyName).getKeyDataAsHashMap().get(keyName)+" but new entry type: "+value+" creates conflict");
            }
        }
    }

    private boolean isValueSameKeyEqualDataType(String keyName, Object dataTypeOfValue) {
        //get current entry type
        // is equal to new entry value type, return true
        // if not, return false
        boolean result = this.keyDataList.get(keyName).getKeyDataAsHashMap().get(keyName).equals(dataTypeOfValue);
        return result;
    }
}
