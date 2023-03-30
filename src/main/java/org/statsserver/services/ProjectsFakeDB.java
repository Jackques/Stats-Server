package org.statsserver.services;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.statsserver.domain.ProjectDBDomain;
import org.statsserver.domain.QuerySet;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProjectsFakeDB extends ArrayList<ProjectDBDomain> {
    @Autowired
    private final ProjectService projectService;
    private final String pathToDBFile = "src/main/resources/queryDB.json";
    private ArrayList<ProjectDBDomain> projectDBDomains;
    private static Validator validator;

    private ProjectsFakeDB(ProjectService projectService){
        this.projectService = projectService;

        this.projectDBDomains = this.getAllQueriesFromFakeDb();
        this.runValidations();
        this.checkProjects();
    }

    private void checkProjects() {
        List<String> dbProjects = this.projectDBDomains.stream().map(ProjectDBDomain::getProjectName).toList();
        if(!dbProjects.containsAll(projectService.getLoadedProjectNames())){
            throw new RuntimeException("Some projects set in projectService have no (fake)DB entry");
        }
    }

    public Optional<QuerySet> getQueryById(String id, String projectName){
        return this.getProjectDBDomainByProjectName(projectName).getQuerySets().stream().filter((querySet)-> querySet.getId().toString().equals(id)).findFirst();
    }
    public List<QuerySet> getAllQueries(String projectName){
        return this.getProjectDBDomainByProjectName(projectName).getQuerySets();
    }
    public Boolean addQuerySet(QuerySet querySet, String projectName){
        ProjectDBDomain projectDBDomain = this.getProjectDBDomainByProjectName(projectName);
        Boolean result = projectDBDomain.addQuerySet(querySet);
        this.overwriteFakeDB(pathToDBFile);
        return result;
    }
    public Boolean updateQueryById(QuerySet querySet, String queryId, String projectName){
        Boolean result = false;
        Optional<QuerySet> currentQuerySet = this.getQueryById(queryId, projectName);
        if(currentQuerySet.isPresent()){
            if(this.deleteQueryById(queryId, projectName)){
                result = this.addQuerySet(querySet, projectName);
                this.overwriteFakeDB(pathToDBFile);
            }
        }
        return result;
    }
    public Boolean deleteQueryById(String id, String projectName){
        Boolean isRemovedQueryResults = this.getProjectDBDomainByProjectName(projectName).removeQuerySetResults(id);
        Boolean isRemovedQuerySet = this.getProjectDBDomainByProjectName(projectName).removeQuerySetById(id);
        this.overwriteFakeDB(pathToDBFile);
        return isRemovedQueryResults && isRemovedQuerySet;
    }

    private ProjectDBDomain getProjectDBDomainByProjectName(String projectName){
        Optional<ProjectDBDomain> project = this.projectDBDomains.stream().filter((projectDBDomain)-> projectDBDomain.getProjectName().equals(projectName)).findFirst();
        if(project.isPresent()){
            return project.get();
        }else{
            throw new RuntimeException("Project with name :"+projectName+" does not exist in the database");
        }
    }

    private ArrayList<ProjectDBDomain> getAllQueriesFromFakeDb() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            ProjectDBDomain[] fileContents = mapper.readValue(new File(pathToDBFile), ProjectDBDomain[].class);
            ArrayList<ProjectDBDomain> arrayToBeReturned = new ArrayList<>();
            Collections.addAll(arrayToBeReturned, fileContents);
            return arrayToBeReturned;
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file!");
            throw new RuntimeException(e);
        }
    }

    private void runValidations() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
        this.projectDBDomains.forEach((result) -> {
            Set<ConstraintViolation<ProjectDBDomain>> violations = validator.validate(result);
            if (violations.size() > 0) {
                System.out.println("Validations found!");
                throw new RuntimeException("Validations found in imported queries, please resolve these issues; " + violations.toString());
            }
        });
    }
    private Boolean overwriteFakeDB(String pathToDBFile){
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(pathToDBFile), this.projectDBDomains);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
