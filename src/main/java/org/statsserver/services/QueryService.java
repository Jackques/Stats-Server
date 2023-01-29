package org.statsserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.statsserver.domain.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class QueryService {
    HashMap<UUID, Query> queries = new HashMap<UUID, Query>();
    @Autowired private final ProjectService projectService;
    public QueryService(ProjectService projectService) {
        this.projectService = projectService;
    }
    public UUID createQuery(String projectName, HashMap<String, ?> query) {
        try{
            ArrayList<String> fromProfiles = getFromProfiles(projectName, (ArrayList<String>) query.get("fromProfiles"));

            Query newQuery = new Query((String) query.get("name"), (String) query.get("description"), projectName, fromProfiles, (HashMap<String, Object>) query.get("queryContent"));
            this.queries.put(newQuery.getId(), newQuery);
            return newQuery.getId();
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private ArrayList<String> getFromProfiles(String projectName, ArrayList<String> fromProfilesValue) {
        if (fromProfilesValue.size() != 0 && !this.projectService.getProfileNamesByProject(projectName).containsAll(fromProfilesValue)) {
            throw new RuntimeException("Value provided in fromProfiles is not valid");
        }
        return fromProfilesValue;
    }
}
