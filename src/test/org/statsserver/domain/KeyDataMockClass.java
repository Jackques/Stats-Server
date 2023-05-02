package org.statsserver.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
        THelperData.put("Age", new KeyData("Age", 30.023));
        THelperData.put("Seems-toppick", new KeyData("Seems-toppick", true));

        ArrayList<String> interests = new ArrayList<String>();
        interests.add("Basicfit");
        interests.add("Bioscoop");
        interests.add("Bordspellen");
        interests.add("Borrelen");
        interests.add("Boksen");
        interests.add("Beauty");
        interests.add("Lezen");
        interests.add("Eten");
        interests.add("Disney");
        interests.add("Gamer");
        interests.add("Instagram");
        interests.add("Influencer");
        interests.add("Festivals");
        interests.add("Fotografie");
        interests.add("Hardlopen");
        interests.add("Natuur");
        interests.add("Koken");
        interests.add("Voetbal");
        interests.add("Uitgaan");
        interests.add("Series");
        interests.add("Spiritualiteit");
        interests.add("Sporten");
        interests.add("Schrijven");
        interests.add("Tv kijken");
        interests.add("Zwemmen");
        THelperData.put("Interests", new KeyData("Interests", interests));

        HashMap THelperDataResult = new HashMap();
        THelperDataResult.put("T-Helper", THelperData);
        return THelperDataResult;
    }
}
