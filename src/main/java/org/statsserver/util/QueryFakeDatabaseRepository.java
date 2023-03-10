package org.statsserver.util;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;
import org.statsserver.domain.QuerySet;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class QueryFakeDatabaseRepository {
    private final String pathToDBFile = "src/main/resources/queryDB.json";
    public ArrayList<QuerySet> resultsDB;
    private static Validator validator;

    public QueryFakeDatabaseRepository() {
        this.resultsDB = this.getAllQueriesFromFakeDb();
        this.runValidations();
    }

    public Optional<QuerySet> getQueryById(String id){
        return this.resultsDB.stream().filter((resultDb)-> resultDb.getId().toString().equals(id)).findFirst();
    }
    public List<QuerySet> getAllQueries(){
        return resultsDB;
    }
    public Boolean addQuery(QuerySet querySet){
        Boolean result = this.resultsDB.add(querySet);
        this.overwriteFakeDB();
        return result;
    }

//    public updateQueryById(){
//        //todo: needs inplementation
//    }

    public Boolean deleteQueryById(String id){
        Optional<QuerySet> optionalQueryDto = this.getQueryById(id);
        if(optionalQueryDto.isPresent()){
            this.resultsDB.remove(optionalQueryDto.get());
            return true;
        }
        return false;
    }

    private ArrayList<QuerySet> getAllQueriesFromFakeDb() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            QuerySet[] fileContents = mapper.readValue(new File(pathToDBFile), QuerySet[].class);
            ArrayList<QuerySet> arrayToBeReturned = new ArrayList<QuerySet>();
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
        this.resultsDB.forEach((result) -> {
            Set<ConstraintViolation<QuerySet>> violations = validator.validate(result);
            if (violations.size() > 0) {
                System.out.println("Validations found!");
                throw new RuntimeException("Validations found in imported queries, please resolve these issues; " + violations.toString());
            }
        });
    }

    private Boolean overwriteFakeDB(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(pathToDBFile), this.resultsDB);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
