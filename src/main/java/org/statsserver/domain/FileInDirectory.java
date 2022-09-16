package org.statsserver.domain;

import org.statsserver.enums.FileExtension;
import org.statsserver.records.Profile;
import org.statsserver.util.FileNameChecker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInDirectory implements Comparable<FileInDirectory> {
    public final String fullFileName;
    public final String fullFilePath;
    public Date formattedDateTime;
    public Long differenceInMSFromCurrentDateTime = null;
    public Long getDifferenceInMSFromCurrentDateTime() {
        return differenceInMSFromCurrentDateTime;
    }

    //todo: refactor to private properties and use lombok to get these properties?
    public Boolean isNotEmpty = false;//todo: remove this if not used?
    public Boolean hasValidDataStructure = false;//todo: remove this if not used?
    public FileExtension fileExtension;

    private final Profile associatedProfile;

    public FileInDirectory(String fullFileName, String directoryPath, Profile profile, String dateStringFromFileName) {
        this.fullFileName = fullFileName;
        this.fullFilePath = directoryPath+"\\"+fullFileName;

        this.fileExtension = FileNameChecker.getFileExtension(fullFileName);
        this.associatedProfile = profile;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy--HH-mm-ss");
        try {
            this.formattedDateTime = formatter.parse(dateStringFromFileName);
        } catch (ParseException e) {
            //todo: throw more descriptive errormessage; what if string provided is not a valid date?
            throw new RuntimeException(e);
        }

        long currentTimeInMS = new Date().getTime();
        try {
            this.differenceInMSFromCurrentDateTime = Math.abs(currentTimeInMS - this.formattedDateTime.getTime());
            if(this.differenceInMSFromCurrentDateTime < 0){
                // if file is in the future (thus is negative amount of seconds removed from current dateTime), throw error
                throw new Exception("DateTime mentioned in fileName is a future date of current date. Future dates are not allowed.");
            }
        } catch(Exception e){
            System.out.println("DateTime mentioned in fileName is a future date of current date. Future dates are not allowed.");
        }

//        System.out.println(differenceInMSFromCurrentDateTime);

        //todo: experiment to check if sorting works, might want to move this to a unit-test
//        long random_int = (long)Math.floor(Math.random()*(100-50+1)+50);
//        this.differenceInMSFromCurrentDateTime = random_int;
//        System.out.println(this.differenceInMSFromCurrentDateTime);
    }

    @Override
    public int compareTo(FileInDirectory fileInDirectory) {
        return getDifferenceInMSFromCurrentDateTime().compareTo(fileInDirectory.getDifferenceInMSFromCurrentDateTime());
    }

    public Boolean hasValidFileExtension(){
        return this.fileExtension != null && this.fileExtension != FileExtension.OTHER;
    }
}
