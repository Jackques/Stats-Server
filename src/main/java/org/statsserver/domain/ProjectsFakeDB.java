package org.statsserver.domain;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProjectsFakeDB extends ArrayList<ProjectDBDomain> {
    private final String pathToDBFile = "src/main/resources/queryDB.json";
    private ArrayList<ProjectDBDomain> projectDBDomains;
    private static Validator validator;

    private ProjectsFakeDB(){
        this.projectDBDomains = this.getAllQueriesFromFakeDb();
        this.runValidations();
    }
    //TODO TODO TODO: write logic to ensure project names in project also exist in db
    //TODO TODO TODO: write logic to no two the same projectnames exist in db
    //TODO TODO TODO: write logic to ensure no two the same projectnames exist in projectservice

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
        Boolean result = this.getProjectDBDomainByProjectName(projectName).removeQuerySetById(id);
        this.overwriteFakeDB(pathToDBFile);
        return result;
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
