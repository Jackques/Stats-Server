package org.statsserver.services;

import org.springframework.stereotype.Service;
import org.statsserver.domain.KeyData;
import org.statsserver.domain.ProjectSetting;
import org.statsserver.records.Profile;
import org.statsserver.settings.ProjectSettings;

import java.util.*;
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
//    public HashMap<String, KeyData> getKeyDataMapFromProject(String projectName){
//        if(!this.keyDataList.containsKey(projectName)){
//            throw new RuntimeException("Projectname: '"+projectName+"' was not found. A valid projectname is required");
//        }
//        return this.keyDataList.get(projectName);
//    }
//    public KeyData getKeyFromProject(String projectName, String keyName){
//        List<ProjectSetting> projectSettingList = this.loadedProjects.getProjectSettings(Optional.of(projectName));
//        if(projectSettingList.size() > 1){
//            //todo: throw error multiple projectSettings with same name should not be allowed
//            return null;
//        }
//        return projectSettingList.get(0).getDataTypesList().getKey(keyName);
//    }

    public ArrayList<HashMap<String, Object>> getAllKeysFromProject(String projectName){
        // todo todo todo: turn into private method which returns the list once, stores it and makes it available?
        List<ProjectSetting> projectSettingList = this.loadedProjects.getProjectSettings(Optional.of(projectName));
        if(projectSettingList.size() > 1){
            //todo: throw error multiple projectSettings with same name should not be allowed
            return new ArrayList<>();
        }
        return projectSettingList.get(0).getDataTypesList().getAllKeysAndDataTypes();
        //todo: isn't this something i should do once and store it somewhere in memory for preformance?
    }
    public Set<?> getValuesFromKey(String projectName, String keyName){
        // todo todo todo: will i need this?
        List<ProjectSetting> projectSettingList = this.loadedProjects.getProjectSettings(Optional.of(projectName));
        if(projectSettingList.size() > 1){
            //todo: throw error multiple projectSettings with same name should not be allowed
            return null;
        }

        KeyData keyDataField = projectSettingList.get(0).getDataTypesList().getKey(keyName);
        if(keyDataField == null){
            return null;
            //todo: throw 400 bad request if provided field does not exist or field does not contain a list to return (wrong data type)
        }

        Set<?> listValues = keyDataField.getListValues();
        if(listValues == null){
            return null;
            //todo: throw 400 bad request if provided field does not exist or field does not contain a list to return (wrong data type)
        }
        return listValues;
    }

}
