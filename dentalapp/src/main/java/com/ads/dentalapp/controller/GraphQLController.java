package com.ads.dentalapp.controller;

import com.ads.dentalapp.config.GraphQLConfig;
import com.ads.dentalapp.dto.request.GraphQLRequest;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class GraphQLController {

    private final GraphQLConfig graphQLConfig;

    public GraphQLController(GraphQLConfig graphQLConfig) {
        this.graphQLConfig = graphQLConfig;
    }

    @PreAuthorize("hasRole('OFFICE_MANAGER')")
    @RequestMapping(value = "/graphql", method = RequestMethod.POST)
    @ResponseBody
    public Object graphql(@RequestBody GraphQLRequest request) {
        // Pass the query to GraphQL and return the result
        ExecutionResult result = graphQLConfig.getGraphQL().execute(request.getQuery());
        System.out.println("Errors: " + result.getErrors());
        System.out.println("Data: " + result.getData());
        return result.toSpecification();
    }

}