package com.ads.dentalapp.resolver;

import com.ads.dentalapp.dto.response.AppointmentResponseDTO;
import com.ads.dentalapp.model.Appointment;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.service.AppointmentService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentResolver {

    @Autowired
    private AppointmentService appointmentService;

    public List<Appointment> getAllAppointments(DataFetchingEnvironment env) {
        return appointmentService.getAllAppointments();
    }

    public List<Appointment> getAppointmentsByPatientId(DataFetchingEnvironment env) {
        Long patientId = getLongArgument(env, "patientId");
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    private Long getLongArgument(DataFetchingEnvironment env, String argName) {
        Object raw = env.getArgument(argName);
        return (raw instanceof Integer) ? ((Integer) raw).longValue() : (Long) raw;
    }
}
