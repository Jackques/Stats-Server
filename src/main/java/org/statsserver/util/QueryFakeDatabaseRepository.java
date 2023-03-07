package org.statsserver.util;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;
import org.statsserver.domain.QueryDto;

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
    public ArrayList<QueryDto> resultsDB;
    private static Validator validator;

    public QueryFakeDatabaseRepository() {
        this.resultsDB = this.getAllQueriesFromFakeDb();
        this.runValidations();
    }

    public Optional<QueryDto> getQueryById(String id){
        return this.resultsDB.stream().filter((resultDb)-> resultDb.getId().toString().equals(id)).findFirst();
    }
    public List<QueryDto> getAllQueries(){
        return resultsDB;
    }
    public Boolean addQuery(QueryDto queryDto){
        Boolean result = this.resultsDB.add(queryDto);
        this.overwriteFakeDB();
        return result;
    }

//    public updateQueryById(){
//        //todo: needs inplementation
//    }

    public Boolean deleteQueryById(String id){
        Optional<QueryDto> optionalQueryDto = this.getQueryById(id);
        if(optionalQueryDto.isPresent()){
            this.resultsDB.remove(optionalQueryDto.get());
            return true;
        }
        return false;
    }

    private ArrayList<QueryDto> getAllQueriesFromFakeDb() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            QueryDto[] fileContents = mapper.readValue(new File(pathToDBFile), QueryDto[].class);
            ArrayList<QueryDto> arrayToBeReturned = new ArrayList<QueryDto>();
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
            Set<ConstraintViolation<QueryDto>> violations = validator.validate(result);
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
