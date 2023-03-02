package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
//import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Product {

  @Getter
  @Setter
  private String id; // since this properties exist at root level, this is automatically set
  @NotNull
  @Getter
  @Setter
  private String name; // since this properties exist at root level, this is automatically set
  @Getter
  @Setter
  private String brandName;
  @Getter
  @Setter
  private String ownerName;

  @SuppressWarnings("unchecked")
  @JsonProperty("brand")
  private void unpackNested(Map<String,Object> brand) {
    this.brandName = (String)brand.get("name"); // this property is being set by extracting the 'brand' property in the json
    Map<String,String> owner = (Map<String,String>)brand.get("owner");
    this.ownerName = owner.get("name"); // get the name from the owner property inside the brand property of which the latter is being provided here
  }
}