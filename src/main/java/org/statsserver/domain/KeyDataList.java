package org.statsserver.domain;

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
            String dataTypeOfValue = getValueDataType(value);
            if(!this.keysList.contains(keyName)){
                this.keysList.add(keyName);
                this.keyDataList.put(keyName, new KeyData(keyName, dataTypeOfValue));
            }
            if(!this.isValueSameKeyEqualDataType(keyName, dataTypeOfValue)){
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

    private String getValueDataType(Object value){
        if(String.class.equals(value.getClass())){
            String stringValue = (String) value;
            if(isValidDate(stringValue)){
//                System.out.println("DateString");
                return "DateString";
            }
//            System.out.println("String");
            return "String";
        } else if (Integer.class.equals(value.getClass()) || Long.class.equals(value.getClass())) {
//            System.out.println("Integer");
            return "WholeNumber";
        } else if (Double.class.equals(value.getClass())) {
//            System.out.println("Double");
            return "DecimalNumber";
        } else if (Boolean.class.equals(value.getClass())) {
//            System.out.println("Boolean");
            return "Boolean";
        } else if (ArrayList.class.equals(value.getClass())) {
//            System.out.println("List");
            return "List";
        } else if (Map.class.equals(value.getClass()) || LinkedHashMap.class.equals(value.getClass())) {
//            System.out.println("Map");
            return "Map";
        }
        System.out.println("DEBUG: OTHER");
        return "OTHER";
    }

    public static boolean isValidDate(String inDate) {
        //todo: prettify this code
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dateFormat.setLenient(true);
            dateFormat.parse(inDate);
            return true;
        } catch(Exception e){ }

        try {
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(inDate);
            Instant i = Instant.from(ta);
            Date d = Date.from(i);
            return true;
        } catch (Exception pe) {
            return false;
        }
    }
}
