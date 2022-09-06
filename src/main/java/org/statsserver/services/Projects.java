package org.statsserver.services;

import org.springframework.stereotype.Service;
import org.statsserver.domain.ProjectSetting;
import org.statsserver.records.Profile;
import org.statsserver.settings.ProjectSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

@Service
public class Projects {

    public ProjectSettings loadedProjects;

    public Projects() {
        System.out.println("Service Projects was instantiated!");
        // todo: add inplementation to throw exception, because if i.e. file paths are missing, importants keys are missing or config is invalid an exception is needed!

        this.loadedProjects = new ProjectSettings();
        this.loadedProjects.addProject(
                new ProjectSetting(
                    "T-Helper",
                        new ArrayList<Profile>() {
                            {
                                add(new Profile("Jack-original", "F:\\\\Dropbox\\\\Profile-jsons"));
//                                add(new Profile("Jack-new", "F:\\Dropbox\\Administratie"));
//                                add(new Profile("Jack-old", "F:\\Dropbox\\Administratie\\Aut"));
                            }
                        },
                    "No",
                    true,
                    "Date-liked-or-passed"
                )
        );
        this.loadedProjects.addProject(
                new ProjectSetting(
                        "Fitnis-stats",
                        new ArrayList<Profile>() {
                            {
                                add(new Profile("Jack-fitnes", "F:\\Dropbox\\Sport\\Fitnis\\Fitnisstats"));
                            }
                        },
                        "No",
                        true,
                        "Datum"
                )
        );
    }

}
