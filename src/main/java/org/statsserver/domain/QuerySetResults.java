package org.statsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter // Remember; for sending back data to the REST controller it needs to have a @Getter otherwise Jackson (part of Springboot RESTcontroller?) cannot access the properties in this class
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuerySetResults {
    private int totalResults = 123456789;
    private int amountOfQueries = 1132579486;
    private ArrayList<QueryResult> querySetResults = new ArrayList<>();
}
