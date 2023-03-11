package org.statsserver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.statsserver.enums.GraphType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuerySetTest {
    @Test
    public void whenUsingAnnotations_thenOk() throws IOException {
        // Test to check if it's possible to map a JSON string directly into a class instance
        String SOURCE_JSON = """
                        {
                            "id": "957c43f2-fa2e-42f9-bf75-6e3d5bb6960a",
                            "name": "Verloop fitnesresultaten 100 dagen",
                            "description": "Het verloop van de fitnesresultaten van de afgelopen 100 dagen",
                            "queryMetaData": {
                                "affected-profile-names": ["Jack-fitnes"],
                                "affected-keys": ["Gewicht in kg", "Vetpercentage in %", "Spiermassa in KG", "Spiermassa in PERCENTAGE (nu met kommagetal!)", "Lichaamsvocht in KG", "Lichaamsvocht in PERCENTAGE (nu met kommagetal)"],
                                "graphType": "LINE_GRAPH"
                            },
                            "queryResults": {},
                            "queryMetaData" : {
                                  "affectedProfileNames" : [ "Jack-fitnes" ],
                                  "graphType" : "COLUMN_CHART"
                            },
                            "queries": [{
                                    "amountValue": "ALL",
                                    "fromDate": "2021-01-27T14:26:11.339Z",
                                    "toDate": "ALL",
                                    "labelForThisQuery": "myFirstQuery",
                                    "colorQuery": "#ff3333",
                                    "queryParameters": [
                                                {
                                                    "key": "Age",
                                                    "operator": "GREATER_THAN_OR_EQUAL_TO",
                                                    "value": 35.0
                                                },
                                                {
                                                    "key": "Interests",
                                                    "operator": "CONTAINS",
                                                    "value": ["Reizen"]
                                                },
                                                {
                                                    "key": "How-many-ghosts",
                                                    "subKey": "number",
                                                    "operator": "LESS_THAN_OR_EQUAL_TO",
                                                    "value": 1
                                                }
                                    ]
                            }]
                        }
                """;

        QuerySet querySet = new ObjectMapper()
                .readerFor(QuerySet.class)
                .readValue(SOURCE_JSON);

        assertEquals(querySet.getName(), "Verloop fitnesresultaten 100 dagen");
        assertEquals(querySet.getProjectName(), null);
        assertEquals(querySet.getQueryMetaData().getAffectedProfileNames().contains("Jack-fitnes"), true);
        assertEquals(querySet.getQueryMetaData().getGraphType().equals(GraphType.COLUMN_CHART), true);
    }
}