package org.statsserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.statsserver.domain.*;

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
            ArrayList<String> fromProfiles = getFromProfiles(projectName, (ArrayList<String>) query.get("usedProfiles"));
            QuerySet newQuerySet = new QuerySet((String) query.get("name"), (String) query.get("description"), projectName, (String) query.get("graphType"), fromProfiles, (List<HashMap<String, Object>>) query.get("queryList"));
//            QuerySetResults results = this.getResultsForQuerySet(newQuerySet);
            ArrayList<HashMap<String, HashMap>> querySetResults = this.getResultsForQuerySet(projectName, newQuerySet);

//            this.projectsFakeDB.addQuerySet(newQuerySet, projectName); //TODO TODO TODO: Tijdelijk uitgecomment omdat ik niet duizend nieuwe records in db wil hebben
            return newQuerySet.getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private ArrayList<HashMap<String, HashMap>> getResultsForQuerySet(String projectName, QuerySet newQuerySet) {
        ArrayList<HashMap<String, HashMap>> querySetResultsProfile = new ArrayList<>();
        newQuerySet.getQueryMetaData().getAffectedProfileNames().forEach((profileName)->{
            Optional<ProjectSetting> projectSetting = this.projectService.loadedProjects.getProjectSettings(Optional.of(projectName)).stream().findFirst();
            LinkedHashMap<Integer, HashMap<String, ?>> profileData = projectSetting.get().getDataFromProfileName(profileName);
            querySetResultsProfile.add(new HashMap<String, LinkedHashMap>().put(profileName, profileData));
        });
        return querySetResultsProfile;

//        newQuerySet.getQueryMetaData().getAffectedProfileNames().
    }

    public Boolean updateQuery(String projectName, String queryId, HashMap<String, ?> query) {
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

    public Optional<QuerySet> getQueryById(String id, String projectName) {
        return this.projectsFakeDB.getQueryById(id, projectName);
    }

    public Boolean removeQuery(String id, String projectName) {
        return this.projectsFakeDB.deleteQueryById(id, projectName);
    }

    private ArrayList<String> getFromProfiles(String projectName, ArrayList<String> fromProfilesValue) {
        if (fromProfilesValue.size() != 0 && !this.projectService.getProfileNamesByProject(projectName).containsAll(fromProfilesValue)) {
            throw new RuntimeException("Value provided in fromProfiles is not valid");
        }
        return fromProfilesValue;
    }

    public ArrayList<QueryResult> getQueryDetailResults(String querySetId, String projectName, ArrayList<String> queryIds) {
        Optional<QuerySet> querySet = this.projectsFakeDB.getQueryById(querySetId, projectName);
        if (querySet.isPresent()) {
            QuerySetResults querySetResults = querySet.get().getQuerySetResults();
            if (!querySetResults.isValidIds(queryIds)) {
                throw new RuntimeException("QueryIds provided is not valid");
            }
            return querySetResults.getQueryResultsByQueryIds(queryIds);
        }
        throw new RuntimeException("Provided queryId and/or projectName is invalid");
    }
}
