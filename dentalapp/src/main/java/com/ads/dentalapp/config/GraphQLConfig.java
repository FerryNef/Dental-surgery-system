package com.ads.dentalapp.config;

import com.ads.dentalapp.resolver.DentistResolver;
import com.ads.dentalapp.resolver.PatientResolver;
import com.ads.dentalapp.service.AppointmentService;
import com.ads.dentalapp.service.DentistService;

import com.ads.dentalapp.service.PatientService;
import graphql.GraphQL;
import graphql.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
@Component
public class GraphQLConfig {

    public static GraphQL graphQL;
    private final PatientService patientService;
    private final PatientResolver patientResolver;
    @Autowired
    private DentistResolver dentistResolver;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DentistService dentistService;
    GraphQLScalarType longScalar = CustomScalars.createLongScalar();
    GraphQLScalarType localDateScalar = CustomScalars.createLocalDateScalar();
    GraphQLScalarType localTimeScalar = CustomScalars.createLocalTimeScalar();

    @Autowired
    public GraphQLConfig(PatientService patientService, PatientResolver patientResolver) {
        this.patientService = patientService;
        this.patientResolver = patientResolver;
    }

    public void setupGraphQL() {
        // Define the patientType GraphQL object
        GraphQLObjectType patientType = GraphQLObjectType.newObject()
                .name("Patient")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("id")
                        .type(longScalar)
                        .build())
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("firstName")
                        .type(GraphQLString)
                        .build())
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("lastName")
                        .type(GraphQLString)
                        .build())
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("email")
                        .type(GraphQLString)
                        .build())
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("phone")
                        .type(GraphQLString)
                        .build())
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("dateOfBirth")
                        .type(localDateScalar)  // Using custom LocalDate scalar
                        .build())
                .build();

        GraphQLObjectType dentistType = GraphQLObjectType.newObject()
                .name("Dentist")
                .field(f -> f.name("id").type(longScalar))
                .field(f -> f.name("firstName").type(GraphQLString))
                .field(f -> f.name("lastName").type(GraphQLString))
                .field(f -> f.name("specialization").type(GraphQLString))
                .build();

        GraphQLObjectType appointmentType = GraphQLObjectType.newObject()
                .name("Appointment")
                .field(f -> f.name("id").type(longScalar))
                .field(f -> f.name("date").type(localDateScalar))
                .field(f -> f.name("time").type(localTimeScalar))
                .field(f -> f.name("status").type(GraphQLString))
                .field(f -> f.name("dentist").type(dentistType)) // nested dentist
                .build();

        // Define the Query type and link it to the resolver
        GraphQLObjectType queryType = GraphQLObjectType.newObject()
                .name("Query")
                .field(field -> field
                        .name("getPatient")
                        .type(patientType)
                        .argument(a -> a.name("id").type(longScalar))
                        .dataFetcher(patientResolver::getPatient) // Use the resolver here
                ).field(field -> field
                        .name("getAllPatients")
                        .type(GraphQLList.list(patientType))
                        .dataFetcher(patientResolver::getAllPatients)  // You’ll need this method
                ).field(field -> field
                        .name("getAllAppointments")
                        .type(GraphQLList.list(appointmentType))
                        .dataFetcher(env -> appointmentService.getAllAppointments()) // You’ll need this too
                )
                .field(field -> field
                        .name("getAppointmentsByPatient")
                        .type(GraphQLList.list(appointmentType))
                        .argument(a -> a.name("patientId").type(GraphQLInt))
                        .dataFetcher(env -> {
                            Long patientId = env.getArgument("patientId");
                            return appointmentService.getAppointmentsByPatientId(patientId);
                        })
                )
                .field(field -> field
                        .name("getAllDentist")
                        .type(GraphQLList.list(dentistType))
                        .dataFetcher(dentistResolver::getAllDentists)
                )
                .build();

        GraphQLObjectType mutationType = GraphQLObjectType.newObject()
                .name("Mutation")
                .field(field -> field.name("deletePatient")
                        .type(GraphQLString)
                        .argument(arg -> arg.name("id").type(longScalar))
                        .dataFetcher(env -> {
                            Long id = env.getArgument("id");
                            patientService.deletePatient(id);
                            return "Patient deleted successfully.";
                        }))
                .build();

        // Create GraphQL schema with custom scalars
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
                .query(queryType)
                .mutation(mutationType)
                .additionalType(longScalar)
                .additionalType(localDateScalar)
                .additionalType(localTimeScalar)
                .build();

        // Initialize the GraphQL instance
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}