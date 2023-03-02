package org.statsserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.stereotype.Service;
import org.statsserver.domain.Product;

@Service
public class QueryFakeDatabaseRepository {
  private final String pathToDBFile = "src/main/resources/queryDB.json";
  public List<Product> resultsDB;
  private static Validator validator;
  public QueryFakeDatabaseRepository() {
    this.resultsDB = this.getAllQueries();
    this.runValidations();
  }

  private List<Product> getAllQueries() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      Product[] fileContents = mapper.readValue(new File(pathToDBFile), Product[].class);
      return Arrays.stream(fileContents).toList();
    }catch (IOException e) {
      System.out.println("Something went wrong while reading the file!");
      throw new RuntimeException(e);
    }
  }

  private void runValidations() {
    try(ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      validator = validatorFactory.getValidator();
    }
    this.resultsDB.forEach((result)->{
      Set<ConstraintViolation<Product>> violations = validator.validate(result);
      if(violations.size() > 0){
        System.out.println("Validations found!");
        throw new RuntimeException("Validations found in imported queries, please resolve these issues; "+violations.toString());
      }
    });
  }
}
