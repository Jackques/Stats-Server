package org.statsserver.domain;

import java.util.HashMap;

public class KeyDataMockClass {

    public static HashMap<String, HashMap<String, KeyData>> generateFitnessData(){
        HashMap fitnessData = new HashMap<>();
        fitnessData.put("No", new KeyData("No", "1"));
        fitnessData.put("Gewicht in kg", new KeyData("Gewicht in kg", 86.8));

        HashMap fitnessDataResult = new HashMap();
        fitnessDataResult.put("Fitness-stats", fitnessData);
        return fitnessData;
    }

    public static HashMap<String, HashMap<String, KeyData>> generateTHelperData(){
        HashMap THelperData = new HashMap<>();

        HashMap systemNo = new HashMap<>();
        systemNo.put("appType", "tinder");
        systemNo.put("id", "528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a");
        systemNo.put("tempId", "529313f0e8ed4f6c0e00002a");
        THelperData.put("System-no", new KeyData("System-no", systemNo));

        THelperData.put("No", new KeyData("No", 1));
        THelperData.put("City", new KeyData("City", "Tilburg"));
        THelperData.put("Last-updated", new KeyData("Last-updated", "2016-10-22T12:53:23.386Z"));

        HashMap THelperDataResult = new HashMap();
        THelperDataResult.put("T-Helper", THelperData);
        return THelperDataResult;
    }
}
