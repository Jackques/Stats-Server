package org.statsserver.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.NumberUtils;
import org.statsserver.domain.FileInDirectory;
import org.statsserver.domain.KeyDataList;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.statsserver.services.NumberConverterFromString.getConvertedNumberFromString;

public class FileReader {
    public static LinkedHashMap<Integer, HashMap<String, Object>> readFile(FileInDirectory fileInDirectory, KeyDataList dataTypesList){

        File file = new File(fileInDirectory.fullFilePath);
        LinkedHashMap<Integer, HashMap<String, Object>> fileContents = null;

        switch (fileInDirectory.fileExtension) {
            case JSON -> {
                fileContents = mapFileContentsForJson(file, dataTypesList);
                //System.out.println("DEBUG");
            }
            case CSV -> {
                fileContents = mapFileContentsForCsv(file, dataTypesList);
                //System.out.println("DEBUG");
            }
            case OTHER -> {
                System.out.println("DEBUG: OTHER");
                //todo: throw error
            }
        }

        return fileContents;
    }

    private static LinkedHashMap<Integer, HashMap<String, Object>> mapFileContentsForJson(File json, KeyDataList dataTypesList){
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<Integer, HashMap<String, Object>> results = new LinkedHashMap<>();

        try {
            List<LinkedHashMap<String, Object>> fileContents = mapper.readValue(json, new TypeReference<List<LinkedHashMap<String, Object>>>() {});
            System.out.println("DEBUG");

            if(fileContents.size() == 0){
                //todo: throw error
                throw new RuntimeException("File contents may not be emtpy");
            }

            AtomicInteger no = new AtomicInteger();
            fileContents.forEach((fileContentObject) -> {
                HashMap keyValuesContentObject = new HashMap();
                Set<String> keysContentObject = fileContentObject.keySet();

                if(keysContentObject.size() == 0){
                    //todo: throw error
                    throw new RuntimeException("object may not be emtpy");
                }

                for(String keyName : keysContentObject){
                    if(!hasSubValuesAllowedStructure(fileContentObject.get(keyName))){
                        //todo: throw error
                        throw new RuntimeException("subobject does not have approved structure");
                    }
                    try {
                        dataTypesList.addKeyAndDataType(keyName, fileContentObject.get(keyName));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    keyValuesContentObject.put(keyName, fileContentObject.get(keyName));
                }

                results.put(no.get(), keyValuesContentObject);
                no.getAndIncrement();
            });

        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file!");
            throw new RuntimeException(e);
        }

        return results;
    }

    private static LinkedHashMap<Integer, HashMap<String, Object>> mapFileContentsForCsv(File csv, KeyDataList dataTypesList){
        CSVParser csvParser;
        LinkedHashMap<Integer, HashMap<String, Object>> results = new LinkedHashMap<>();

        try {
            Reader reader = new java.io.FileReader(csv);
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<String> headerNames = csvParser.getHeaderNames();
            List<CSVRecord> records = csvParser.getRecords();
            if(headerNames.size() == 0 || records.size() == 0){
                //todo: throw error
            }

            int no = 0;

            for (CSVRecord record : records) {

                HashMap data = new HashMap();
                for(String headerName : headerNames){
                    data.put(headerName, record.get(headerName));
                    dataTypesList.addKeyAndDataType(headerName, getConvertedNumberFromString(record.get(headerName)));
                }
                results.put(no, data);
                no++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    private static Boolean hasSubValuesAllowedStructure(Object subValue){
        //TODO [COMPLETED, NEEDS TO BE TESTED] 4. ensure object values (see 3) have no underlying objects OR array's

        //TODO: What if an array/list has different types of contents store in it? e.g; [Object, Object, Object, String].
        // Allow this or no? How would I even check what the 'valid structure of this would be?'

        if(isValueMap(subValue)){

            Map<?,?> aMapValue = ((Map<?,?>) subValue);

//            System.out.println("this is an object/map");
            if(aMapValue.size() == 0){
                return true;
            }

            for(Map.Entry<?, ?> aMapEntry : aMapValue.entrySet()){
//                System.out.println("mapped subsubvalue: "+aMapEntry.getValue());

                if(isValueMap(aMapEntry.getValue()) || isValueList(aMapEntry.getValue())){
                    // object value may not be of type list or object
                    return false;
                }
            }

            return true;
        }

        if(isValueList(subValue)){
//            System.out.println("this is an array/list");
            Object[] listValue = ((List<?>) subValue).toArray();
            if(listValue.length == 0){
                return true;
            }

            for(Object listElementValue : listValue){
//                System.out.println("This value of the array is: "+listElementValue);

                if(isValueList(listElementValue)){
                    return false;
                }
                if(isValueMap(listElementValue)){
                    Map<?,?> listElementChildValue = (Map<?, ?>) listElementValue;
                    if(listElementChildValue.size() == 0){
                        return true;
                    }
                    for(Map.Entry<?, ?> listElementChildValueValue : listElementChildValue.entrySet()){
                        if(isValueMap(listElementChildValueValue.getValue()) || isValueList(listElementChildValueValue.getValue())){
                            // list element child may not be of type object or list
                            return false;
                        }
                    }


                }
            }
        }
        //TODO [COMPLETED, NEEDS TESTING & REFACTOR] 5. ensure array values cannot have underlying array's,
        // ensure array object values have no underlying objects OR underlying array's

        return true;
    }
    private static Boolean isValueMap(Object subValue){
        return subValue instanceof Map<?,?>;
    }

    private static Boolean isValueList(Object subValue){
        return subValue instanceof List;
    }
}
