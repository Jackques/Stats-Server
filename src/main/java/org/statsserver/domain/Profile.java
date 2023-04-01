package org.statsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Profile {
    private String name;
    @JsonIgnore
    private String directoryPath;
    private Date dateTimeLatestResource;

    public Profile(String name, String directoryPath) {
        this.name = name;
        this.directoryPath = directoryPath;
    }

    public void setDateTimeLatestResourceLong(Long dateTimeLatestResource) {
        this.dateTimeLatestResource = new Date(dateTimeLatestResource);
    }
}
