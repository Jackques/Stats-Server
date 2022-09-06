package org.statsserver.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.statsserver.domain.FileInDirectory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class FileReader {

    public static void readFile(FileInDirectory fileInDirectory){

        File file = new File(fileInDirectory.fullFilePath);
        LinkedHashMap<Integer, HashMap<String, Object>> fileContents = null;

        switch(fileInDirectory.fileExtension){
            case JSON: {
                fileContents = mapFileContentsForJson(file);
                System.out.println("DEBUG");
            }
            case CSV: {
                fileContents = mapFileContentsForCsv(file);
                System.out.println("DEBUG");
            }
            case OTHER: {
                //todo: throw error
//                return null; //TODO: NEEDS WORK; DO I NOT RETURN ANYTHING HERE?
            }

            System.out.println("DEBUG");
        }

    }

    private static LinkedHashMap<Integer, HashMap<String, Object>> mapFileContentsForJson(File json){
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<Integer, HashMap<String, Object>> results = new LinkedHashMap<>();
        //TODO: Make sure to enforce that provided file is in a desired structure; Array containing objects. Throw error is this is not the case!
//                List<LinkedHashMap<String, Object>> listOfMaps = null;
        try {
            List<LinkedHashMap<String, Object>> fileContents = mapper.readValue(json, new TypeReference<List<LinkedHashMap<String, Object>>>() {});
            System.out.println("DEBUG");

            if(fileContents.size() == 0){
                //todo: throw error
            }

            AtomicInteger no = new AtomicInteger();
            fileContents.forEach((fileContentObject) -> {
                HashMap data = new HashMap();

                for(String keyName : fileContentObject.keySet()){
                    data.put(keyName, fileContentObject.get(keyName));
                }

                results.put(no.get(), data);
                no.getAndIncrement();
            });

        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file!");
            throw new RuntimeException(e);
        }

        return results;
    }

    private static LinkedHashMap<Integer, HashMap<String, Object>> mapFileContentsForCsv(File csv){
        CSVParser csvParser;
        LinkedHashMap<Integer, HashMap<String, Object>> results = new LinkedHashMap<>();

        try {
            Reader reader = new java.io.FileReader(csv);
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<String> headerNames = csvParser.getHeaderNames();
            if(headerNames.size() == 0){
                //todo: throw error
            }

            int no = 0;

            for (CSVRecord record : csvParser) {

                HashMap data = new HashMap();
                for(String headerName : headerNames){
                    data.put(headerName, record.get(headerName));
                }
                results.put(no, data);
                no++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return results;
    }
}
