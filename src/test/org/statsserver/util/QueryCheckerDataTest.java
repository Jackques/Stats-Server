package org.statsserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
public class QueryCheckerDataTest {

    static ObjectMapper mapper = new ObjectMapper();

    public static LinkedHashMap<String, Object> getTHelperMockDataSet(String dummyProjectName, String alternativeProfileDataPath) throws JsonProcessingException {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        LinkedHashMap<Integer, Object> resultMap = new LinkedHashMap<Integer, Object>();

        List<HashMap> jsonDataResultList;
        
        String dataPath = alternativeProfileDataPath == null ? "./src/test/org/statsserver/util/Profile_JackUpdatedKopie_15-09-2022--22-53-39.json" : alternativeProfileDataPath;

        try {
            jsonDataResultList = mapper.readValue(
                    new File(dataPath),
                    new TypeReference<ArrayList<HashMap>>() {
                    });

        } catch (Exception e) {
            System.out.println("Could not read data from mock JSON file. Please check if the mock test file is correct");
            throw new RuntimeException(e);
        }

        if (jsonDataResultList == null || jsonDataResultList.size() == 0) {
            throw new RuntimeException("JSON Mock data is null or empty. Please check the mock data");
        }

        for (int i = 0; i < jsonDataResultList.size(); i++) {
            resultMap.put(i, jsonDataResultList.get(i));
        }

        result.put(dummyProjectName, resultMap);
        return result;
    }
}
