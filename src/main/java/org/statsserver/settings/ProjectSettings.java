package org.statsserver.settings;

import org.statsserver.domain.FileInDirectory;
import org.statsserver.domain.ProjectSetting;
import org.statsserver.records.Profile;
import org.statsserver.services.FileReader;
import org.statsserver.util.FileNameChecker;
import org.statsserver.util.FormattedDateMatcher;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ProjectSettings {
    //todo: This class should probably be refactored & moved into a seperate resource-like file (i.e. a .yaml, .xml or .json file in the resources folder)

    public ArrayList<ProjectSetting> projectSettings = new ArrayList<ProjectSetting>();

    public ProjectSettings() {

    }

    public boolean addProject(ProjectSetting projectSetting){
        if(!this.isFilePathsValid(projectSetting)){
            //todo: throw exception?
            return false;
        }

        HashMap<String, FileInDirectory> latestFiles = this.getLatestFilePathsFromDirectories(projectSetting.projectFilesPaths);
        if(Objects.isNull(latestFiles) || latestFiles.size() == 0){
            //todo: throw error; could not retrieve latest file from directory path
            return false;
        }
        latestFiles.forEach((key, value) -> {
            FileReader.readFile(value);
        });


        //TODO: MAYBE CHECKING LATESTFILES MAY NOT EVEN BE NECESSARY ANYMORE, SINCE IF I CAN LOAD THEM.. THEYVE BEEN CHECKED?
            // * properties; contains valid data (not empty) & structure
        if(!this.fileContainsValidStructure()){
            //todo: throw exception?
            return false;
        }

        // inplement other logic for importing projects

        this.projectSettings.add(projectSetting);
        return true;
    }

    private boolean isFilePathsValid(ProjectSetting projectSetting) {
        LinkedList<Boolean> projectFilesPathExistsResults = new LinkedList<>();

        for (int i = 0; i < projectSetting.projectFilesPaths.size(); i++) {
            Profile currentProfile = projectSetting.projectFilesPaths.get(0);
            String currentProjectFilePath = currentProfile.directoryPath();
//            System.out.println("NOW CHECKING FILE PATH NO: "+i+" PATH: "+currentProjectFilePath);

            if(Files.isDirectory(Path.of(currentProjectFilePath))){
                projectFilesPathExistsResults.add(true);
//                System.out.println("FILE PATH IS VALID, TRUE BEING ADDED TO THE LIST");
            }else{
                //todo: throw error
                System.out.println("file directory provided does not exist or has insufficient right to read file path: "+currentProjectFilePath);
            }
        }

//        System.out.println("NOW RETURNING RESULT: "+(projectFilesPathExistsResults.size() == projectSetting.projectFilesPaths.size()));
        return projectFilesPathExistsResults.size() == projectSetting.projectFilesPaths.size();
    };

    private HashMap<String, FileInDirectory> getLatestFilePathsFromDirectories(ArrayList<Profile> profiles) {
        HashMap<String, FileInDirectory> latestFilesInDirectories = new HashMap<String, FileInDirectory>();

        for (Profile profile : profiles) {
            String directoryPath = profile.directoryPath();
            try {
                Set<String> filesInDirectory = this.listFilesUsingDirectoryStream(directoryPath);
                if (filesInDirectory.size() <= 0) {
                    //todo: throw exception if no files are found in this path
                    return null;
                }

                //todo: refactor sometime to only create SINGLE FileInDirectory classobject AFTER fileName is valid, got dateTimeStringFromFileName AND is sorted
                List<FileInDirectory> filteredFilesInDirectory = filesInDirectory.stream()
                        .filter(fileName -> !FormattedDateMatcher.getDateTimeStringFromFileName(fileName).equals(""))
                        .map(fileName -> new FileInDirectory(fileName, directoryPath, FormattedDateMatcher.getDateTimeStringFromFileName(fileName)))
                        .filter(FileInDirectory::hasValidFileExtension)
                        .sorted()
                        .toList();

                if (filteredFilesInDirectory.size() <= 0) {
                    //todo: throw exception if no VALID files are found in this path
                    return null;
                }

                System.out.println("This is the latest file path: " + filteredFilesInDirectory.get(0).fullFileName + " of a file for directory: " + profile.directoryPath());

                latestFilesInDirectories.put(directoryPath, filteredFilesInDirectory.get(0));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return latestFilesInDirectories;
    }

    private boolean fileContainsValidStructure() {
        //todo: ensure file loaded contains a valid structure;
            // array with objects, each object contsists of properties with a key-value pair of which the value can be of any type.
            // array with objects and subobjects of root objects are allowed, objects of subobjects are not allowed.

        return true;
    }

    private Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }
        return fileList;
    }

}
