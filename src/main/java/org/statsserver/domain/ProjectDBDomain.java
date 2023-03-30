package org.statsserver.domain;

import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.Optional;

@Value
@NoArgsConstructor
public class ProjectDBDomain {
    private String projectName = null;
    private ArrayList<QuerySet> querySets = null; // cannot refactor this to use a predefined empty arraylist since this will be overwritten by Jackson?

    public ArrayList<QuerySet> getQuerySets() {
        return this.querySets;
    }

    public Boolean addQuerySet(QuerySet querySet) {
        if (!this.isQuerySetNameDuplicate(querySet)) {
            return this.querySets.add(querySet);
        }
        throw new RuntimeException("Queryset with name: " + querySet.getName() + " already exists.");
    }

    public Boolean removeQuerySetById(String id) {
        return this.querySets.removeIf((querySet) -> querySet.getId().toString().equals(id));
    }

    private boolean isQuerySetNameDuplicate(QuerySet querySet) {
        return this.querySets.stream().anyMatch((currentQuerySet) -> currentQuerySet.getName().equals(querySet.getName()));
    }

    public boolean removeQuerySetResults(String id) {
        Optional<QuerySet> querySetResult = this.querySets.stream().filter((querySet) -> querySet.getId().toString().equals(id)).findFirst();
        if(querySetResult.isPresent()){
            return querySetResult.get().removeQuerySetResults();
        }
        return true;
    }
}
