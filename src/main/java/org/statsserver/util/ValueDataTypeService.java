package org.statsserver.util;

import static org.statsserver.util.DateChecker.isValidDate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class ValueDataTypeService {
  public static String getValueDataType(Object value) {
    // todo: should refactor these data types to enums
    if (String.class.equals(value.getClass())) {
      String stringValue = (String) value;
      if (isValidDate(stringValue)) {
        //                System.out.println("DateString");
        return "DateString";
      }
      //            System.out.println("String");
      return "String";
    } else if (Integer.class.equals(value.getClass()) || Long.class.equals(value.getClass())) {
      //            System.out.println("Integer");
      return "WholeNumber";
    } else if (Double.class.equals(value.getClass()) || Float.class.equals(value.getClass())) {
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
//    System.out.println("DEBUG: OTHER");
    return "OTHER";
  }

  public static Object convertWholeNumberToDecimalNumber(Object value) {
    DecimalFormat decimalFormattedValue =
        new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
    return Double.valueOf(decimalFormattedValue.format(value));
  }
}
