package org.statsserver.services;

import org.springframework.stereotype.Service;
import org.statsserver.domain.KeyData;
import org.statsserver.domain.ProjectSetting;
import org.statsserver.records.Profile;
import org.statsserver.settings.ProjectSettings;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    public ProjectSettings loadedProjects;
    private final HashMap<String, HashMap<String, KeyData>> keyDataList = new HashMap<String, HashMap<String, KeyData>>();
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
                        "Fitness-stats", //todo: when changing the name, don't forget to also change the same name in FileReader accordingly!
                        new ArrayList<Profile>() {
                            {
//                                add(new Profile("Jack-fitnes", "D:\\Projects\\Prive\\Stats-Server\\examples\\fitness-stats"));
                                add(new Profile("Jack-fitnes", "F:\\Dropbox\\Sport\\Fitnis\\Fitnisstats")); //todo: when changing the name, don't forget to also change the same name in FileReader accordingly!
                            }
                        },
                        "No",
                        true,
                        "Datum"
                )
        );
        System.out.println("All projects have been loaded");
        this.loadAllKeyDataFromProject();
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
                .map(ProjectSetting::getProfileNames)//todo: getProfileNames is not a static method, but isn't :: only used when accessing static methods?
                .findFirst().orElse(null);
    }

    public boolean getProjectNameExist(String projectName){
        if(projectName.isEmpty()){
            //TODO: throw error OR return 404
            return false;
        }
        return this.getLoadedProjectNames().contains(projectName);
    }

    private void loadAllKeyDataFromProject(){
        List<ProjectSetting> projectSettingList = this.loadedProjects.getProjectSettings();
        projectSettingList.forEach(projectSetting -> {
            this.keyDataList.put(projectSetting.getProjectName(), projectSetting.getDataTypesList().getKeyDataList());
            KeyDataListStatic.addKeyDataMap(projectSetting.getProjectName(), projectSetting.getDataTypesList().getKeyDataList());
        });
    }

    public String getProjectDateKey(String projectName){
        AtomicReference<String> projectDateKey = new AtomicReference<>("");
        this.loadedProjects.getProjectSettings().forEach((projectSetting)->{
            if(projectSetting.getProjectName().equals(projectName)){
                projectDateKey.set(projectSetting.keyNameForDate);
            }
        });
        if(projectDateKey.get().isEmpty() || projectDateKey.get().isBlank()){
            throw new RuntimeException("Could not get set project date key for project: "+projectName);
        }
        return projectDateKey.get();
    }

    public ArrayList<HashMap<String, Object>> getAllKeysFromProject(String projectName){
        List<ProjectSetting> projectSettingList = this.loadedProjects.getProjectSettings(Optional.of(projectName));
        if(projectSettingList.size() > 1){
            return new ArrayList<>();
        }
        return projectSettingList.get(0).getDataTypesList().getAllKeysAndDataTypes();
    }

}
