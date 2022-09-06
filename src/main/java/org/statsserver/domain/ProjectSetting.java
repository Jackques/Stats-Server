package org.statsserver.domain;

import org.statsserver.records.Profile;

import java.util.ArrayList;

public class ProjectSetting {
    public final String projectName;
//    public final HashMap<String, String> projectFilesPaths;
    public final ArrayList<Profile> projectFilesPaths;
    public final String keyNameForNo;
    public final Boolean useAutoNumberingRecords;
    public final String keyNameForDate;

    public ProjectSetting(String projectName, ArrayList<Profile> projectFilesPaths, String keyNameForNo, Boolean useAutoNumberingRecords, String keyNameForDate) {
        this.projectName = projectName;
        this.projectFilesPaths = projectFilesPaths;
        this.keyNameForNo = keyNameForNo;
        this.useAutoNumberingRecords = useAutoNumberingRecords;
        this.keyNameForDate = keyNameForDate;
    }
}
