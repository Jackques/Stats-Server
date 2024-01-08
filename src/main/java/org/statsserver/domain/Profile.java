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
    @JsonIgnore
    private String localDirectoryPath;
    private Date dateTimeLatestResource;
    private Boolean useLocalDirectoryPath;

    public Profile(String name, String directoryPath, String localDirectoryPath, Boolean useLocalDirectoryPath) {
        this.name = name;
        this.directoryPath = directoryPath;
        this.localDirectoryPath = "src\\\\main\\\\resources\\\\data" + localDirectoryPath;
        this.useLocalDirectoryPath = useLocalDirectoryPath;
    }

    public void setDateTimeLatestResourceLong(Long dateTimeLatestResource) {
        this.dateTimeLatestResource = new Date(dateTimeLatestResource);
    }
}
