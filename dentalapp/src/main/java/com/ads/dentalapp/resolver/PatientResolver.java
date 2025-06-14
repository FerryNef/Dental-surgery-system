package com.ads.dentalapp.resolver;

import com.ads.dentalapp.model.Appointment;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.service.PatientService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientResolver  {

    @Autowired
    private PatientService patientService;
    public Patient getPatient(DataFetchingEnvironment environment) {
        Object rawId = environment.getArgument("id");
        Long id = rawId instanceof Integer ? ((Integer) rawId).longValue() : (Long) rawId;
        return patientService.getPatientById(id);
    }

    public List<Patient> getAllPatients(DataFetchingEnvironment env) {
        return patientService.getAllPatientsSortedByLastName();
    }

//    public Patient getPatient(DataFetchingEnvironment environment) {
//        Long id = environment.getArgument("id"); // Get the argument from the environment
//        return patientService.getPatientById(id); // Fetch the patient using the service
//    }



}
