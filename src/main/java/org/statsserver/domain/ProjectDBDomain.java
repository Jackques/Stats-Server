package org.statsserver.domain;

import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Value
@NoArgsConstructor
public class ProjectDBDomain {
    private String projectName = null;
    private ArrayList<QuerySet> querySets = null; // cannot refactor this to use a predefined empty arraylist since this will be overwritten by Jackson?
    public ArrayList<QuerySet> getQuerySets(Boolean ignoreResults){
        System.out.println("OMG I AM ACTUALLY CALLED INSTEAD OF THE LOMBOK GETTER?");
        if(ignoreResults){
            //BUG: SHALLOW COPY OF ARRAY DELETES THE CONTENTS
            // EITHER FIX THIS BY MAKING A DEEP COPY OR RETHINK THIS SOLUTION ENTIRELY
            // i.e; I store the results amount only in queryResults, AND details can be retrieved by a seperate endpoint?
            return this.querySets.stream().map((querySet)->{
                querySet.cleanResults();
                return querySet;
            }).collect(toCollection(ArrayList::new));
        }
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
}
