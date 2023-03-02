package org.statsserver.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.statsserver.util.QueryFakeDatabaseRepository;

class ProductTest {

  @Test
  public void whenUsingAnnotations_thenOk() throws IOException {
    String SOURCE_JSON = """
        {
            "id": "957c43f2-fa2e-42f9-bf75-6e3d5bb6960a",
            "name": "The Best Product",
            "brand": {
                "id": "9bcd817d-0141-42e6-8f04-e5aaab0980b6",
                "name": "ACME Products",
                "owner": {
                    "id": "b21a80b1-0c09-4be3-9ebd-ea3653511c13",
                    "name": "Ultimate Corp, Inc."
                }
            } \s
        }""";

    Product product = new ObjectMapper()
        .readerFor(Product.class)
        .readValue(SOURCE_JSON);

    assertEquals(product.getName(), "The Best Product");
    assertEquals(product.getBrandName(), "ACME Products");
    assertEquals(product.getOwnerName(), "Ultimate Corp, Inc.");
  }

  @Test
  public void queryFakeDatabaseRepository() throws Exception {
    QueryFakeDatabaseRepository queryFakeDatabaseRepository = new QueryFakeDatabaseRepository();
    assertEquals(queryFakeDatabaseRepository.resultsDB.size(), 2);
  }
}