package org.statsserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.statsserver.domain.ProjectsFakeDB;
import org.statsserver.domain.QuerySet;

import java.util.*;

@Service
public class QueryService {
    @Autowired
    private final ProjectService projectService;
    @Autowired
    private final ProjectsFakeDB projectsFakeDB;

    public QueryService(ProjectService projectService, ProjectsFakeDB projectsFakeDB) {
        this.projectService = projectService;
        this.projectsFakeDB = projectsFakeDB;
    }

    public UUID createQuery(String projectName, HashMap<String, ?> query) {
        try {
            ArrayList<String> fromProfiles = getFromProfiles(projectName, (ArrayList<String>) query.get("fromProfiles"));
            QuerySet newQuerySet = new QuerySet((String) query.get("name"), (String) query.get("description"), projectName, (String) query.get("graphType"), fromProfiles, (List<HashMap<String, Object>>) query.get("queryList"));
            this.projectsFakeDB.addQuerySet(newQuerySet, projectName);
            return newQuerySet.getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public Boolean updateQuery(String projectName, String queryId, HashMap<String, ?> query){
        QuerySet updatedQuerySet;
        try {
            ArrayList<String> fromProfiles = getFromProfiles(projectName, (ArrayList<String>) query.get("fromProfiles"));
            updatedQuerySet = new QuerySet((String) query.get("name"), (String) query.get("description"), projectName, (String) query.get("graphType"), fromProfiles, (List<HashMap<String, Object>>) query.get("queryList"));
            updatedQuerySet.setId(UUID.fromString(queryId));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return this.projectsFakeDB.updateQueryById(updatedQuerySet, queryId, projectName);
    }

    public List<QuerySet> getAllQueries(String projectName) {
        return this.projectsFakeDB.getAllQueries(projectName);
    }

    public Optional<QuerySet> getQueryById(String id, String projectName){
        return this.projectsFakeDB.getQueryById(id, projectName);
    }

    public Boolean removeQuery(String id, String projectName){
        return this.projectsFakeDB.deleteQueryById(id, projectName);
    }

    private ArrayList<String> getFromProfiles(String projectName, ArrayList<String> fromProfilesValue) {
        if (fromProfilesValue.size() != 0 && !this.projectService.getProfileNamesByProject(projectName).containsAll(fromProfilesValue)) {
            throw new RuntimeException("Value provided in fromProfiles is not valid");
        }
        return fromProfilesValue;
    }
}
