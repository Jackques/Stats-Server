package org.statsserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.statsserver.domain.QuerySet;
import org.statsserver.util.QueryFakeDatabaseRepository;

import java.util.*;

@Service
public class QueryService {
    @Autowired
    private final ProjectService projectService;
    @Autowired
    private final QueryFakeDatabaseRepository queryFakeDatabaseRepository;

    public QueryService(ProjectService projectService, QueryFakeDatabaseRepository queryFakeDatabaseRepository) {
        this.projectService = projectService;
        this.queryFakeDatabaseRepository = queryFakeDatabaseRepository;
    }

    public UUID createQuery(String projectName, HashMap<String, ?> query) {
        try {
            ArrayList<String> fromProfiles = getFromProfiles(projectName, (ArrayList<String>) query.get("fromProfiles"));

            QuerySet newQuerySet = new QuerySet((String) query.get("name"), (String) query.get("description"), projectName, (String) query.get("graphType"), fromProfiles, (List<HashMap<String, Object>>) query.get("queryList"));
            this.queryFakeDatabaseRepository.addQuery(newQuerySet);
            return newQuerySet.getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<QuerySet> getAllQueries() {
        return this.queryFakeDatabaseRepository.getAllQueries();
    }

    public Optional<QuerySet> getQueryById(String id){
        return this.queryFakeDatabaseRepository.getQueryById(id);
    }

    public Boolean removeQuery(String id){
        return this.queryFakeDatabaseRepository.deleteQueryById(id);
    }

    private ArrayList<String> getFromProfiles(String projectName, ArrayList<String> fromProfilesValue) {
        if (fromProfilesValue.size() != 0 && !this.projectService.getProfileNamesByProject(projectName).containsAll(fromProfilesValue)) {
            throw new RuntimeException("Value provided in fromProfiles is not valid");
        }
        return fromProfilesValue;
    }
}
