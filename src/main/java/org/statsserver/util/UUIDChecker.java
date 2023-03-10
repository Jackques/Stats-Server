package org.statsserver.util;

import java.util.UUID;

public class UUIDChecker {
    public static Boolean isUUIDValid(String id){
        boolean result = false;
        try{
            UUID.fromString(id);
            result = true;
        }catch(Exception e){
            throw new RuntimeException("Provided id is invalid: "+e);
        }
        return result;
    }
}
