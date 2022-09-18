package org.statsserver.domain;

import org.statsserver.records.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        return dataTypesList;
    }
}
