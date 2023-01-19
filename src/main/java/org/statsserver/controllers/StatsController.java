package org.statsserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.statsserver.domain.KeyData;
import org.statsserver.services.ProjectService;

import java.net.URI;
import java.util.*;

@RestController
public class StatsController {

    private final ProjectService projectService;

    @Autowired
    public StatsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //todo: advies Siebe; pas dezelfde path toe op meerdere annotations, maar dan wel van andere types; delete, get, post? Vraag nogmaals naar meer uitleg

    // LEARNED: do not return Java object instances of my own created classes (an error is returned if you do).
    // Instead ALWAYS return a known Java object ie.e. hashmap, map, array, etc.
    // These are automatically converted into JSON when returning these values to a client (hashmap becomes JSON object, ArrayList becomes JSON array etc.)

    // When returning objects to a REST endpoint that is

    @RequestMapping(path = "api/v1/getProjects")
    public List<String> getProjects(){
        System.out.println("GET MAPPING AANGESPROKEN getProjects: "+this.projectService.getLoadedProjectNames());
        return this.projectService.getLoadedProjectNames();
    }
    @RequestMapping(path = "api/v1/getProfileNamesFromProject/{projectName}")
    public List<String> getProfileNamesFromProject(@PathVariable("projectName") String projectName){
        System.out.println("GET MAPPING AANGESPROKEN getProfileNamesFromProject: "+this.projectService.getProfileNamesByProject(projectName)+", and the projectName provided is: "+projectName);

        if(!this.projectService.getProjectNameExist(projectName)){
            //TODO: throw error OR return 404
        }
        return this.projectService.getProfileNamesByProject(projectName);
    }

    @RequestMapping(path = "api/v1/getKeysFromProject/{projectName}")
    public ResponseEntity<ArrayList<HashMap<String, Object>>> getKeysFromProject(@PathVariable("projectName") String projectName){
        System.out.println("GET MAPPING AANGESPROKEN getKeysFromProject: ");

        if(!this.projectService.getProjectNameExist(projectName)){
            //TODO: throw error OR return 404
        }

        return ResponseEntity
                .ok()
                .body(this.projectService.getAllKeysFromProject(projectName));
    }

    @GetMapping(path = "api/v1/getAllListValuesFromKeyInProject/{projectName}/{keyName}")
    public List<?> getAllListValuesFromKeyInProject(@PathVariable String projectName, @PathVariable String keyName){
        System.out.println("GET MAPPING AANGESPROKEN getAllListValuesFromKey: ");

        if(!this.projectService.getProjectNameExist(projectName)){
            //TODO: throw error OR return 404
        }
        if(!this.projectService.getKeyExistsInProject(projectName, keyName)){
            //TODO: throw error OR return 404
        }

        Set<?> listValues = this.projectService.getValuesFromKey(projectName, keyName);
        if(listValues == null){
            return null;
            //todo: throw 400 bad request if provided field does not exist or field does not contain a list to return (wrong data type)
        }

        return listValues.stream().toList();
    }

    @GetMapping(path = "api/v1/getAllQueriesFromProject/{projectName}")
    public ResponseEntity<List<String>> getAllQueriesFromProject(@PathVariable String projectName){

        //todo: replace mocked values with actual values
        //todo: return queryId's
        return ResponseEntity
                .ok()
                .body(List.of("abc123", "def456"));
    }

    @PostMapping(path = "api/v1/postQuery/{projectName}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ArrayList<String>> postQuery(@PathVariable String projectName, @RequestBody HashMap<String, ?> queryString){
        System.out.println("POST MAPPING AANGESPROKEN queryString: , received queryString: " + queryString);

        //todo: do something with project name

        if(!this.queryIsValid(queryString)){
            //todo: is this the correct way to return an invalid query?
            ArrayList<String> someArray = new ArrayList<String>();
            someArray.add("");
            return ResponseEntity.unprocessableEntity().body(someArray);
        }

        //todo: IDEA; why have queryString when client can also build a JSON object which can be converted into a Java object; Query class? AND easily return as such! E.g;
        ArrayList<String> deconstructedQueryList = this.mockQueryToQueryClass(queryString);

        //todo: replace mocked values with actual values
        //todo: return queryId
        return ResponseEntity
                .created(
                        URI.create(String.format("/persons/%s", 32.33434))
//                        URI.create("test")
                )
                .body(deconstructedQueryList);
    }

    @GetMapping(path = "api/v1/getQuery/{projectName}/{queryId}")
    public boolean getQuery(@PathVariable String projectName, @PathVariable String queryId){
        System.out.println("GET MAPPING AANGESPROKEN getQuery, projectName is: " + projectName + "queryId is: " + queryId);

        if(Objects.equals(projectName, "T-Helper") && Objects.equals(queryId, "123abcDEF")){
            return true;
        }
        //todo: needs work, return actual result of query or bad request or query not found?
        return false;
    }

    @DeleteMapping(path = "api/v1/deleteQuery/{projectName}/{queryId}")
    public boolean deleteQuery(@PathVariable String projectName, @PathVariable String queryId){
        System.out.println("DELETE MAPPING AANGESPROKEN deleteQuery, projectName is: " + projectName + "queryId is: " + queryId);

        //todo: needs work
        return true;
    }

    private boolean queryIsValid(HashMap<String, ?> queryString) {
        //todo: update this method to actually check query or move this logic to query service class?
        return true;
    }
    private boolean keyHasListValues(String keyName) {
        if(Objects.equals(keyName, "Interests") || Objects.equals(keyName, "Vibe-tags")){
            return true;
        }
        return false;
    }

    private boolean projectExists(String projectName) {
        //todo: needs work
        if(!Objects.equals(projectName, "T-Helper")){
            return false;
        }
        return true;
    }

    private boolean keyExistsInProject() {
        // todo: needs work
        return true;
    }

    private ArrayList<String> mockQueryToQueryClass(HashMap<String, ?> queryReceived) {
        ArrayList<String> newArray = new ArrayList<String>();
        newArray.add("Jack");
        newArray.add("Jack2");
        newArray.add("Jack3");

        return newArray;
    }

}
