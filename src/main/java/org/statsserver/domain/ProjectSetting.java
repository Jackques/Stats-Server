package org.statsserver.domain;

import org.statsserver.records.Profile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectSetting {
    private final String projectName;
    public final ArrayList<Profile> projectFilesPaths;
    public final String keyNameForNo;
    public final Boolean useAutoNumberingRecords;
    public final String keyNameForDate;
    private final HashMap<FileInDirectory, LinkedHashMap> latestFilePaths = new HashMap<>();
    private KeyDataList dataTypesList = new KeyDataList();
    public ProjectSetting(String projectName, ArrayList<Profile> projectFilesPaths, String keyNameForNo, Boolean useAutoNumberingRecords, String keyNameForDate) {
        this.projectName = projectName;
        this.projectFilesPaths = projectFilesPaths;
        this.keyNameForNo = keyNameForNo;
        this.useAutoNumberingRecords = useAutoNumberingRecords;
        this.keyNameForDate = keyNameForDate;
    }

    public void addLatestFileContents(FileInDirectory fileMetaData, LinkedHashMap<Integer, HashMap<String, Object>> fileContents) {
        this.latestFilePaths.put(fileMetaData, fileContents);
    }

    public String getProjectName() {
        return projectName;
    }

    public List<String> getProfileNames(){
        return projectFilesPaths.stream()
                .map(projectFilesPath -> projectFilesPath.name())
                .collect(Collectors.toList());
    }

    public KeyDataList getDataTypesList() {
        //todo: can be replaced by simple Lombok getter?
        return dataTypesList;
    }

    public LinkedHashMap<Integer, HashMap<String, ?>> getDataFromProfileName(String profileName){
        Optional<Map.Entry<FileInDirectory, LinkedHashMap>> dataProfile = this.latestFilePaths.entrySet().stream().filter((latestFilePath)-> latestFilePath.getKey().getAssociatedProfile().name().equals(profileName)).findFirst();
        if(dataProfile.isPresent()){
            return dataProfile.get().getValue();
        }
        throw new RuntimeException("No imported file for profileName: "+profileName+" present");
    }

    public Set<?> getValuesFromKey(String keyName) {
        return this.getDataTypesList().getKey(keyName).getListValues();
    }
}
