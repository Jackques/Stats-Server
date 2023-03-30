package org.statsserver.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class QueryResultDetailsFakeDB {
    private static final String pathToDBFiles = "src/main/resources/queryDetailResults";

    public static void writeQueryResultDetailsToFile(String fileName, ArrayList<HashMap> queryResults) {
        boolean fileExists;
        if (doesFileExist(fileName)) {
            fileExists = true;
        } else {
            fileExists = createNewQueryResultDetailsFile(fileName);
        }

        if (fileExists) {
            writeToFile(fileName, queryResults);
        }
    }

    public static ArrayList<HashMap> getQueryResultDetailsFromFile(String fileName) throws IOException {
        if (doesFileExist(fileName)) {
            return readQueryResultDetailFromFile(fileName);
        }
        throw new RuntimeException("File: " + fileName + ".json not found.");
    }

    public static boolean doesFileExist(String fileName) {
        File f = new File(pathToDBFiles, fileName + ".json");
        if (f.exists() && f.isFile()) {
            return true;
        }
        return false;
    }

    private static boolean createNewQueryResultDetailsFile(String fileName) {
        File f = new File(pathToDBFiles, fileName + ".json");
        try {
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
//            System.exit(-1);
            return false;
        }
        return true;
    }

    private static ArrayList<HashMap> readQueryResultDetailFromFile(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<HashMap> fileContents;
        try {
            fileContents = mapper.readValue(new File(pathToDBFiles, fileName + ".json"), new TypeReference<ArrayList<HashMap>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileContents;
    }

    private static Boolean writeToFile(String fileName, Object fileContents) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(pathToDBFiles, fileName + ".json"), fileContents);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean removeFile(String id) {
        try {
            if (doesFileExist(id)) {
                return new File(pathToDBFiles, id + ".json").delete();
            }
            return false;
        } catch(Exception e){
            throw new RuntimeException("Unable to remove file");
        }
    }
}
