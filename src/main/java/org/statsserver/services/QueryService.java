package org.statsserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.statsserver.domain.*;
import org.statsserver.domain.Profile;

import java.util.*;
import java.util.stream.Collectors;

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
        String queryName = (String) query.get("name");

        if(queryName.isEmpty() || queryName.isBlank()){
            throw new RuntimeException("Invalid query name");
        }

        if(this.getQuerySetByName(projectName, queryName).isPresent()){
            throw new RuntimeException("Query in project: "+projectName+" with name: "+queryName+" already exists.");
        }

        try {
            ArrayList<Profile> fromProfiles = getFromProfiles(projectName, (ArrayList<String>) query.get("usedProfiles"), this.projectService.getProfilesByProject(projectName));
            QuerySet newQuerySet = new QuerySet((String) query.get("name"), (String) query.get("description"), projectName, (String) query.get("graphType"), fromProfiles, (List<HashMap<String, Object>>) query.get("queryList"));

            this.setQueryData(projectName, newQuerySet, this.getProjectDateKeyName(projectName));
            this.projectsFakeDB.addQuerySet(newQuerySet, projectName);
            return newQuerySet.getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()+""+e.getCause());
        }

    }

    private Optional<QuerySet> getQuerySetByName(String projectName, String queryName) {
        return this.projectsFakeDB.getQueryByName(queryName, projectName);
    }

    private String getProjectDateKeyName(String projectName) {
        return this.projectService.getProjectDateKey(projectName);
    }

    private HashMap<String, HashMap> getResultsForQuerySet(String projectName, QuerySet newQuerySet) {
        HashMap<String, HashMap> querySetResultsProfile = new HashMap<>();
        newQuerySet.getQueryMetaData().getAffectedProfileNames().forEach((profileName)->{
            Optional<ProjectSetting> projectSetting = this.projectService.loadedProjects.getProjectSettings(Optional.of(projectName)).stream().findFirst();
            LinkedHashMap<Integer, HashMap<String, ?>> profileData = projectSetting.get().getDataFromProfileName(profileName.getName());
            querySetResultsProfile.put(profileName.getName(), profileData);
        });
        return querySetResultsProfile;
    }
    private void setQueryData(String projectName, QuerySet newQuerySet, String dateKeyName) {
        HashMap<String, HashMap> querySetResults = this.getResultsForQuerySet(projectName, newQuerySet);
        newQuerySet.processQueriesResults(querySetResults, dateKeyName);
    }
    public Boolean updateQuery(String projectName, String queryId, HashMap<String, ?> query) {
        String newQueryName = (String) query.get("name");

        if(newQueryName.isEmpty() || newQueryName.isBlank()){
            throw new RuntimeException("Invalid query name");
        }

        Optional<QuerySet> currentQueryById = this.getQuerySetById(queryId, projectName);
        if(!currentQueryById.isPresent()){
            throw new RuntimeException("Query in project: "+projectName+" with id: "+queryId+" not found.");
        }

        QuerySet updatedQuerySet;
        try {
            ArrayList<Profile> fromProfiles = getFromProfiles(projectName, (ArrayList<String>) query.get("usedProfiles"), this.projectService.getProfilesByProject(projectName));
            updatedQuerySet = new QuerySet((String) query.get("name"), (String) query.get("description"), projectName, (String) query.get("graphType"), fromProfiles, (List<HashMap<String, Object>>) query.get("queryList"));
            this.setQueryData(projectName, updatedQuerySet, this.getProjectDateKeyName(projectName));
            updatedQuerySet.setId(UUID.fromString(queryId));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return this.projectsFakeDB.updateQueryById(updatedQuerySet, queryId, projectName);
    }

    public List<QuerySet> getAllQuerySets(String projectName) {
        return this.projectsFakeDB.getAllQueries(projectName);
    }

    public Optional<QuerySet> getQuerySetById(String id, String projectName) {
        return this.projectsFakeDB.getQueryById(id, projectName);
    }

    public Boolean removeSetQuery(String id, String projectName) {
        return this.projectsFakeDB.deleteQueryById(id, projectName);
    }

    private ArrayList<Profile> getFromProfiles(String projectName, ArrayList<String> fromProfilesValue, ArrayList<Profile> loadedProfilesList) {
        if (fromProfilesValue.size() != 0 && !this.projectService.getProfileNamesByProject(projectName).containsAll(fromProfilesValue)) {
            throw new RuntimeException("Value provided in fromProfiles does not exist in this project");
        }
        ArrayList<Profile> loadedProfiles = loadedProfilesList.stream()
                .filter((profile)-> fromProfilesValue.contains(profile.getName())).collect(Collectors.toCollection(ArrayList::new));
        if(loadedProfiles.size() == 0){
            throw new RuntimeException("No value in usedProfiles matches the current profiles loaded.");
        }
        return loadedProfiles;
    }

    public ArrayList<QueryResultDetail> getQueryDetailResults(String querySetId, String projectName, ArrayList<String> queryIds) {
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
