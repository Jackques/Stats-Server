package org.statsserver.settings;

import lombok.Getter;
import org.statsserver.domain.FileInDirectory;
import org.statsserver.domain.ProjectSetting;
import org.statsserver.records.Profile;
import org.statsserver.services.FileReader;
import org.statsserver.util.FormattedDateMatcher;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ProjectSettings {
    //todo: This class should probably be refactored & moved into a seperate resource-like file (i.e. a .yaml, .xml or .json file in the resources folder)
    @Getter
    private final ArrayList<ProjectSetting> projectSettings = new ArrayList<ProjectSetting>();
    public ProjectSettings() {}
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
        //System.out.println("what is the name of the file?");

        latestFiles.forEach((key, value) -> {
            projectSetting.addLatestFileContents(value, FileReader.readFile(value, projectSetting.getDataTypesList()));
        });

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
                        .map(fileName -> new FileInDirectory(fileName, directoryPath, profile, FormattedDateMatcher.getDateTimeStringFromFileName(fileName)))
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

    public List<ProjectSetting> getProjectSettings(Optional<String> projectName) {
        if(projectName.isPresent()){
            return projectSettings.stream()
                    .filter(projectSetting -> Objects.equals(projectSetting.getProjectName(), projectName.get())).toList();
        }
        return projectSettings;
    }

}
