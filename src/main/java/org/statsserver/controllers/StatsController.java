package org.statsserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.statsserver.domain.QuerySet;
import org.statsserver.services.KeyDataListStatic;
import org.statsserver.services.ProjectService;
import org.statsserver.services.QueryService;

import java.net.URI;
import java.util.*;

@RestController
public class StatsController {
    @Autowired private final ProjectService projectService; // todo todo todo: shouldn't this service be annotated thus automatically instantiated & provided?
    private final QueryService queryService; // todo todo todo: shouldn't this service be annotated thus automatically instantiated & provided?


    public StatsController(ProjectService projectService, QueryService queryService) {
        this.projectService = projectService;
        this.queryService = queryService;
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
    public ResponseEntity<List<?>> getAllListValuesFromKeyInProject(@PathVariable String projectName, @PathVariable String keyName){
        System.out.println("GET MAPPING AANGESPROKEN getAllListValuesFromKey: ");

        if(!this.projectService.getProjectNameExist(projectName)){
            //TODO: throw error OR return 404
            return ResponseEntity.badRequest().body(null);
        }

        if(!KeyDataListStatic.doesKeyExist(keyName, projectName)){
            //TODO: throw error OR return 404
            return ResponseEntity.badRequest().body(null);
        }

        Set<?> listValues = KeyDataListStatic.getKeyDataListValues(keyName, projectName);

        if(listValues == null){
            //todo: throw 400 bad request if provided field does not exist or field does not contain a list to return (wrong data type)
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listValues.stream().toList());
    }

    @GetMapping(path = "api/v1/getAllQueriesFromProject/{projectName}")
    public ResponseEntity<List<QuerySet>> getAllQueriesFromProject(@PathVariable String projectName){

        if(!this.projectService.getProjectNameExist(projectName)){
            //TODO: throw error OR return 404
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity
                .ok()
                .body(this.queryService.getAllQueries(projectName));
    }

    @PostMapping(path = "api/v1/postQuery/{projectName}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> postQuery(@PathVariable String projectName, @RequestBody HashMap<String, ?> query){
        System.out.println("POST MAPPING AANGESPROKEN query: , received query: " + query);

        if(!this.projectService.getProjectNameExist(projectName)){
            //TODO: throw error OR return 404
        }

        //todo: add response error advice, because if something goes wrong inside the code then how will i know what went wrong?
        // because the creation of the query is done inside of a throw catch block

        UUID createdQuery = this.queryService.createQuery(projectName, query);
        if(createdQuery != null){
            return ResponseEntity
                    .created(
                            URI.create(String.format("/queryId/%s", createdQuery))
                    )
                    .body(true);
        }else{
            return ResponseEntity.unprocessableEntity().body(false);
        }
    }

    @GetMapping(path = "api/v1/getQuery/{projectName}/{queryId}")
    public ResponseEntity<Optional<QuerySet>> getQuery(@PathVariable String projectName, @PathVariable String queryId){
        System.out.println("GET MAPPING AANGESPROKEN getQuery, projectName is: " + projectName + "queryId is: " + queryId);

        if(!this.projectService.getProjectNameExist(projectName)){
            //TODO: throw error OR return 404
        }
        Optional<QuerySet> result = this.queryService.getQueryById(queryId, projectName);

        if(result.isPresent()){
            return ResponseEntity
                    .ok()
                    .body(result);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping(path = "api/v1/getQuery/{projectName}/{queryId}")
    public boolean updateQuery(@PathVariable String projectName, @PathVariable String queryId){
        System.out.println("PUT MAPPING AANGESPROKEN updateQuery, projectName is: " + projectName + "queryId is: " + queryId);

        if(Objects.equals(projectName, "T-Helper") && Objects.equals(queryId, "123abcDEF")){
            return true;
        }
        //todo: needs work, return actual result of query or bad request or query not found?
        return false;
    }

    @DeleteMapping(path = "api/v1/deleteQuery/{projectName}/{queryId}")
    public ResponseEntity<Boolean> deleteQuery(@PathVariable String projectName, @PathVariable String queryId){
        System.out.println("DELETE MAPPING AANGESPROKEN deleteQuery, projectName is: " + projectName + "queryId is: " + queryId);

        if(!this.projectService.getProjectNameExist(projectName)){
            //TODO: throw error OR return 404
        }

        Boolean result = this.queryService.removeQuery(queryId, projectName);

        if(result){
            return ResponseEntity
                    .ok()
                    .body(true);
        }
        return ResponseEntity
                .badRequest()
                .body(false);

    }

}
