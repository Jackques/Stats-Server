package org.statsserver.services;
import org.statsserver.domain.KeyData;

import java.util.ArrayList;
import java.util.HashMap;
public class KeyDataListStatic {
    private static final HashMap<String, HashMap<String, KeyData>> keyDataMap = new HashMap<String, HashMap<String, KeyData>>();
    public static void addKeyDataMap(String projectName, HashMap<String, KeyData> keyDataMap){
        KeyDataListStatic.keyDataMap.put(projectName, keyDataMap);
    }
    public static HashMap<String, KeyData> getProjectKeyDataMap(String projectName){
        return KeyDataListStatic.keyDataMap.get(projectName);
    }

    public static HashMap<String, KeyData> getKeyDataMap(String keyName, String projectName){
        HashMap<String, KeyData> keyDataMap = KeyDataListStatic.keyDataMap.get(projectName);
        if(keyDataMap == null){
            return null;
        }
        return KeyDataListStatic.keyDataMap.get(keyName);
    }

    public static KeyData getKeyData(String keyName, String projectName){
        HashMap<String, KeyData> keyDataMap = KeyDataListStatic.keyDataMap.get(projectName);
        if(keyDataMap == null){
            return null;
        }
        return keyDataMap.get(keyName);
    }

    public static boolean doesKeyExist(String keyName, String projectName){
        HashMap<String, KeyData> keyDataMap = KeyDataListStatic.keyDataMap.get(projectName);
        if(keyDataMap == null){
            return false;
        }
        return keyDataMap.containsKey(keyName);
    }

    public static boolean doKeysExist(ArrayList<String> keyNames, String projectName){
        HashMap<String, KeyData> keyDataMap = KeyDataListStatic.keyDataMap.get(projectName);
        if(keyDataMap == null){
            return false;
        }
        return keyDataMap.keySet().containsAll(keyNames);
    }
}