package org.statsserver.services;

import org.springframework.stereotype.Service;
import org.statsserver.domain.ProjectSetting;
import org.statsserver.records.Profile;
import org.statsserver.settings.ProjectSettings;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    public ProjectSettings loadedProjects;
    public ProjectService() {
        System.out.println("Service Projects was instantiated!");
        // todo: add inplementation to throw exception, because if i.e. file paths are missing, importants keys are missing or config is invalid an exception is needed!

        this.loadProjects();
    }

    private void loadProjects(){
        this.loadedProjects = new ProjectSettings();
        this.loadedProjects.addProject(
                new ProjectSetting(
                        "T-Helper",
                        new ArrayList<Profile>() {
                            {
//                                add(new Profile("Jack-original", "D:\\Projects\\Prive\\Stats-Server\\examples\\profile-jsons"));
                                add(new Profile("Jack-original", "F:\\\\Dropbox\\\\Profile-jsons"));
                                add(new Profile("Jack-updated", "F:\\\\Dropbox\\\\Profile-jsons-new")); //todo: only created this folder with copied stats for testing purposes, remove when no longer needed
                            }
                        },
                        "No",
                        true,
                        "Date-liked-or-passed"
                )
        );
        this.loadedProjects.addProject(
                new ProjectSetting(
                        "Fitness-stats",
                        new ArrayList<Profile>() {
                            {
//                                add(new Profile("Jack-fitnes", "D:\\Projects\\Prive\\Stats-Server\\examples\\fitness-stats"));
                                add(new Profile("Jack-fitnes", "F:\\Dropbox\\Sport\\Fitnis\\Fitnisstats"));
                            }
                        },
                        "No",
                        true,
                        "Datum"
                )
        );
        System.out.println("All projects have been loaded");
    }

    public List<String> getLoadedProjectNames(){
        return this.loadedProjects.getProjectSettings(Optional.empty())
                .stream()
                .map(ProjectSetting::getProjectName)
                .collect(Collectors.toList());
    }

    public List<String> getProfileNamesByProject(String projectName){
        return this.loadedProjects.getProjectSettings(Optional.ofNullable(projectName))
                .stream()
                .map(ProjectSetting::getProfileNames)
                .findFirst().orElse(null);
    }

    public boolean getProjectNameExist(String projectName){
        if(projectName.isEmpty()){
            //TODO: throw error OR return 404
            return false;
        }
        return this.getLoadedProjectNames().contains(projectName);
    }

    public ArrayList<HashMap<String, String>> getAllKeysFromProject(String projectName){
        List<ProjectSetting> projectSettingList = this.loadedProjects.getProjectSettings(Optional.of(projectName));
        if(projectSettingList.size() > 1){
            //todo: throw error multiple projectSettings with same name should not be allowed
            return new ArrayList<>();
        }
        return projectSettingList.get(0).getDataTypesList().getAllKeysAndDataTypes();
    }





    public Boolean getKeyExistsInProject(String projectName, String keyName){
        List<ProjectSetting> projectSettingList = this.loadedProjects.getProjectSettings(Optional.of(projectName));
        if(projectSettingList.size() > 1){
            return false;
        }
        return projectSettingList.get(0).getDataTypesList().getKeyExists(keyName);
    }

    public Set<?> getValuesFromKey(String projectName, String keyName){
        List<ProjectSetting> projectSettingList = this.loadedProjects.getProjectSettings(Optional.of(projectName));
        if(projectSettingList.size() > 1){
            //todo: throw error multiple projectSettings with same name should not be allowed
            return Set.of();
        }
        return projectSettingList.get(0).getValuesFromKey(keyName);
    }

}
