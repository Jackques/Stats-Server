package org.statsserver.util;

import org.apache.commons.io.FilenameUtils;
import org.statsserver.enums.FileExtension;

public class FileNameChecker {

    public static FileExtension getFileExtension(String fileName){

        if (fileName.isEmpty()) {
            System.out.println("File name appears to be empty!");
            return null;
        }

        switch (getExtensionByApacheCommonLib(fileName)) {
            case "json" -> {
//                System.out.println("The file is a JSON file!");
//                return true;
                return FileExtension.JSON;
            }
            case "csv" -> {
//                System.out.println("The file is a CSV file!");
//                return true;
                return FileExtension.CSV;
            }
            default -> {
                System.out.println("Could not determine file extension!");
                return null;
            }
        }
    }

    private static String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }
}
