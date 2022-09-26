package org.statsserver.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValueDataTypeService {
  public static String getValueDataType(Object value){
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

  private static boolean isValidDate(String inDate) {
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
