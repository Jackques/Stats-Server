package org.statsserver.settings;

import lombok.Getter;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.statsserver.domain.FileInDirectory;
import org.statsserver.domain.ProjectSetting;
import org.statsserver.domain.Profile;
import org.statsserver.services.FileReader;
import org.statsserver.util.FormattedDateMatcher;
import org.statsserver.util.OnHeroku;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectSettings {
    //todo: This class should probably be refactored & moved into a seperate resource-like file (i.e. a .yaml, .xml or .json file in the resources folder)
    @Getter
    private final ArrayList<ProjectSetting> projectSettings = new ArrayList<ProjectSetting>();
    public ProjectSettings() {}
    public boolean addProject(ProjectSetting projectSetting){
        if(!OnHeroku.isHeroku()){
            if(!this.isFilePathsValid(projectSetting) || !this.isProjectNameValid(projectSetting)){
                throw new RuntimeException("Projectsetting: "+projectSetting.getProjectName()+" is invalid. Please check your projectsettings");
            }
        }

        if(OnHeroku.isHeroku()){
            System.out.println("On Heroku, so no need to check if file paths are valid");
        }


        HashMap<String, FileInDirectory> latestFiles = this.getLatestFilePathsFromDirectories(projectSetting.projectFilesPaths);

        //             boolean workOnHeroku = this.willThisWorkOnHeroku(currentProjectFilePath);

        if(Objects.isNull(latestFiles) || latestFiles.size() == 0){
            //todo: throw error; could not retrieve latest file from directory path
            return false;
        }
        //System.out.println("what is the name of the file?");

        latestFiles.forEach((key, value) -> {
            projectSetting.addLatestFileContents(value, FileReader.readFile(value, projectSetting.getDataTypesList()));
            value.getAssociatedProfile().setDateTimeLatestResource(value.formattedDateTime);
        });

        // inplement other logic for importing projects

        this.projectSettings.add(projectSetting);
        return true;
    }

    private boolean isProjectNameValid(ProjectSetting projectSetting) {
        String projectName = projectSetting.getProjectName();
        boolean isBlank = projectName.isBlank();
        boolean isDuplicate = this.projectSettings.stream().anyMatch((currentProjectSetting)-> currentProjectSetting.getProjectName().equals(projectName));
        return !isBlank && !isDuplicate;
    }

    private boolean isFilePathsValid(ProjectSetting projectSetting) {
        LinkedList<Boolean> projectFilesPathExistsResults = new LinkedList<>();

        for (int i = 0; i < projectSetting.projectFilesPaths.size(); i++) {
            Profile currentProfile = projectSetting.projectFilesPaths.get(0);
            String currentProjectFilePath = currentProfile.getDirectoryPath();
            if(currentProfile.getUseLocalDirectoryPath()){
                System.out.println("Using local directory path for profile: %s".formatted(currentProfile.getName()));
                currentProjectFilePath = currentProfile.getLocalDirectoryPath();
            }

//            System.out.println("NOW CHECKING FILE PATH NO: "+i+" PATH: "+currentProjectFilePath);

            if(Files.isDirectory(Path.of(currentProjectFilePath))){
                if(this.hasDirectoryNoDataFiles(Path.of(currentProjectFilePath))){
                    throw new RuntimeException("The directory: "+currentProjectFilePath+" set for project: "+projectSetting.getProjectName()+" contains no data files to get data from");
                }
                projectFilesPathExistsResults.add(true);
//                System.out.println("FILE PATH IS VALID, TRUE BEING ADDED TO THE LIST");
            }else{
                //todo: throw error
                throw new RuntimeException("file directory provided does not exist or has insufficient right to read file path: "+currentProjectFilePath);
            }
        }

//        System.out.println("NOW RETURNING RESULT: "+(projectFilesPathExistsResults.size() == projectSetting.projectFilesPaths.size()));
        return projectFilesPathExistsResults.size() == projectSetting.projectFilesPaths.size();
    }

    private boolean willThisWorkOnHeroku(String currentProjectFilePath) {
        try{
            byte[] array = new byte[100];

            ClassLoader cl = this.getClass().getClassLoader();
            InputStream inputStream = cl.getResourceAsStream(currentProjectFilePath);

            inputStream.read(array);
            System.out.println("START - Data read from the file: ");

            String data = new String(array);
            System.out.println(data);
            System.out.println("END - Data read from the file: ");

            return true;
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("Reading file; Take 1 does not work");
        } catch (RuntimeException e) {
            System.out.println("Reading file; Take 1 does not work, RuntimeException");
        }

        try{
            InputStream inputStream = ResourceUtils.getURL(currentProjectFilePath).openStream();
            String wholeContentOfFile = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            System.out.println("START - Data read from the file, take 2: ");
            System.out.println(wholeContentOfFile);
            System.out.println("END - Data read from the file, take 2: ");

            inputStream.close();

            return true;
        }catch(RuntimeException e){
            System.out.println("Reading file; Take 2 does not work, RuntimeException");
        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
            System.out.println("Reading file; Take 2 does not work, FileNotFoundException");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("Reading file; Take 2 does not work, IOException");
        }


        return false;
    }

    private boolean hasDirectoryNoDataFiles(Path directoryPath) {
        try (Stream<Path> pathStream = Files.list(directoryPath)) {
            Set<String> directoryContents = pathStream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
            return directoryContents.size() > 0 ? false : true;
        }catch(IOException e){
            throw new RuntimeException("Error getting directory, the path: "+ directoryPath.toString() +" is not a directory");
        }
    }

    private HashMap<String, FileInDirectory> getLatestFilePathsFromDirectories(ArrayList<Profile> profiles) {
        HashMap<String, FileInDirectory> latestFilesInDirectories = new HashMap<String, FileInDirectory>();

        for (Profile profile : profiles) {
//            String directoryPath = profile.getDirectoryPath();
            String directoryPath = profile.getUseLocalDirectoryPath() ? profile.getLocalDirectoryPath() : profile.getDirectoryPath();

            if(profile.getUseLocalDirectoryPath()){
                System.out.println("Retrieving data from local directory for profile: %s".formatted(profile.getLocalDirectoryPath()));
            }
            Set<String> filesInDirectory = Set.of();

            try {
                filesInDirectory = this.listFilesUsingDirectoryStream(directoryPath);
            } catch (IOException e) {
                System.out.println("Error retrieving listFilesUsingDirectoryStream");
                throw new RuntimeException(e);
            }

            if (filesInDirectory.size() <= 0) {
                //todo: throw exception if no files are found in this path
                System.out.println("filesInDirectory is empty");
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
                System.out.println("FilteredFilesInDirectory is empty");
                return null;
            }

            System.out.println("This is the latest file path: " + filteredFilesInDirectory.get(0).fullFileName + " of a file for directory: " + profile.getDirectoryPath());

            latestFilesInDirectories.put(directoryPath, filteredFilesInDirectory.get(0));
        }
        return latestFilesInDirectories;
    }
    private Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        Path path1 = null;

        try{
            path1 = Paths.get(dir);
        }catch(RuntimeException e){
            System.out.println("Error when attempting to get the path of the directory");
            throw e;
        }

        DirectoryStream<Path> stream = null;
        try{
            stream = Files.newDirectoryStream(path1);
        }catch(RuntimeException e){
            System.out.println("Error when attempting to use Files.newDirectoryStream");
            System.out.println(e.getMessage());
            System.out.println("===");
            throw e;
        }

        try {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }catch(RuntimeException e){
            System.out.println("Error when attempting to use Files.newDirectoryStream AND forloop over the stream for: "+ path1);
            System.out.println(e.getMessage());
            System.out.println("===");
            throw e;
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
