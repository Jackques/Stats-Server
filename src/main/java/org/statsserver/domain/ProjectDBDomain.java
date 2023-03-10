package org.statsserver.domain;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Value
@NoArgsConstructor
public class ProjectDBDomain {
    private String projectName = null;
    private ArrayList<QuerySet> querySets = null;

    public Boolean addQuerySet(QuerySet querySet) {
        return this.querySets.add(querySet);
    }
    public Boolean removeQuerySetById(String id) {
        return this.querySets.removeIf((querySet)-> querySet.getId().toString().equals(id));
    }
}
